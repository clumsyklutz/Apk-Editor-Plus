package jadx.core.dex.visitors

import jadx.core.codegen.CodeWriter
import jadx.core.codegen.MethodGen
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.attributes.IAttributeNode
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.utils.InsnUtils
import jadx.core.utils.RegionUtils
import jadx.core.utils.StringUtils
import jadx.core.utils.Utils
import java.io.File
import java.util.HashSet
import java.util.Iterator
import java.util.List
import org.d.b
import org.d.c

class DotGraphVisitor extends AbstractVisitor {
    private static val LOG = c.a(DotGraphVisitor.class)
    private static val NL = "\\l"
    private static val PRINT_DOMINATORS = false
    private final File dir
    private final Boolean rawInsn
    private final Boolean useRegions

    class DumpDotGraph {
        private final CodeWriter conn
        private final CodeWriter dot

        private constructor() {
            this.dot = CodeWriter()
            this.conn = CodeWriter()
        }

        private fun addEdge(BlockNode blockNode, BlockNode blockNode2, String str) {
            this.conn.startLine(makeName(blockNode)).add(" -> ").add(makeName(blockNode2))
            this.conn.add(str)
            this.conn.add(';')
        }

        private fun attributesString(IAttributeNode iAttributeNode) {
            StringBuilder sb = StringBuilder()
            Iterator it = iAttributeNode.getAttributesStringsList().iterator()
            while (it.hasNext()) {
                sb.append(escape((String) it.next())).append(DotGraphVisitor.NL)
            }
            return sb.toString()
        }

        private fun escape(String str) {
            return str.replace("\\", "").replace("/", "\\/").replace(">", "\\>").replace("<", "\\<").replace("{", "\\{").replace("}", "\\}").replace("\"", "\\\"").replace("-", "\\-").replace("|", "\\|").replace("\n", DotGraphVisitor.NL)
        }

        private fun insertInsns(MethodNode methodNode, IBlock iBlock) {
            if (!DotGraphVisitor.this.rawInsn) {
                CodeWriter codeWriter = CodeWriter()
                List instructions = iBlock.getInstructions()
                MethodGen.addFallbackInsns(codeWriter, methodNode, (Array<InsnNode>) instructions.toArray(new InsnNode[instructions.size()]), false)
                String strEscape = escape(codeWriter.newLine().toString())
                return strEscape.startsWith(DotGraphVisitor.NL) ? strEscape.substring(2) : strEscape
            }
            StringBuilder sb = StringBuilder()
            for (InsnNode insnNode : iBlock.getInstructions()) {
                sb.append(escape(insnNode + " " + insnNode.getAttributesString()))
                sb.append(DotGraphVisitor.NL)
            }
            return sb.toString()
        }

        private fun makeName(IContainer iContainer) {
            return iContainer is BlockNode ? "Node_" + ((BlockNode) iContainer).getId() : iContainer is IBlock ? "Node_" + iContainer.getClass().getSimpleName() + "_" + iContainer.hashCode() : "cluster_" + iContainer.getClass().getSimpleName() + "_" + iContainer.hashCode()
        }

        private fun processBlock(MethodNode methodNode, BlockNode blockNode, Boolean z) {
            String strAttributesString = attributesString(blockNode)
            this.dot.startLine(makeName(blockNode))
            this.dot.add(" [shape=record,")
            if (z) {
                this.dot.add("color=red,")
            }
            this.dot.add("label=\"{")
            this.dot.add(String.valueOf(blockNode.getId())).add("\\:\\ ")
            this.dot.add(InsnUtils.formatOffset(blockNode.getStartOffset()))
            if (strAttributesString.length() != 0) {
                this.dot.add('|').add(strAttributesString)
            }
            String strInsertInsns = insertInsns(methodNode, blockNode)
            if (strInsertInsns.length() != 0) {
                this.dot.add('|').add(strInsertInsns)
            }
            this.dot.add("}\"];")
            BlockNode elseBlock = null
            List instructions = blockNode.getInstructions()
            if (!instructions.isEmpty() && ((InsnNode) instructions.get(0)).getType() == InsnType.IF) {
                elseBlock = ((IfNode) instructions.get(0)).getElseBlock()
            }
            for (BlockNode blockNode2 : blockNode.getSuccessors()) {
                addEdge(blockNode, blockNode2, blockNode2 == elseBlock ? "[style=dashed]" : "")
            }
        }

        private fun processIBlock(MethodNode methodNode, IBlock iBlock, Boolean z) {
            String strAttributesString = attributesString(iBlock)
            this.dot.startLine(makeName(iBlock))
            this.dot.add(" [shape=record,")
            if (z) {
                this.dot.add("color=red,")
            }
            this.dot.add("label=\"{")
            if (strAttributesString.length() != 0) {
                this.dot.add(strAttributesString)
            }
            String strInsertInsns = insertInsns(methodNode, iBlock)
            if (strInsertInsns.length() != 0) {
                this.dot.add('|').add(strInsertInsns)
            }
            this.dot.add("}\"];")
        }

        private fun processMethodRegion(MethodNode methodNode) {
            processRegion(methodNode, methodNode.getRegion())
            for (ExceptionHandler exceptionHandler : methodNode.getExceptionHandlers()) {
                if (exceptionHandler.getHandlerRegion() != null) {
                    processRegion(methodNode, exceptionHandler.getHandlerRegion())
                }
            }
            HashSet hashSet = HashSet(methodNode.getBasicBlocks().size())
            RegionUtils.getAllRegionBlocks(methodNode.getRegion(), hashSet)
            Iterator it = methodNode.getExceptionHandlers().iterator()
            while (it.hasNext()) {
                IContainer handlerRegion = ((ExceptionHandler) it.next()).getHandlerRegion()
                if (handlerRegion != null) {
                    RegionUtils.getAllRegionBlocks(handlerRegion, hashSet)
                }
            }
            for (BlockNode blockNode : methodNode.getBasicBlocks()) {
                if (!hashSet.contains(blockNode)) {
                    processBlock(methodNode, blockNode, true)
                }
            }
        }

        private fun processRegion(MethodNode methodNode, IContainer iContainer) {
            if (!(iContainer is IRegion)) {
                if (iContainer is BlockNode) {
                    processBlock(methodNode, (BlockNode) iContainer, false)
                    return
                } else {
                    if (iContainer is IBlock) {
                        processIBlock(methodNode, (IBlock) iContainer, false)
                        return
                    }
                    return
                }
            }
            IRegion iRegion = (IRegion) iContainer
            this.dot.startLine("subgraph " + makeName(iContainer) + " {")
            this.dot.startLine("label = \"").add(iRegion.toString())
            String strAttributesString = attributesString(iRegion)
            if (strAttributesString.length() != 0) {
                this.dot.add(" | ").add(strAttributesString)
            }
            this.dot.add("\";")
            this.dot.startLine("node [shape=record,color=blue];")
            Iterator it = iRegion.getSubBlocks().iterator()
            while (it.hasNext()) {
                processRegion(methodNode, (IContainer) it.next())
            }
            this.dot.startLine('}')
        }

        fun process(MethodNode methodNode) throws Throwable {
            this.dot.startLine("digraph \"CFG for")
            this.dot.add(escape(methodNode.getParentClass() + Deobfuscator.CLASS_NAME_SEPARATOR + methodNode.getMethodInfo().getShortId()))
            this.dot.add("\" {")
            if (!DotGraphVisitor.this.useRegions) {
                Iterator it = methodNode.getBasicBlocks().iterator()
                while (it.hasNext()) {
                    processBlock(methodNode, (BlockNode) it.next(), false)
                }
            } else if (methodNode.getRegion() == null) {
                return
            } else {
                processMethodRegion(methodNode)
            }
            this.dot.startLine("MethodNode[shape=record,label=\"{")
            this.dot.add(escape(methodNode.getAccessFlags().makeString()))
            this.dot.add(escape(methodNode.getReturnType() + " " + methodNode.getParentClass() + Deobfuscator.CLASS_NAME_SEPARATOR + methodNode.getName() + "(" + Utils.listToString(methodNode.getArguments(true)) + ") "))
            String strAttributesString = attributesString(methodNode)
            if (strAttributesString.length() != 0) {
                this.dot.add(" | ").add(strAttributesString)
            }
            this.dot.add("}\"];")
            this.dot.startLine("MethodNode -> ").add(makeName(methodNode.getEnterBlock())).add(';')
            this.dot.add(this.conn.toString())
            this.dot.startLine('}')
            this.dot.startLine()
            this.dot.save(DotGraphVisitor.this.dir, methodNode.getParentClass().getClassInfo().getFullPath() + "_graphs", StringUtils.escape(methodNode.getMethodInfo().getShortId()) + (DotGraphVisitor.this.useRegions ? ".regions" : "") + (DotGraphVisitor.this.rawInsn ? ".raw" : "") + ".dot")
        }
    }

    private constructor(File file, Boolean z, Boolean z2) {
        this.dir = file
        this.useRegions = z
        this.rawInsn = z2
        b bVar = LOG
        Array<Object> objArr = new Object[3]
        objArr[0] = z ? "regions " : ""
        objArr[1] = z2 ? "raw " : ""
        objArr[2] = file.getAbsolutePath()
        bVar.a("DOT {}{}graph dump dir: {}", objArr)
    }

    fun dump(File file) {
        return DotGraphVisitor(file, false, false)
    }

    fun dumpRaw(File file) {
        return DotGraphVisitor(file, false, true)
    }

    fun dumpRawRegions(File file) {
        return DotGraphVisitor(file, true, true)
    }

    fun dumpRegions(File file) {
        return DotGraphVisitor(file, true, false)
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) throws Throwable {
        if (methodNode.isNoCode()) {
            return
        }
        DumpDotGraph().process(methodNode)
    }
}

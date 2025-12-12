package jadx.core.utils

import jadx.core.codegen.CodeWriter
import jadx.core.codegen.InsnGen
import jadx.core.codegen.MethodGen
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.PhiListAttr
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.visitors.DotGraphVisitor
import jadx.core.dex.visitors.regions.DepthRegionTraversal
import jadx.core.dex.visitors.regions.TracedRegionVisitor
import jadx.core.utils.exceptions.CodegenException
import jadx.core.utils.exceptions.JadxRuntimeException
import java.io.File
import java.util.ArrayList
import java.util.Iterator
import java.util.LinkedHashSet
import java.util.List
import org.d.b
import org.d.c

@Deprecated
class DebugUtils {
    private static val LOG = c.a(DebugUtils.class)

    private fun checkPHI(MethodNode methodNode) {
        for (BlockNode blockNode : methodNode.getBasicBlocks()) {
            ArrayList arrayList = ArrayList()
            for (InsnNode insnNode : blockNode.getInstructions()) {
                if (insnNode.getType() == InsnType.PHI) {
                    PhiInsn phiInsn = (PhiInsn) insnNode
                    arrayList.add(phiInsn)
                    if (phiInsn.getArgsCount() != phiInsn.getBlockBinds().size()) {
                        throw JadxRuntimeException("Incorrect args and binds in PHI")
                    }
                    if (phiInsn.getArgsCount() == 0) {
                        throw JadxRuntimeException("No args and binds in PHI")
                    }
                    for (InsnArg insnArg : insnNode.getArguments()) {
                        if (!(insnArg is RegisterArg)) {
                            throw JadxRuntimeException("Not register in phi insn")
                        }
                        if (phiInsn.getBlockByArg((RegisterArg) insnArg) == null) {
                            throw JadxRuntimeException("Predecessor block not found")
                        }
                    }
                }
            }
            PhiListAttr phiListAttr = (PhiListAttr) blockNode.get(AType.PHI_LIST)
            if (phiListAttr != null) {
                List list = phiListAttr.getList()
                if (list.isEmpty()) {
                    throw JadxRuntimeException("Empty PHI list attribute")
                }
                if (!arrayList.containsAll(list) || !list.containsAll(arrayList)) {
                    throw JadxRuntimeException("Instructions not match")
                }
            } else if (!arrayList.isEmpty()) {
                throw JadxRuntimeException("Missing PHI list attribute")
            }
        }
        for (SSAVar sSAVar : methodNode.getSVars()) {
            PhiInsn usedInPhi = sSAVar.getUsedInPhi()
            if (usedInPhi != null) {
                Boolean z = false
                Iterator it = sSAVar.getUseList().iterator()
                while (it.hasNext()) {
                    InsnNode parentInsn = ((RegisterArg) it.next()).getParentInsn()
                    if (parentInsn != null && parentInsn == usedInPhi) {
                        z = true
                    }
                    z = z
                }
                if (!z) {
                    throw JadxRuntimeException("Used in phi incorrect")
                }
            }
        }
    }

    fun checkSSA(MethodNode methodNode) {
        Iterator it = methodNode.getBasicBlocks().iterator()
        while (it.hasNext()) {
            for (InsnNode insnNode : ((BlockNode) it.next()).getInstructions()) {
                if (insnNode.getResult() != null) {
                    checkSSAVar(methodNode, insnNode, insnNode.getResult())
                }
                for (InsnArg insnArg : insnNode.getArguments()) {
                    if (insnArg is RegisterArg) {
                        checkSSAVar(methodNode, insnNode, (RegisterArg) insnArg)
                    }
                }
            }
        }
        checkPHI(methodNode)
    }

    private fun checkSSAVar(MethodNode methodNode, InsnNode insnNode, RegisterArg registerArg) {
        SSAVar sVar = registerArg.getSVar()
        if (sVar == null) {
            throw JadxRuntimeException("Null SSA var in " + insnNode + ", mth: " + methodNode)
        }
        for (RegisterArg registerArg2 : sVar.getUseList()) {
            InsnNode parentInsn = registerArg2.getParentInsn()
            if (parentInsn != null && !parentInsn.containsArg(registerArg2)) {
                throw JadxRuntimeException("Incorrect use info in PHI insn")
            }
        }
    }

    fun dump(MethodNode methodNode) throws Throwable {
        dump(methodNode, "")
    }

    fun dump(MethodNode methodNode, String str) throws Throwable {
        File file = File("test-graph" + str + "-tmp")
        DotGraphVisitor.dump(file).visit(methodNode)
        DotGraphVisitor.dumpRaw(file).visit(methodNode)
        DotGraphVisitor.dumpRegions(file).visit(methodNode)
    }

    private fun printInsns(MethodNode methodNode, String str, IBlock iBlock) {
        for (InsnNode insnNode : iBlock.getInstructions()) {
            try {
                InsnGen insnGen = InsnGen(MethodGen.getFallbackMethodGen(methodNode), true)
                CodeWriter codeWriter = CodeWriter()
                insnGen.makeInsn(insnNode, codeWriter)
                LOG.a("{} - {}", str, codeWriter.toString().substring(CodeWriter.NL.length()))
            } catch (CodegenException e) {
                LOG.a("{} - {}", str, insnNode)
            }
        }
    }

    private fun printRegion(MethodNode methodNode, IRegion iRegion, String str, Boolean z) {
        LOG.a("{}{}", str, iRegion)
        String str2 = str + "|  "
        for (IContainer iContainer : iRegion.getSubBlocks()) {
            if (iContainer is IRegion) {
                printRegion(methodNode, (IRegion) iContainer, str2, z)
            } else {
                LOG.a("{}{} {}", str2, iContainer, iContainer.getAttributesString())
                if (z && (iContainer is IBlock)) {
                    printInsns(methodNode, str2, (IBlock) iContainer)
                }
            }
        }
    }

    fun printRegion(MethodNode methodNode, IRegion iRegion, Boolean z) {
        printRegion(methodNode, iRegion, "", z)
    }

    fun printRegions(MethodNode methodNode) {
        printRegions(methodNode, false)
    }

    fun printRegions(MethodNode methodNode, Boolean z) {
        LOG.a("|{}", methodNode)
        printRegion(methodNode, methodNode.getRegion(), "|  ", z)
    }

    fun printRegionsWithBlock(MethodNode methodNode, final BlockNode blockNode) {
        val linkedHashSet = LinkedHashSet()
        DepthRegionTraversal.traverse(methodNode, TracedRegionVisitor() { // from class: jadx.core.utils.DebugUtils.1
            @Override // jadx.core.dex.visitors.regions.TracedRegionVisitor
            public final Unit processBlockTraced(MethodNode methodNode2, IBlock iBlock, IRegion iRegion) {
                if (blockNode.equals(iBlock)) {
                    linkedHashSet.add(iRegion)
                }
            }
        })
        LOG.a(" Found block: {} in regions: {}", blockNode, linkedHashSet)
    }
}

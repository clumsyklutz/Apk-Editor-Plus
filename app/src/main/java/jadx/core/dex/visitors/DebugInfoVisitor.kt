package jadx.core.dex.visitors

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.nodes.parser.DebugInfoParser
import jadx.core.utils.BlockUtils
import jadx.core.utils.exceptions.DecodeException
import java.util.Iterator

class DebugInfoVisitor extends AbstractVisitor {
    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) throws DecodeException {
        InsnNode insnNode
        Int debugInfoOffset = methodNode.getDebugInfoOffset()
        if (debugInfoOffset > 0) {
            Array<InsnNode> instructions = methodNode.getInstructions()
            DebugInfoParser(methodNode, debugInfoOffset, instructions).process()
            if (instructions.length != 0) {
                Int length = instructions.length
                Int i = 0
                while (true) {
                    if (i >= length) {
                        break
                    }
                    InsnNode insnNode2 = instructions[i]
                    if (insnNode2 != null) {
                        Int sourceLine = insnNode2.getSourceLine()
                        if (sourceLine != 0) {
                            methodNode.setSourceLine(sourceLine - 1)
                        }
                    } else {
                        i++
                    }
                }
            }
            if (!methodNode.getReturnType().equals(ArgType.VOID)) {
                Iterator it = methodNode.getExitBlocks().iterator()
                while (it.hasNext()) {
                    InsnNode lastInsn = BlockUtils.getLastInsn((BlockNode) it.next())
                    if (lastInsn != null && (insnNode = instructions[lastInsn.getOffset()]) != lastInsn) {
                        RegisterArg registerArg = (RegisterArg) insnNode.getArg(0)
                        ((RegisterArg) lastInsn.getArg(0)).mergeDebugInfo(registerArg.getType(), registerArg.getName())
                        lastInsn.setSourceLine(insnNode.getSourceLine())
                    }
                }
            }
        }
        methodNode.unloadInsnArr()
    }
}

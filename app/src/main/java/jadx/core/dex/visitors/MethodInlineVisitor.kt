package jadx.core.dex.visitors

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.nodes.MethodInlineAttr
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import java.util.List

class MethodInlineVisitor extends AbstractVisitor {
    private fun addInlineAttr(MethodNode methodNode, InsnNode insnNode) {
        methodNode.addAttr(MethodInlineAttr(insnNode))
        methodNode.add(AFlag.DONT_GENERATE)
    }

    private fun inlineMth(MethodNode methodNode, BlockNode blockNode, BlockNode blockNode2) {
        List instructions = blockNode.getInstructions()
        if (instructions.isEmpty()) {
            addInlineAttr(methodNode, InsnNode.wrapArg(((InsnNode) ((BlockNode) methodNode.getBasicBlocks().get(1)).getInstructions().get(0)).getArg(0)))
            return
        }
        if (instructions.size() == 1) {
            addInlineAttr(methodNode, (InsnNode) instructions.get(0))
            return
        }
        if (instructions.size() == 2 && blockNode2.getInstructions().size() == 1 && !methodNode.getReturnType().equals(ArgType.VOID)) {
            InsnNode insnNode = (InsnNode) instructions.get(0)
            InsnNode insnNode2 = (InsnNode) instructions.get(1)
            InsnArg arg = ((InsnNode) blockNode2.getInstructions().get(0)).getArg(0)
            if (insnNode.getType() == InsnType.IGET && insnNode2.getType() == InsnType.IPUT && arg.isRegister() && insnNode.getResult().equalRegisterAndType((RegisterArg) arg)) {
                RegisterArg registerArg = (RegisterArg) arg
                registerArg.getSVar().removeUse(registerArg)
                CodeShrinker.shrinkMethod(methodNode)
                List instructions2 = blockNode.getInstructions()
                if (instructions2.size() == 1) {
                    addInlineAttr(methodNode, (InsnNode) instructions2.get(0))
                }
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        AccessInfo accessFlags = methodNode.getAccessFlags()
        if (accessFlags.isSynthetic() && accessFlags.isStatic() && methodNode.getBasicBlocks().size() == 2) {
            BlockNode blockNode = (BlockNode) methodNode.getBasicBlocks().get(1)
            if (blockNode.contains(AFlag.RETURN) || blockNode.getInstructions().isEmpty()) {
                inlineMth(methodNode, (BlockNode) methodNode.getBasicBlocks().get(0), blockNode)
            }
        }
    }
}

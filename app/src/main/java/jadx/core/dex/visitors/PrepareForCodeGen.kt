package jadx.core.dex.visitors

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.ArithNode
import jadx.core.dex.instructions.ArithOp
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import java.util.Iterator
import java.util.List

@JadxVisitor(desc = "Prepare instructions for code generation pass", name = "PrepareForCodeGen", runAfter = {CodeShrinker.class, ClassModifier.class})
class PrepareForCodeGen extends AbstractVisitor {
    private fun checkInline(BlockNode blockNode) {
        List instructions = blockNode.getInstructions()
        for (Int i = 0; i < instructions.size(); i++) {
            InsnNode insnNode = (InsnNode) instructions.get(i)
            if (insnNode.getType() == InsnType.MOVE && insnNode.getArg(0).isInsnWrap()) {
                InsnNode wrapInsn = ((InsnWrapArg) insnNode.getArg(0)).getWrapInsn()
                wrapInsn.setResult(insnNode.getResult())
                wrapInsn.copyAttributesFrom(insnNode)
                instructions.set(i, wrapInsn)
            }
        }
    }

    private fun checkInsn(InsnNode insnNode) {
        if (insnNode.getType() != InsnType.ARITH) {
            for (InsnArg insnArg : insnNode.getArguments()) {
                if (insnArg.isInsnWrap()) {
                    checkInsn(((InsnWrapArg) insnArg).getWrapInsn())
                }
            }
            return
        }
        ArithNode arithNode = (ArithNode) insnNode
        ArithOp op = arithNode.getOp()
        if (op != ArithOp.ADD && op != ArithOp.SUB) {
            return
        }
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= 2) {
                return
            }
            InsnArg arg = arithNode.getArg(i2)
            if (arg.isInsnWrap()) {
                InsnNode wrapInsn = ((InsnWrapArg) arg).getWrapInsn()
                wrapInsn.add(AFlag.DONT_WRAP)
                checkInsn(wrapInsn)
            }
            i = i2 + 1
        }
    }

    private fun modifyArith(BlockNode blockNode) {
        for (InsnNode insnNode : blockNode.getInstructions()) {
            if (insnNode.getType() == InsnType.ARITH) {
                RegisterArg result = insnNode.getResult()
                InsnArg arg = insnNode.getArg(0)
                if (result.equals(arg) ? true : arg.isRegister() ? result.equalRegisterAndType((RegisterArg) arg) : false) {
                    insnNode.add(AFlag.ARITH_ONEARG)
                }
            }
        }
    }

    private fun removeInstructions(BlockNode blockNode) {
        Iterator it = blockNode.getInstructions().iterator()
        while (it.hasNext()) {
            InsnNode insnNode = (InsnNode) it.next()
            switch (insnNode.getType()) {
                case NOP:
                case MONITOR_ENTER:
                case MONITOR_EXIT:
                case MOVE_EXCEPTION:
                    it.remove()
                    break
                case CONSTRUCTOR:
                    if (!((ConstructorInsn) insnNode).isSelf()) {
                        break
                    } else {
                        it.remove()
                        break
                    }
                case MOVE:
                    RegisterArg result = insnNode.getResult()
                    if (result.getSVar().getUseCount() != 0 || !result.isNameEquals(insnNode.getArg(0))) {
                        break
                    } else {
                        it.remove()
                        break
                    }
                    break
            }
        }
    }

    private fun removeParenthesis(BlockNode blockNode) {
        Iterator it = blockNode.getInstructions().iterator()
        while (it.hasNext()) {
            checkInsn((InsnNode) it.next())
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        List<BlockNode> basicBlocks = methodNode.getBasicBlocks()
        if (basicBlocks == null) {
            return
        }
        for (BlockNode blockNode : basicBlocks) {
            removeInstructions(blockNode)
            checkInline(blockNode)
            modifyArith(blockNode)
        }
    }
}

package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.mods.TernaryInsn
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.Region
import jadx.core.dex.regions.conditions.IfRegion
import jadx.core.dex.visitors.CodeShrinker
import jadx.core.utils.InsnList
import java.util.HashMap
import java.util.Iterator
import java.util.Map

class TernaryMod {
    private constructor() {
    }

    private fun checkLineStats(InsnNode insnNode, InsnNode insnNode2) {
        InsnNode assignInsn
        Int sourceLine
        if (insnNode.getResult() == null || insnNode2.getResult() == null) {
            return false
        }
        PhiInsn usedInPhi = insnNode.getResult().getSVar().getUsedInPhi()
        PhiInsn usedInPhi2 = insnNode2.getResult().getSVar().getUsedInPhi()
        if (usedInPhi == null || usedInPhi2 == null || usedInPhi != usedInPhi2) {
            return false
        }
        HashMap map = HashMap(usedInPhi.getArgsCount())
        for (InsnArg insnArg : usedInPhi.getArguments()) {
            if (insnArg.isRegister() && (assignInsn = ((RegisterArg) insnArg).getAssignInsn()) != null && (sourceLine = assignInsn.getSourceLine()) != 0) {
                Integer num = (Integer) map.get(Integer.valueOf(sourceLine))
                if (num != null) {
                    map.put(Integer.valueOf(sourceLine), Integer.valueOf(num.intValue() + 1))
                } else {
                    map.put(Integer.valueOf(sourceLine), 1)
                }
            }
        }
        Iterator it = map.entrySet().iterator()
        while (it.hasNext()) {
            if (((Integer) ((Map.Entry) it.next()).getValue()).intValue() >= 2) {
                return true
            }
        }
        return false
    }

    private fun containsTernary(InsnNode insnNode) {
        if (insnNode.getType() == InsnType.TERNARY) {
            return true
        }
        for (Int i = 0; i < insnNode.getArgsCount(); i++) {
            InsnArg arg = insnNode.getArg(i)
            if (arg.isInsnWrap() && containsTernary(((InsnWrapArg) arg).getWrapInsn())) {
                return true
            }
        }
        return false
    }

    private fun getTernaryInsnBlock(IContainer iContainer) {
        if (iContainer is Region) {
            Region region = (Region) iContainer
            if (region.getSubBlocks().size() == 1) {
                IContainer iContainer2 = (IContainer) region.getSubBlocks().get(0)
                if (iContainer2 is BlockNode) {
                    BlockNode blockNode = (BlockNode) iContainer2
                    if (blockNode.getInstructions().size() == 1) {
                        return blockNode
                    }
                }
            }
        }
        return null
    }

    static Boolean makeTernaryInsn(MethodNode methodNode, IfRegion ifRegion) {
        RegisterArg result
        if (ifRegion.contains(AFlag.ELSE_IF_CHAIN)) {
            return false
        }
        IContainer thenRegion = ifRegion.getThenRegion()
        IContainer elseRegion = ifRegion.getElseRegion()
        if (thenRegion == null || elseRegion == null) {
            return false
        }
        BlockNode ternaryInsnBlock = getTernaryInsnBlock(thenRegion)
        BlockNode ternaryInsnBlock2 = getTernaryInsnBlock(elseRegion)
        if (ternaryInsnBlock == null || ternaryInsnBlock2 == null) {
            return false
        }
        BlockNode header = ifRegion.getHeader()
        InsnNode insnNode = (InsnNode) ternaryInsnBlock.getInstructions().get(0)
        InsnNode insnNode2 = (InsnNode) ternaryInsnBlock2.getInstructions().get(0)
        if (insnNode.getSourceLine() != insnNode2.getSourceLine()) {
            if (insnNode.getSourceLine() == 0 || insnNode2.getSourceLine() == 0) {
                if (containsTernary(insnNode) || containsTernary(insnNode2)) {
                    return false
                }
            } else if (!checkLineStats(insnNode, insnNode2)) {
                return false
            }
        }
        if (insnNode.getResult() != null && insnNode2.getResult() != null) {
            PhiInsn usedInPhi = insnNode.getResult().getSVar().getUsedInPhi()
            if (usedInPhi == null || !insnNode.getResult().equalRegisterAndType(insnNode2.getResult())) {
                return false
            }
            if (!ifRegion.getParent().replaceSubBlock(ifRegion, header)) {
                return false
            }
            InsnList.remove(ternaryInsnBlock, insnNode)
            InsnList.remove(ternaryInsnBlock2, insnNode2)
            if (usedInPhi.getArgsCount() == 2) {
                result = usedInPhi.getResult()
            } else {
                result = insnNode.getResult()
                usedInPhi.removeArg(insnNode2.getResult())
            }
            TernaryInsn ternaryInsn = TernaryInsn(ifRegion.getCondition(), result, InsnArg.wrapArg(insnNode), InsnArg.wrapArg(insnNode2))
            ternaryInsn.setSourceLine(insnNode.getSourceLine())
            header.getInstructions().clear()
            header.getInstructions().add(ternaryInsn)
            CodeShrinker.shrinkMethod(methodNode)
            return true
        }
        if (methodNode.getReturnType().equals(ArgType.VOID) || insnNode.getType() != InsnType.RETURN || insnNode2.getType() != InsnType.RETURN) {
            return false
        }
        if (!ifRegion.getParent().replaceSubBlock(ifRegion, header)) {
            return false
        }
        InsnList.remove(ternaryInsnBlock, insnNode)
        InsnList.remove(ternaryInsnBlock2, insnNode2)
        ternaryInsnBlock.remove(AFlag.RETURN)
        ternaryInsnBlock2.remove(AFlag.RETURN)
        TernaryInsn ternaryInsn2 = TernaryInsn(ifRegion.getCondition(), null, insnNode.getArg(0), insnNode2.getArg(0))
        ternaryInsn2.setSourceLine(insnNode.getSourceLine())
        InsnNode insnNode3 = InsnNode(InsnType.RETURN, 1)
        insnNode3.addArg(InsnArg.wrapArg(ternaryInsn2))
        header.getInstructions().clear()
        header.getInstructions().add(insnNode3)
        header.add(AFlag.RETURN)
        CodeShrinker.shrinkMethod(methodNode)
        return true
    }
}

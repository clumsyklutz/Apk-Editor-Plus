package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.ArithNode
import jadx.core.dex.instructions.ArithOp
import jadx.core.dex.instructions.IfOp
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.InvokeType
import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.conditions.Compare
import jadx.core.dex.regions.conditions.IfCondition
import jadx.core.dex.regions.loops.ForEachLoop
import jadx.core.dex.regions.loops.ForLoop
import jadx.core.dex.regions.loops.LoopRegion
import jadx.core.dex.regions.loops.LoopType
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.dex.visitors.CodeShrinker
import jadx.core.utils.BlockUtils
import jadx.core.utils.InstructionRemover
import jadx.core.utils.RegionUtils
import java.util.Iterator
import java.util.LinkedList
import java.util.List

class LoopRegionVisitor extends AbstractVisitor implements IRegionVisitor {
    private fun argInLoop(MethodNode methodNode, LoopRegion loopRegion, RegisterArg registerArg) {
        BlockNode blockByInsn
        InsnNode parentInsn = registerArg.getParentInsn()
        if (parentInsn == null || (blockByInsn = BlockUtils.getBlockByInsn(methodNode, parentInsn)) == null) {
            return false
        }
        return RegionUtils.isRegionContainsBlock(loopRegion, blockByInsn)
    }

    private fun assignOnlyInLoop(MethodNode methodNode, LoopRegion loopRegion, RegisterArg registerArg) {
        InsnNode assignInsn = registerArg.getAssignInsn()
        if (assignInsn == null) {
            return true
        }
        if (!argInLoop(methodNode, loopRegion, assignInsn.getResult())) {
            return false
        }
        if (assignInsn is PhiInsn) {
            Iterator it = ((PhiInsn) assignInsn).getArguments().iterator()
            while (it.hasNext()) {
                if (!assignOnlyInLoop(methodNode, loopRegion, (RegisterArg) ((InsnArg) it.next()))) {
                    return false
                }
            }
        }
        return true
    }

    private fun checkArrayForEach(MethodNode methodNode, InsnNode insnNode, InsnNode insnNode2, IfCondition ifCondition) {
        InsnNode assignInsn
        RegisterArg result
        InsnArg insnArgSearchWrappedInsnParent
        if ((insnNode2 is ArithNode) && ((ArithNode) insnNode2).getOp() == ArithOp.ADD) {
            InsnArg arg = insnNode2.getArg(1)
            if (!arg.isLiteral() || ((LiteralArg) arg).getLiteral() != 1) {
                return null
            }
            if (insnNode.getType() != InsnType.CONST || !insnNode.getArg(0).isLiteral() || ((LiteralArg) insnNode.getArg(0)).getLiteral() != 0) {
                return null
            }
            InsnArg arg2 = insnNode2.getArg(0)
            if (!arg2.isRegister()) {
                return null
            }
            List useList = ((RegisterArg) arg2).getSVar().getUseList()
            if (useList.size() != 3 || useList.get(2) != arg2) {
                return null
            }
            InsnArg insnArg = (InsnArg) useList.get(0)
            InsnNode parentInsn = ((RegisterArg) useList.get(1)).getParentInsn()
            if (parentInsn == null || parentInsn.getType() != InsnType.AGET) {
                return null
            }
            if (!ifCondition.isCompare()) {
                return null
            }
            Compare compare = ifCondition.getCompare()
            if (compare.getOp() != IfOp.LT || compare.getA() != insnArg) {
                return null
            }
            InsnArg b2 = compare.getB()
            if (b2.isInsnWrap()) {
                assignInsn = ((InsnWrapArg) b2).getWrapInsn()
            } else {
                if (!b2.isRegister()) {
                    return null
                }
                assignInsn = ((RegisterArg) b2).getAssignInsn()
            }
            if (assignInsn == null || assignInsn.getType() != InsnType.ARRAY_LENGTH) {
                return null
            }
            if (assignInsn.getArg(0).equals(parentInsn.getArg(0)) && (result = parentInsn.getResult()) != null) {
                assignInsn.add(AFlag.SKIP)
                parentInsn.add(AFlag.SKIP)
                InstructionRemover.unbindInsn(methodNode, assignInsn)
                CodeShrinker.shrinkMethod(methodNode)
                if (parentInsn.contains(AFlag.WRAPPED) && (insnArgSearchWrappedInsnParent = BlockUtils.searchWrappedInsnParent(methodNode, parentInsn)) != null && insnArgSearchWrappedInsnParent.getParentInsn() != null) {
                    insnArgSearchWrappedInsnParent.getParentInsn().replaceArg(insnArgSearchWrappedInsnParent, result)
                }
                return ForEachLoop(result, assignInsn.getArg(0))
            }
            return null
        }
        return null
    }

    private fun checkForIndexedLoop(MethodNode methodNode, LoopRegion loopRegion, IfCondition ifCondition) {
        InsnNode lastInsn = RegionUtils.getLastInsn(loopRegion)
        if (lastInsn == null) {
            return false
        }
        RegisterArg result = lastInsn.getResult()
        if (result == null || result.getSVar() == null || !result.getSVar().isUsedInPhi()) {
            return false
        }
        PhiInsn usedInPhi = result.getSVar().getUsedInPhi()
        if (usedInPhi == null || usedInPhi.getArgsCount() != 2 || !usedInPhi.getArg(1).equals(result) || result.getSVar().getUseCount() != 1) {
            return false
        }
        RegisterArg result2 = usedInPhi.getResult()
        if (!ifCondition.getRegisterArgs().contains(result2) || result2.getSVar().isUsedInPhi()) {
            return false
        }
        RegisterArg arg = usedInPhi.getArg(0)
        InsnNode assignInsn = arg.getAssignInsn()
        if (assignInsn == null || arg.getSVar().getUseCount() != 1) {
            return false
        }
        if (!usedOnlyInLoop(methodNode, loopRegion, result2)) {
            return false
        }
        LinkedList linkedList = LinkedList()
        lastInsn.getRegisterArgs(linkedList)
        Iterator it = linkedList.iterator()
        while (it.hasNext()) {
            if (assignOnlyInLoop(methodNode, loopRegion, (RegisterArg) it.next())) {
                return false
            }
        }
        assignInsn.add(AFlag.SKIP)
        lastInsn.add(AFlag.SKIP)
        LoopType loopTypeCheckArrayForEach = checkArrayForEach(methodNode, assignInsn, lastInsn, ifCondition)
        if (loopTypeCheckArrayForEach != null) {
            loopRegion.setType(loopTypeCheckArrayForEach)
            return true
        }
        loopRegion.setType(ForLoop(assignInsn, lastInsn))
        return true
    }

    private fun checkInvoke(InsnNode insnNode, String str, String str2, Int i) {
        if (insnNode.getType() != InsnType.INVOKE) {
            return false
        }
        InvokeNode invokeNode = (InvokeNode) insnNode
        MethodInfo callMth = invokeNode.getCallMth()
        if (callMth.getArgsCount() == i && callMth.getShortId().equals(str2) && invokeNode.getInvokeType() == InvokeType.INTERFACE) {
            return str == null || callMth.getDeclClass().getFullName().equals(str)
        }
        return false
    }

    private fun checkIterableForEach(MethodNode methodNode, LoopRegion loopRegion, IfCondition ifCondition) {
        RegisterArg registerArg
        List registerArgs = ifCondition.getRegisterArgs()
        if (registerArgs.size() != 1) {
            return false
        }
        RegisterArg registerArg2 = (RegisterArg) registerArgs.get(0)
        SSAVar sVar = registerArg2.getSVar()
        if (sVar == null || sVar.isUsedInPhi()) {
            return false
        }
        List useList = sVar.getUseList()
        InsnNode assignInsn = registerArg2.getAssignInsn()
        if (useList.size() != 2 || assignInsn == null || !checkInvoke(assignInsn, null, "iterator()Ljava/util/Iterator;", 0)) {
            return false
        }
        InsnArg arg = assignInsn.getArg(0)
        InsnNode parentInsn = ((RegisterArg) useList.get(0)).getParentInsn()
        InsnNode parentInsn2 = ((RegisterArg) useList.get(1)).getParentInsn()
        if (parentInsn == null || parentInsn2 == null || !checkInvoke(parentInsn, "java.util.Iterator", "hasNext()Z", 0) || !checkInvoke(parentInsn2, "java.util.Iterator", "next()Ljava/lang/Object;", 0)) {
            return false
        }
        LinkedList linkedList = LinkedList()
        RegisterArg result = parentInsn2.getResult()
        if (result == null) {
            return false
        }
        if (parentInsn2.contains(AFlag.WRAPPED)) {
            InsnArg insnArgSearchWrappedInsnParent = BlockUtils.searchWrappedInsnParent(methodNode, parentInsn2)
            if (insnArgSearchWrappedInsnParent == null || insnArgSearchWrappedInsnParent.getParentInsn() == null) {
                return false
            }
            InsnNode parentInsn3 = insnArgSearchWrappedInsnParent.getParentInsn()
            if (parentInsn3.getType() == InsnType.CHECK_CAST) {
                result = parentInsn3.getResult()
                if (result == null || !fixIterableType(methodNode, arg, result)) {
                    return false
                }
                InsnArg insnArgSearchWrappedInsnParent2 = BlockUtils.searchWrappedInsnParent(methodNode, parentInsn3)
                if (insnArgSearchWrappedInsnParent2 == null || insnArgSearchWrappedInsnParent2.getParentInsn() == null) {
                    linkedList.add(parentInsn3)
                    registerArg = result
                } else {
                    insnArgSearchWrappedInsnParent2.getParentInsn().replaceArg(insnArgSearchWrappedInsnParent2, result)
                }
            } else {
                if (!fixIterableType(methodNode, arg, result)) {
                    return false
                }
                parentInsn3.replaceArg(insnArgSearchWrappedInsnParent, result)
            }
            registerArg = result
        } else {
            linkedList.add(parentInsn2)
            registerArg = result
        }
        assignInsn.add(AFlag.SKIP)
        Iterator it = linkedList.iterator()
        while (it.hasNext()) {
            ((InsnNode) it.next()).add(AFlag.SKIP)
        }
        loopRegion.setType(ForEachLoop(registerArg, arg))
        return true
    }

    private fun fixIterableType(MethodNode methodNode, InsnArg insnArg, RegisterArg registerArg) {
        ArgType type = insnArg.getType()
        ArgType type2 = registerArg.getType()
        if (!type.isGeneric()) {
            if (!insnArg.isRegister()) {
                return true
            }
            insnArg.setType(ArgType.generic(type.getObject(), new Array<ArgType>{type2}))
            return true
        }
        Array<ArgType> genericTypes = type.getGenericTypes()
        if (genericTypes == null || genericTypes.length != 1) {
            return false
        }
        ArgType argType = genericTypes[0]
        if (argType.equals(type2)) {
            return true
        }
        if (argType.isGenericType()) {
            registerArg.setType(argType)
            return true
        }
        if (ArgType.isInstanceOf(methodNode.dex(), argType, type2)) {
            return true
        }
        ArgType wildcardType = argType.getWildcardType()
        return wildcardType != null && argType.getWildcardBounds() == 1 && ArgType.isInstanceOf(methodNode.dex(), wildcardType, type2)
    }

    private fun processLoopRegion(MethodNode methodNode, LoopRegion loopRegion) {
        IfCondition condition
        if (!loopRegion.isConditionAtEnd() && (condition = loopRegion.getCondition()) != null && !checkForIndexedLoop(methodNode, loopRegion, condition) && checkIterableForEach(methodNode, loopRegion, condition)) {
        }
    }

    private fun usedOnlyInLoop(MethodNode methodNode, LoopRegion loopRegion, RegisterArg registerArg) {
        Iterator it = registerArg.getSVar().getUseList().iterator()
        while (it.hasNext()) {
            if (!argInLoop(methodNode, loopRegion, (RegisterArg) it.next())) {
                return false
            }
        }
        return true
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun enterRegion(MethodNode methodNode, IRegion iRegion) {
        if (!(iRegion is LoopRegion)) {
            return true
        }
        processLoopRegion(methodNode, (LoopRegion) iRegion)
        return true
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun leaveRegion(MethodNode methodNode, IRegion iRegion) {
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun processBlock(MethodNode methodNode, IBlock iBlock) {
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        DepthRegionTraversal.traverse(methodNode, this)
    }
}

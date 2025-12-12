package jadx.core.dex.visitors.blocksmaker

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.IgnoreEdgeAttr
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.trycatch.CatchAttr
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.SplitterBlockAttr
import jadx.core.dex.trycatch.TryCatchBlock
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.dex.visitors.blocksmaker.helpers.BlocksPair
import jadx.core.dex.visitors.blocksmaker.helpers.BlocksRemoveInfo
import jadx.core.dex.visitors.ssa.LiveVarAnalysis
import jadx.core.utils.BlockUtils
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.BitSet
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedList
import java.util.List
import java.util.Map
import java.util.Set

class BlockFinallyExtract extends AbstractVisitor {
    private fun addIgnoredEdge(BlockNode blockNode, BlockNode blockNode2) {
        IgnoreEdgeAttr ignoreEdgeAttr = (IgnoreEdgeAttr) blockNode.get(AType.IGNORE_EDGE)
        if (ignoreEdgeAttr == null) {
            ignoreEdgeAttr = IgnoreEdgeAttr()
            blockNode.addAttr(ignoreEdgeAttr)
        }
        ignoreEdgeAttr.getBlocks().add(blockNode2)
    }

    private fun applyRemove(MethodNode methodNode, BlocksRemoveInfo blocksRemoveInfo) {
        BlockNode blockNode
        BlocksPair blocksPair
        BlockNode first = blocksRemoveInfo.getStart().getFirst()
        BlockNode second = blocksRemoveInfo.getStart().getSecond()
        if (first.contains(AFlag.REMOVE)) {
            return true
        }
        if (first.getPredecessors().size() != 1) {
            return false
        }
        blocksRemoveInfo.setStartPredecessor((BlockNode) first.getPredecessors().get(0))
        Int startSplitIndex = blocksRemoveInfo.getStartSplitIndex()
        Int endSplitIndex = blocksRemoveInfo.getEndSplitIndex()
        if (blocksRemoveInfo.getStart().equals(blocksRemoveInfo.getEnd())) {
            blocksRemoveInfo.setEndSplitIndex(endSplitIndex - startSplitIndex)
        }
        if (startSplitIndex > 0) {
            BlockNode blockNodeSplitBlock = splitBlock(methodNode, first, startSplitIndex)
            blocksRemoveInfo.getProcessed().remove(blocksRemoveInfo.getStart())
            blocksRemoveInfo.getProcessed().add(BlocksPair(blockNodeSplitBlock, second))
            blockNode = blockNodeSplitBlock
        } else {
            blockNode = first
        }
        if (endSplitIndex > 0) {
            BlockNode blockNodeSplitBlock2 = splitBlock(methodNode, blocksRemoveInfo.getEnd().getFirst(), endSplitIndex)
            for (BlockNode blockNode2 : blockNodeSplitBlock2.getSuccessors()) {
                Iterator it = blocksRemoveInfo.getOuts().iterator()
                while (true) {
                    if (!it.hasNext()) {
                        blocksPair = null
                        break
                    }
                    BlocksPair blocksPair2 = (BlocksPair) it.next()
                    if (blocksPair2.getFirst().equals(blockNode2)) {
                        it.remove()
                        blocksPair = BlocksPair(blockNodeSplitBlock2, blocksPair2.getSecond())
                        break
                    }
                }
                if (blocksPair != null) {
                    blocksRemoveInfo.getOuts().add(blocksPair)
                }
            }
        }
        BlocksPair blocksPair3 = (BlocksPair) blocksRemoveInfo.getOuts().iterator().next()
        BlockNode first2 = blocksPair3.getFirst()
        BlockNode second2 = blocksPair3.getSecond()
        List listFilterPredecessors = BlockUtils.filterPredecessors(second2)
        if (listFilterPredecessors.size() > 1) {
            BlockNode blockNodeInsertBlockBetween = BlockSplitter.insertBlockBetween(methodNode, (BlockNode) second2.getPredecessors().get(0), second2)
            Iterator it2 = ArrayList(second2.getPredecessors()).iterator()
            while (it2.hasNext()) {
                BlockNode blockNode3 = (BlockNode) it2.next()
                if (blockNode3 != blockNodeInsertBlockBetween) {
                    BlockSplitter.removeConnection(blockNode3, second2)
                    BlockSplitter.connect(blockNode3, blockNodeInsertBlockBetween)
                }
            }
            first2.getPredecessors().clear()
            addIgnoredEdge(blockNodeInsertBlockBetween, first2)
            BlockSplitter.connect(blockNodeInsertBlockBetween, first2)
        } else {
            if (listFilterPredecessors.size() != 1) {
                throw JadxRuntimeException("Finally extract failed, unexpected preds: " + listFilterPredecessors + " for " + second2 + ", method: " + methodNode)
            }
            BlockNode blockNode4 = (BlockNode) listFilterPredecessors.get(0)
            BlockNode bySecond = blocksRemoveInfo.getBySecond(blockNode4)
            if (bySecond == null) {
                return false
            }
            BlockSplitter.removeConnection(blockNode4, first2)
            addIgnoredEdge(bySecond, first2)
            BlockSplitter.connect(bySecond, first2)
        }
        Iterator it3 = ArrayList(blockNode.getPredecessors()).iterator()
        while (it3.hasNext()) {
            BlockNode blockNodeInsertBlockBetween2 = BlockSplitter.insertBlockBetween(methodNode, (BlockNode) it3.next(), blockNode)
            BlockSplitter.removeConnection(blockNodeInsertBlockBetween2, blockNode)
            BlockSplitter.connect(blockNodeInsertBlockBetween2, second)
            addIgnoredEdge(blockNodeInsertBlockBetween2, second)
            BlockSplitter.connect(blockNodeInsertBlockBetween2, first2)
        }
        markForRemove(methodNode, blockNode)
        for (BlocksPair blocksPair4 : blocksRemoveInfo.getProcessed()) {
            markForRemove(methodNode, blocksPair4.getFirst())
            blocksPair4.getSecond().updateCleanSuccessors()
        }
        return true
    }

    private fun checkBlocksTree(BlockNode blockNode, BlockNode blockNode2, BlocksRemoveInfo blocksRemoveInfo, BitSet bitSet) {
        if (!blocksRemoveInfo.getProcessed().isEmpty() && !sameBlocks(blockNode, blockNode2, blocksRemoveInfo)) {
            return false
        }
        BlocksPair blocksPair = BlocksPair(blockNode, blockNode2)
        blocksRemoveInfo.getProcessed().add(blocksPair)
        List cleanSuccessors = blockNode2.getCleanSuccessors()
        List cleanSuccessors2 = blockNode.getCleanSuccessors()
        if (cleanSuccessors.size() != cleanSuccessors2.size()) {
            blocksRemoveInfo.getOuts().add(blocksPair)
            return true
        }
        for (Int i = 0; i < cleanSuccessors.size(); i++) {
            BlockNode blockNode3 = (BlockNode) cleanSuccessors.get(i)
            BlockNode blockNode4 = (BlockNode) cleanSuccessors2.get(i)
            if (!bitSet.get(blockNode3.getId())) {
                blocksRemoveInfo.getOuts().add(BlocksPair(blockNode4, blockNode3))
            } else if (blocksRemoveInfo.getEndSplitIndex() != 0 || !checkBlocksTree(blockNode4, blockNode3, blocksRemoveInfo, bitSet)) {
                return false
            }
        }
        return true
    }

    private fun checkFromFirstBlock(BlockNode blockNode, BlockNode blockNode2, BitSet bitSet) {
        BlocksRemoveInfo blocksRemoveInfoIsStartBlock = isStartBlock(blockNode, blockNode2)
        if (blocksRemoveInfoIsStartBlock != null && checkBlocksTree(blockNode, blockNode2, blocksRemoveInfoIsStartBlock, bitSet)) {
            return blocksRemoveInfoIsStartBlock
        }
        return null
    }

    private fun checkInsns(List list, List list2, Int i, BlocksRemoveInfo blocksRemoveInfo) {
        for (Int size = list2.size() - 1; size >= 0; size--) {
            if (!sameInsns((InsnNode) list.get(i + size), (InsnNode) list2.get(size), blocksRemoveInfo)) {
                return false
            }
        }
        return true
    }

    private fun countInstructions(ExceptionHandler exceptionHandler) {
        Iterator it = exceptionHandler.getBlocks().iterator()
        Int size = 0
        while (it.hasNext()) {
            List instructions = ((BlockNode) it.next()).getInstructions()
            if (!instructions.isEmpty() && ((InsnNode) instructions.get(0)).getType() == InsnType.MOVE_EXCEPTION) {
                size--
            }
            size = instructions.size() + size
        }
        return size
    }

    private fun extractFinally(MethodNode methodNode, ExceptionHandler exceptionHandler) {
        LiveVarAnalysis liveVarAnalysis
        LiveVarAnalysis liveVarAnalysis2
        BlocksRemoveInfo blocksRemoveInfoRemoveInsns
        LiveVarAnalysis liveVarAnalysis3 = null
        Boolean z = false
        Int size = exceptionHandler.getBlocks().size()
        BitSet bitSet = BitSet(size)
        ArrayList arrayList = ArrayList(size)
        for (BlockNode blockNode : exceptionHandler.getBlocks()) {
            List instructions = blockNode.getInstructions()
            if (!instructions.isEmpty()) {
                if (((InsnNode) instructions.get(0)).getType() != InsnType.MOVE_EXCEPTION) {
                    arrayList.add(blockNode)
                }
                bitSet.set(blockNode.getId())
            }
        }
        if (arrayList.isEmpty()) {
            return false
        }
        LinkedList linkedList = LinkedList()
        HashSet<BlockNode> hashSet = HashSet()
        TryCatchBlock tryBlock = exceptionHandler.getTryBlock()
        if (tryBlock.getHandlersCount() > 1) {
            for (ExceptionHandler exceptionHandler2 : tryBlock.getHandlers()) {
                if (exceptionHandler2 != exceptionHandler) {
                    Iterator it = exceptionHandler2.getBlocks().iterator()
                    while (true) {
                        if (!it.hasNext()) {
                            break
                        }
                        BlocksRemoveInfo blocksRemoveInfoRemoveInsns2 = removeInsns(methodNode, (BlockNode) it.next(), arrayList, bitSet)
                        if (blocksRemoveInfoRemoveInsns2 != null) {
                            linkedList.add(blocksRemoveInfoRemoveInsns2)
                            break
                        }
                    }
                }
            }
            if (linkedList.size() != tryBlock.getHandlersCount() - 1) {
                return false
            }
        }
        Iterator it2 = tryBlock.getHandlers().iterator()
        while (it2.hasNext()) {
            SplitterBlockAttr splitterBlockAttr = (SplitterBlockAttr) ((ExceptionHandler) it2.next()).getHandlerBlock().get(AType.SPLITTER_BLOCK)
            if (splitterBlockAttr != null) {
                BlockNode block = splitterBlockAttr.getBlock()
                if (!block.getCleanSuccessors().isEmpty()) {
                    hashSet.add(block)
                }
            }
        }
        Boolean z2 = false
        for (BlockNode blockNode2 : hashSet) {
            Iterator it3 = BlockUtils.collectBlocksDominatedBy(blockNode2, (BlockNode) blockNode2.getCleanSuccessors().get(0)).iterator()
            while (true) {
                if (it3.hasNext()) {
                    BlockNode blockNode3 = (BlockNode) it3.next()
                    if (!bitSet.get(blockNode3.getId()) && (blocksRemoveInfoRemoveInsns = removeInsns(methodNode, blockNode3, arrayList, bitSet)) != null) {
                        linkedList.add(blocksRemoveInfoRemoveInsns)
                        z2 = true
                        break
                    }
                }
            }
        }
        if (!z2) {
            return false
        }
        Boolean zIsReMapNeeded = isReMapNeeded(linkedList)
        if (zIsReMapNeeded) {
            LiveVarAnalysis liveVarAnalysis4 = LiveVarAnalysis(methodNode)
            liveVarAnalysis4.runAnalysis()
            liveVarAnalysis = liveVarAnalysis4
        } else {
            liveVarAnalysis = null
        }
        Iterator it4 = linkedList.iterator()
        while (it4.hasNext()) {
            if (!applyRemove(methodNode, (BlocksRemoveInfo) it4.next())) {
                return false
            }
        }
        BlockNode handlerBlock = exceptionHandler.getHandlerBlock()
        InsnNode lastInsn = BlockUtils.getLastInsn(handlerBlock)
        if (lastInsn == null || lastInsn.getType() != InsnType.MOVE_EXCEPTION) {
            liveVarAnalysis2 = null
        } else {
            List instructions2 = handlerBlock.getInstructions()
            if (!handlerBlock.getCleanSuccessors().isEmpty()) {
                liveVarAnalysis3 = LiveVarAnalysis(methodNode)
                liveVarAnalysis3.runAnalysis()
                RegisterArg result = lastInsn.getResult()
                if (liveVarAnalysis3.isLive((BlockNode) handlerBlock.getCleanSuccessors().get(0), result.getRegNum())) {
                    InsnNode insnNode = InsnNode(InsnType.NOP, 0)
                    insnNode.setResult(result)
                    insnNode.add(AFlag.REMOVE)
                    instructions2.set(instructions2.size() - 1, insnNode)
                    z = true
                }
            }
            if (!z) {
                instructions2.remove(instructions2.size() - 1)
                handlerBlock.add(AFlag.SKIP)
            }
            liveVarAnalysis2 = liveVarAnalysis3
        }
        if (zIsReMapNeeded) {
            if (liveVarAnalysis2 == null) {
                liveVarAnalysis2 = LiveVarAnalysis(methodNode)
                liveVarAnalysis2.runAnalysis()
            }
            performVariablesReMap(methodNode, linkedList, liveVarAnalysis, liveVarAnalysis2)
        }
        exceptionHandler.setFinally(true)
        return true
    }

    private fun getFinallyOutBlock(List list) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            BlockNode blockNode = (BlockNode) it.next()
            for (BlockNode blockNode2 : blockNode.getPredecessors()) {
                IgnoreEdgeAttr ignoreEdgeAttr = (IgnoreEdgeAttr) blockNode2.get(AType.IGNORE_EDGE)
                if (ignoreEdgeAttr != null && ignoreEdgeAttr.contains(blockNode)) {
                    return blockNode2
                }
            }
        }
        return null
    }

    private fun injectInsn(MethodNode methodNode, BlockNode blockNode, InsnNode insnNode) {
        insnNode.add(AFlag.SYNTHETIC)
        if (blockNode.getInstructions().isEmpty()) {
            blockNode.getInstructions().add(insnNode)
        } else {
            ((BlockNode) splitBlock(methodNode, blockNode, 0).getPredecessors().get(0)).getInstructions().add(insnNode)
        }
    }

    private fun isReMapNeeded(List list) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            if (!((BlocksRemoveInfo) it.next()).getRegMap().isEmpty()) {
                return true
            }
        }
        return false
    }

    private fun isStartBlock(BlockNode blockNode, BlockNode blockNode2) {
        Int size
        Boolean z = true
        Int i = 0
        List instructions = blockNode.getInstructions()
        List instructions2 = blockNode2.getInstructions()
        if (instructions.size() < instructions2.size()) {
            return null
        }
        Int size2 = instructions.size() - instructions2.size()
        if (checkInsns(instructions, instructions2, size2, null)) {
            i = size2
            size = 0
        } else if (checkInsns(instructions, instructions2, 0, null)) {
            size = instructions2.size()
        } else {
            Int i2 = 1
            while (true) {
                if (i2 >= size2) {
                    z = false
                    i = size2
                    size = 0
                    break
                }
                if (checkInsns(instructions, instructions2, i2, null)) {
                    size = instructions2.size() + i2
                    i = i2
                    break
                }
                i2++
            }
            if (!z) {
                return null
            }
        }
        BlocksPair blocksPair = BlocksPair(blockNode, blockNode2)
        BlocksRemoveInfo blocksRemoveInfo = BlocksRemoveInfo(blocksPair)
        blocksRemoveInfo.setStartSplitIndex(i)
        blocksRemoveInfo.setEndSplitIndex(size)
        if (size != 0) {
            blocksRemoveInfo.setEnd(blocksPair)
        }
        if (checkInsns(instructions, instructions2, i, blocksRemoveInfo)) {
            return blocksRemoveInfo
        }
        return null
    }

    private fun markForRemove(MethodNode methodNode, BlockNode blockNode) {
        for (BlockNode blockNode2 : blockNode.getPredecessors()) {
            blockNode2.getSuccessors().remove(blockNode)
            blockNode2.updateCleanSuccessors()
        }
        Iterator it = blockNode.getSuccessors().iterator()
        while (it.hasNext()) {
            ((BlockNode) it.next()).getPredecessors().remove(blockNode)
        }
        blockNode.getPredecessors().clear()
        blockNode.getSuccessors().clear()
        blockNode.add(AFlag.REMOVE)
        blockNode.remove(AFlag.SKIP)
        CatchAttr catchAttr = (CatchAttr) blockNode.get(AType.CATCH_BLOCK)
        if (catchAttr != null) {
            catchAttr.getTryBlock().removeBlock(methodNode, blockNode)
            for (BlockNode blockNode3 : methodNode.getBasicBlocks()) {
                if (blockNode3.contains(AFlag.SKIP)) {
                    markForRemove(methodNode, blockNode3)
                }
            }
        }
    }

    private fun mergeReturnBlocks(MethodNode methodNode) {
        IgnoreEdgeAttr ignoreEdgeAttr
        BlockNode blockNode
        BlockNode finallyOutBlock = getFinallyOutBlock(methodNode.getExitBlocks())
        if (finallyOutBlock == null || (ignoreEdgeAttr = (IgnoreEdgeAttr) finallyOutBlock.get(AType.IGNORE_EDGE)) == null) {
            return
        }
        LinkedList<BlockNode> linkedList = LinkedList()
        for (BlockNode blockNode2 : finallyOutBlock.getSuccessors()) {
            if (blockNode2.contains(AFlag.RETURN)) {
                linkedList.add(blockNode2)
            }
        }
        if (linkedList.size() >= 2) {
            Iterator it = linkedList.iterator()
            while (true) {
                if (!it.hasNext()) {
                    blockNode = null
                    break
                }
                BlockNode blockNode3 = (BlockNode) it.next()
                if (blockNode3.contains(AFlag.ORIG_RETURN)) {
                    blockNode = blockNode3
                    break
                }
            }
            if (blockNode != null) {
                for (BlockNode blockNode4 : linkedList) {
                    if (blockNode4 != blockNode) {
                        Iterator it2 = blockNode4.getPredecessors().iterator()
                        while (it2.hasNext()) {
                            BlockSplitter.connect((BlockNode) it2.next(), blockNode)
                        }
                        markForRemove(methodNode, blockNode4)
                        ignoreEdgeAttr.getBlocks().remove(blockNode4)
                    }
                }
                mergeSyntheticPredecessors(methodNode, blockNode)
            }
        }
    }

    private fun mergeReturns(MethodNode methodNode, Set set) {
        HashSet hashSet = HashSet()
        Iterator it = set.iterator()
        Boolean z = true
        while (it.hasNext()) {
            BlocksPair blocksPair = (BlocksPair) it.next()
            if (!blocksPair.getFirst().isReturnBlock()) {
                z = false
            }
            hashSet.add(blocksPair.getSecond())
        }
        if (!z || hashSet.size() != 1) {
            return false
        }
        Iterator it2 = set.iterator()
        while (it2.hasNext()) {
            BlockNode first = ((BlocksPair) it2.next()).getFirst()
            if (!first.contains(AFlag.ORIG_RETURN)) {
                markForRemove(methodNode, first)
                it2.remove()
            }
        }
        return true
    }

    private fun mergeSyntheticPredecessors(MethodNode methodNode, BlockNode blockNode) {
        BlockNode blockNode2 = null
        ArrayList<BlockNode> arrayList = ArrayList(blockNode.getPredecessors())
        Iterator it = arrayList.iterator()
        while (it.hasNext()) {
            if (!((BlockNode) it.next()).isSynthetic()) {
                it.remove()
            }
        }
        if (arrayList.size() < 2) {
            return
        }
        BlockNode blockNode3 = null
        for (BlockNode blockNode4 : arrayList) {
            List successors = blockNode4.getSuccessors()
            if (successors.size() != 2) {
                return
            }
            BlockNode blockNodeSelectOtherSafe = BlockUtils.selectOtherSafe(blockNode, successors)
            if (blockNode3 != null) {
                if (blockNodeSelectOtherSafe != blockNode3) {
                    return
                } else {
                    blockNodeSelectOtherSafe = blockNode3
                }
            }
            if (!blockNode4.contains(AType.IGNORE_EDGE)) {
                return
            } else {
                blockNode3 = blockNodeSelectOtherSafe
            }
        }
        if (blockNode3 != null) {
            for (BlockNode blockNode5 : arrayList) {
                if (blockNode2 == null) {
                    blockNode2 = blockNode5
                } else {
                    Iterator it2 = blockNode5.getPredecessors().iterator()
                    while (it2.hasNext()) {
                        BlockSplitter.connect((BlockNode) it2.next(), blockNode2)
                    }
                    markForRemove(methodNode, blockNode5)
                }
            }
        }
    }

    private fun performVariablesReMap(MethodNode methodNode, List list, LiveVarAnalysis liveVarAnalysis, LiveVarAnalysis liveVarAnalysis2) {
        BitSet bitSet = BitSet(methodNode.getRegsCount())
        Iterator it = list.iterator()
        while (it.hasNext()) {
            BlocksRemoveInfo blocksRemoveInfo = (BlocksRemoveInfo) it.next()
            bitSet.clear()
            BlocksPair start = blocksRemoveInfo.getStart()
            BlockNode first = start.getFirst()
            BlockNode second = start.getSecond()
            if (!blocksRemoveInfo.getRegMap().isEmpty() && second != null) {
                for (Map.Entry entry : blocksRemoveInfo.getRegMap().entrySet()) {
                    RegisterArg registerArg = (RegisterArg) entry.getKey()
                    RegisterArg registerArg2 = (RegisterArg) entry.getValue()
                    Int regNum = registerArg.getRegNum()
                    Int regNum2 = registerArg2.getRegNum()
                    if (!bitSet.get(regNum)) {
                        Boolean zIsLive = liveVarAnalysis.isLive(first, regNum)
                        Boolean zIsLive2 = liveVarAnalysis2.isLive(second, regNum)
                        if (liveVarAnalysis2.isLive(second, regNum2) && zIsLive) {
                            InsnNode insnNode = InsnNode(InsnType.MERGE, 2)
                            insnNode.setResult(registerArg2.duplicate())
                            insnNode.addArg(registerArg2.duplicate())
                            insnNode.addArg(registerArg.duplicate())
                            injectInsn(methodNode, second, insnNode)
                        } else if (zIsLive) {
                            InsnNode insnNode2 = InsnNode(InsnType.MOVE, 1)
                            insnNode2.setResult(registerArg2.duplicate())
                            insnNode2.addArg(registerArg.duplicate())
                            injectInsn(methodNode, second, insnNode2)
                        } else if (zIsLive2) {
                            InsnNode insnNode3 = InsnNode(InsnType.NOP, 0)
                            insnNode3.setResult(registerArg.duplicate())
                            insnNode3.add(AFlag.REMOVE)
                            injectInsn(methodNode, second, insnNode3)
                        }
                        bitSet.set(regNum)
                    }
                }
            }
        }
    }

    private fun processExceptionHandler(MethodNode methodNode, ExceptionHandler exceptionHandler) {
        Boolean z
        Boolean z2 = false
        Boolean zContainsAll = true
        for (BlockNode blockNode : exceptionHandler.getBlocks()) {
            if (zContainsAll) {
                zContainsAll = exceptionHandler.getBlocks().containsAll(blockNode.getCleanSuccessors())
            }
            List instructions = blockNode.getInstructions()
            Int size = instructions.size()
            if (exceptionHandler.isCatchAll() && size != 0 && ((InsnNode) instructions.get(size - 1)).getType() == InsnType.THROW) {
                instructions.remove(size - 1)
                z = true
            } else {
                z = z2
            }
            z2 = z
        }
        if (z2 && zContainsAll && extractFinally(methodNode, exceptionHandler)) {
            return true
        }
        if (countInstructions(exceptionHandler) == 0 && z2 && zContainsAll) {
            exceptionHandler.getTryBlock().removeHandler(methodNode, exceptionHandler)
        }
        return false
    }

    private fun removeInsns(MethodNode methodNode, BlockNode blockNode, List list, BitSet bitSet) {
        BlocksRemoveInfo blocksRemoveInfoCheckFromFirstBlock
        if (!list.isEmpty() && (blocksRemoveInfoCheckFromFirstBlock = checkFromFirstBlock(blockNode, (BlockNode) list.get(0), bitSet)) != null) {
            Set outs = blocksRemoveInfoCheckFromFirstBlock.getOuts()
            if (outs.size() == 1 || mergeReturns(methodNode, outs)) {
                return blocksRemoveInfoCheckFromFirstBlock
            }
            return null
        }
        return null
    }

    private fun sameBlocks(BlockNode blockNode, BlockNode blockNode2, BlocksRemoveInfo blocksRemoveInfo) {
        List instructions = blockNode.getInstructions()
        List instructions2 = blockNode2.getInstructions()
        if (instructions.size() < instructions2.size()) {
            return false
        }
        Int size = instructions2.size()
        for (Int i = 0; i < size; i++) {
            if (!sameInsns((InsnNode) instructions.get(i), (InsnNode) instructions2.get(i), blocksRemoveInfo)) {
                return false
            }
        }
        if (instructions.size() > instructions2.size()) {
            blocksRemoveInfo.setEndSplitIndex(instructions2.size())
            blocksRemoveInfo.setEnd(BlocksPair(blockNode, blockNode2))
        }
        return true
    }

    private fun sameInsns(InsnNode insnNode, InsnNode insnNode2, BlocksRemoveInfo blocksRemoveInfo) {
        if (!insnNode.isSame(insnNode2)) {
            return false
        }
        for (Int i = 0; i < insnNode.getArgsCount(); i++) {
            InsnArg arg = insnNode.getArg(i)
            InsnArg arg2 = insnNode2.getArg(i)
            if (arg.isRegister() != arg2.isRegister()) {
                return false
            }
            if (blocksRemoveInfo != null && arg2.isRegister()) {
                RegisterArg registerArg = (RegisterArg) arg
                RegisterArg registerArg2 = (RegisterArg) arg2
                if (registerArg.getRegNum() == registerArg2.getRegNum()) {
                    continue
                } else {
                    RegisterArg registerArg3 = (RegisterArg) blocksRemoveInfo.getRegMap().get(arg)
                    if (registerArg3 == null) {
                        blocksRemoveInfo.getRegMap().put(registerArg, registerArg2)
                    } else if (!registerArg3.equalRegisterAndType(registerArg2)) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun splitBlock(MethodNode methodNode, BlockNode blockNode, Int i) {
        BlockNode blockNodeStartNewBlock = BlockSplitter.startNewBlock(methodNode, -1)
        blockNodeStartNewBlock.getSuccessors().addAll(blockNode.getSuccessors())
        Iterator it = ArrayList(blockNode.getSuccessors()).iterator()
        while (it.hasNext()) {
            BlockNode blockNode2 = (BlockNode) it.next()
            BlockSplitter.removeConnection(blockNode, blockNode2)
            BlockSplitter.connect(blockNodeStartNewBlock, blockNode2)
        }
        blockNode.getSuccessors().clear()
        BlockSplitter.connect(blockNode, blockNodeStartNewBlock)
        blockNode.updateCleanSuccessors()
        blockNodeStartNewBlock.updateCleanSuccessors()
        List instructions = blockNode.getInstructions()
        Int size = instructions.size()
        while (i < size) {
            InsnNode insnNode = (InsnNode) instructions.get(i)
            insnNode.add(AFlag.SKIP)
            blockNodeStartNewBlock.getInstructions().add(insnNode)
            i++
        }
        Iterator it2 = instructions.iterator()
        while (it2.hasNext()) {
            if (((InsnNode) it2.next()).contains(AFlag.SKIP)) {
                it2.remove()
            }
        }
        Iterator it3 = blockNodeStartNewBlock.getInstructions().iterator()
        while (it3.hasNext()) {
            ((InsnNode) it3.next()).remove(AFlag.SKIP)
        }
        return blockNodeStartNewBlock
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        Boolean z
        if (methodNode.isNoCode() || methodNode.isNoExceptionHandlers()) {
            return
        }
        Boolean z2 = false
        Iterator it = methodNode.getExceptionHandlers().iterator()
        while (true) {
            z = z2
            if (!it.hasNext()) {
                break
            } else {
                z2 = processExceptionHandler(methodNode, (ExceptionHandler) it.next()) ? true : z
            }
        }
        if (z) {
            mergeReturnBlocks(methodNode)
            BlockProcessor.rerun(methodNode)
        }
    }
}

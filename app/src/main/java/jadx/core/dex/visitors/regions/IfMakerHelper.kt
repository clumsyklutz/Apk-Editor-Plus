package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.LoopInfo
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.conditions.IfCondition
import jadx.core.dex.regions.conditions.IfInfo
import jadx.core.utils.BlockUtils
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Collection
import java.util.Iterator
import java.util.List
import java.util.Set

class IfMakerHelper {
    private constructor() {
    }

    private fun allPathsFromIf(BlockNode blockNode, IfInfo ifInfo) {
        List predecessors = blockNode.getPredecessors()
        Set mergedBlocks = ifInfo.getMergedBlocks()
        Iterator it = predecessors.iterator()
        while (it.hasNext()) {
            BlockNode blockNodeSkipSyntheticPredecessor = BlockUtils.skipSyntheticPredecessor((BlockNode) it.next())
            if (!mergedBlocks.contains(blockNodeSkipSyntheticPredecessor) && !blockNodeSkipSyntheticPredecessor.contains(AFlag.LOOP_END)) {
                return false
            }
        }
        return true
    }

    private fun canMerge(IfInfo ifInfo, IfInfo ifInfo2, Boolean z) {
        return z ? RegionMaker.isEqualPaths(ifInfo.getElseBlock(), ifInfo2.getElseBlock()) : RegionMaker.isEqualPaths(ifInfo.getThenBlock(), ifInfo2.getThenBlock())
    }

    private fun canSelectNext(IfInfo ifInfo, BlockNode blockNode) {
        return blockNode.getPredecessors().size() == 1 || ifInfo.getMergedBlocks().containsAll(blockNode.getPredecessors())
    }

    private fun checkConditionBranches(BlockNode blockNode, BlockNode blockNode2) {
        return blockNode.getCleanSuccessors().size() == 1 && blockNode.getCleanSuccessors().contains(blockNode2)
    }

    private fun checkForTernaryInCondition(IfInfo ifInfo) {
        IfInfo nextIf = getNextIf(ifInfo, ifInfo.getThenBlock())
        IfInfo nextIf2 = getNextIf(ifInfo, ifInfo.getElseBlock())
        if (nextIf == null || nextIf2 == null || !nextIf.getIfBlock().getDomFrontier().equals(nextIf2.getIfBlock().getDomFrontier())) {
            return null
        }
        IfInfo ifInfoSearchNestedIf = searchNestedIf(nextIf)
        IfInfo ifInfoSearchNestedIf2 = searchNestedIf(nextIf2)
        if (ifInfoSearchNestedIf.getThenBlock() == ifInfoSearchNestedIf2.getThenBlock() && ifInfoSearchNestedIf.getElseBlock() == ifInfoSearchNestedIf2.getElseBlock()) {
            return mergeTernaryConditions(ifInfo, ifInfoSearchNestedIf, ifInfoSearchNestedIf2)
        }
        if (ifInfoSearchNestedIf.getThenBlock() == ifInfoSearchNestedIf2.getElseBlock() && ifInfoSearchNestedIf.getElseBlock() == ifInfoSearchNestedIf2.getThenBlock()) {
            return mergeTernaryConditions(ifInfo, ifInfoSearchNestedIf, IfInfo.invert(ifInfoSearchNestedIf2))
        }
        return null
    }

    static Unit confirmMerge(IfInfo ifInfo) {
        if (ifInfo.getMergedBlocks().size() > 1) {
            for (BlockNode blockNode : ifInfo.getMergedBlocks()) {
                if (blockNode != ifInfo.getIfBlock()) {
                    blockNode.add(AFlag.SKIP)
                }
            }
        }
        if (ifInfo.getSkipBlocks().isEmpty()) {
            return
        }
        Iterator it = ifInfo.getSkipBlocks().iterator()
        while (it.hasNext()) {
            ((BlockNode) it.next()).add(AFlag.SKIP)
        }
        ifInfo.getSkipBlocks().clear()
    }

    private fun getCrossBlock(BlockNode blockNode, BlockNode blockNode2) {
        if (!isSameBlocks(blockNode, blockNode2)) {
            BlockNode blockNodeSkipSyntheticSuccessor = BlockUtils.skipSyntheticSuccessor(blockNode)
            if (!isSameBlocks(blockNodeSkipSyntheticSuccessor, blockNode2)) {
                blockNode2 = BlockUtils.skipSyntheticSuccessor(blockNode2)
                if (!isSameBlocks(blockNodeSkipSyntheticSuccessor, blockNode2) && !isSameBlocks(blockNode, blockNode2)) {
                    throw JadxRuntimeException("Unexpected merge pattern")
                }
            }
        }
        return blockNode2
    }

    private fun getNextIf(IfInfo ifInfo, BlockNode blockNode) {
        BlockNode nextIfNode
        if (canSelectNext(ifInfo, blockNode) && (nextIfNode = getNextIfNode(blockNode)) != null) {
            return makeIfInfo(nextIfNode)
        }
        return null
    }

    private fun getNextIfNode(BlockNode blockNode) {
        Boolean z
        BlockNode blockNode2 = blockNode
        while (blockNode2 != null && !blockNode2.contains(AType.LOOP) && !blockNode2.contains(AFlag.SKIP)) {
            List instructions = blockNode2.getInstructions()
            if (instructions.size() == 1 && ((InsnNode) instructions.get(0)).getType() == InsnType.IF) {
                return blockNode2
            }
            List successors = blockNode2.getSuccessors()
            if (successors.size() != 1) {
                return null
            }
            BlockNode blockNode3 = (BlockNode) successors.get(0)
            if (blockNode3.getPredecessors().size() != 1) {
                return null
            }
            if (instructions.isEmpty()) {
                z = true
            } else {
                Iterator it = instructions.iterator()
                while (it.hasNext()) {
                    RegisterArg result = ((InsnNode) it.next()).getResult()
                    if (result != null) {
                        List useList = result.getSVar().getUseList()
                        if (useList.size() == 1) {
                            InsnNode parentInsn = ((InsnArg) useList.get(0)).getParentInsn()
                            if (!BlockUtils.blockContains(blockNode2, parentInsn) && !BlockUtils.blockContains(blockNode3, parentInsn)) {
                                z = false
                                break
                            }
                        } else {
                            z = false
                            break
                        }
                    } else {
                        z = false
                        break
                    }
                }
                z = true
            }
            if (!z) {
                return null
            }
            blockNode2 = blockNode3
        }
        return null
    }

    private fun isBadBranchBlock(IfInfo ifInfo, BlockNode blockNode) {
        if (blockNode.contains(AFlag.LOOP_START) && blockNode.getPredecessors().size() == 1) {
            BlockNode blockNode2 = (BlockNode) blockNode.getPredecessors().get(0)
            if (blockNode2.contains(AFlag.LOOP_END)) {
                List<LoopInfo> all = blockNode.getAll(AType.LOOP)
                List all2 = blockNode2.getAll(AType.LOOP)
                for (LoopInfo loopInfo : all) {
                    Iterator it = all2.iterator()
                    while (it.hasNext()) {
                        if (loopInfo == ((LoopInfo) it.next())) {
                            return true
                        }
                    }
                }
            }
        }
        return !allPathsFromIf(blockNode, ifInfo)
    }

    private fun isInversionNeeded(IfInfo ifInfo, IfInfo ifInfo2) {
        return RegionMaker.isEqualPaths(ifInfo.getElseBlock(), ifInfo2.getThenBlock()) || RegionMaker.isEqualPaths(ifInfo.getThenBlock(), ifInfo2.getElseBlock())
    }

    private fun isSameBlocks(BlockNode blockNode, BlockNode blockNode2) {
        return blockNode == blockNode2 || RegionMaker.isEqualReturnBlocks(blockNode, blockNode2)
    }

    static IfInfo makeIfInfo(BlockNode blockNode) {
        IfNode ifNode = (IfNode) blockNode.getInstructions().get(0)
        IfInfo ifInfo = IfInfo(IfCondition.fromIfNode(ifNode), ifNode.getThenBlock(), ifNode.getElseBlock())
        ifInfo.setIfBlock(blockNode)
        ifInfo.getMergedBlocks().add(blockNode)
        return ifInfo
    }

    private fun mergeIfInfo(IfInfo ifInfo, IfInfo ifInfo2, Boolean z) {
        BlockNode crossBlock
        BlockNode elseBlock
        IfCondition ifConditionMerge = IfCondition.merge(z ? IfCondition.Mode.AND : IfCondition.Mode.OR, ifInfo.getCondition(), ifInfo2.getCondition())
        if (z) {
            crossBlock = ifInfo2.getThenBlock()
            elseBlock = getCrossBlock(ifInfo.getElseBlock(), ifInfo2.getElseBlock())
        } else {
            crossBlock = getCrossBlock(ifInfo.getThenBlock(), ifInfo2.getThenBlock())
            elseBlock = ifInfo2.getElseBlock()
        }
        IfInfo ifInfo3 = IfInfo(ifConditionMerge, crossBlock, elseBlock)
        ifInfo3.setIfBlock(ifInfo.getIfBlock())
        ifInfo3.merge(ifInfo, ifInfo2)
        skipSimplePath(z ? ifInfo.getElseBlock() : ifInfo.getThenBlock(), ifInfo3.getSkipBlocks())
        return ifInfo3
    }

    static IfInfo mergeNestedIfNodes(IfInfo ifInfo) {
        Boolean z
        BlockNode thenBlock = ifInfo.getThenBlock()
        BlockNode elseBlock = ifInfo.getElseBlock()
        if (thenBlock == elseBlock) {
            return null
        }
        IfInfo nextIf = getNextIf(ifInfo, thenBlock)
        if (nextIf != null) {
            z = true
        } else {
            nextIf = getNextIf(ifInfo, elseBlock)
            if (nextIf == null) {
                return null
            }
            z = false
        }
        if (isInversionNeeded(ifInfo, nextIf)) {
            nextIf = IfInfo.invert(nextIf)
        }
        if (!RegionMaker.isEqualPaths(thenBlock, nextIf.getThenBlock()) && !RegionMaker.isEqualPaths(elseBlock, nextIf.getElseBlock())) {
            if (checkConditionBranches(thenBlock, elseBlock) || checkConditionBranches(elseBlock, thenBlock)) {
                return null
            }
            if (!z) {
                elseBlock = thenBlock
            }
            if (!BlockUtils.isPathExists(nextIf.getIfBlock(), BlockUtils.skipSyntheticSuccessor(elseBlock))) {
                return checkForTernaryInCondition(ifInfo)
            }
            nextIf = mergeNestedIfNodes(nextIf)
            if (nextIf == null) {
                return ifInfo
            }
            if (isInversionNeeded(ifInfo, nextIf)) {
                nextIf = IfInfo.invert(nextIf)
            }
            if (!canMerge(ifInfo, nextIf, z)) {
                return ifInfo
            }
        }
        return searchNestedIf(mergeIfInfo(ifInfo, nextIf, z))
    }

    private fun mergeTernaryConditions(IfInfo ifInfo, IfInfo ifInfo2, IfInfo ifInfo3) {
        IfInfo ifInfo4 = IfInfo(IfCondition.ternary(ifInfo.getCondition(), ifInfo2.getCondition(), ifInfo3.getCondition()), ifInfo2.getThenBlock(), ifInfo2.getElseBlock())
        ifInfo4.setIfBlock(ifInfo.getIfBlock())
        ifInfo4.merge(ifInfo, ifInfo2, ifInfo3)
        confirmMerge(ifInfo4)
        return ifInfo4
    }

    static IfInfo restructureIf(MethodNode methodNode, BlockNode blockNode, IfInfo ifInfo) {
        BlockNode thenBlock = ifInfo.getThenBlock()
        BlockNode elseBlock = ifInfo.getElseBlock()
        if (thenBlock.contains(AFlag.RETURN) && elseBlock.contains(AFlag.RETURN)) {
            ifInfo.setOutBlock(null)
            return ifInfo
        }
        Boolean zIsBadBranchBlock = isBadBranchBlock(ifInfo, thenBlock)
        Boolean zIsBadBranchBlock2 = isBadBranchBlock(ifInfo, elseBlock)
        if (zIsBadBranchBlock && zIsBadBranchBlock2) {
            return null
        }
        if (zIsBadBranchBlock2) {
            IfInfo ifInfo2 = IfInfo(ifInfo, thenBlock, (BlockNode) null)
            ifInfo2.setOutBlock(elseBlock)
            ifInfo = ifInfo2
        } else if (zIsBadBranchBlock) {
            ifInfo = IfInfo(IfInfo.invert(ifInfo), elseBlock, (BlockNode) null)
            ifInfo.setOutBlock(thenBlock)
        } else {
            List cleanSuccessors = thenBlock.getCleanSuccessors()
            List cleanSuccessors2 = elseBlock.getCleanSuccessors()
            if (cleanSuccessors.size() == 1 && sameElements(cleanSuccessors, cleanSuccessors2)) {
                ifInfo.setOutBlock((BlockNode) cleanSuccessors.get(0))
            } else if (ifInfo.getMergedBlocks().size() == 1 && blockNode.getDominatesOn().size() == 2) {
                ifInfo.setOutBlock(BlockUtils.getPathCross(methodNode, thenBlock, elseBlock))
            }
        }
        if (ifInfo.getOutBlock() == null) {
            Iterator it = blockNode.getDominatesOn().iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                BlockNode blockNode2 = (BlockNode) it.next()
                if (blockNode2 != thenBlock && blockNode2 != elseBlock && !ifInfo.getMergedBlocks().contains(blockNode2) && BlockUtils.isPathExists(thenBlock, blockNode2)) {
                    ifInfo.setOutBlock(blockNode2)
                    break
                }
            }
        }
        if (!BlockUtils.isBackEdge(blockNode, ifInfo.getOutBlock())) {
            return ifInfo
        }
        ifInfo.setOutBlock(null)
        return ifInfo
    }

    private fun sameElements(Collection collection, Collection collection2) {
        return collection.size() == collection2.size() && collection.containsAll(collection2)
    }

    static IfInfo searchNestedIf(IfInfo ifInfo) {
        IfInfo ifInfoMergeNestedIfNodes = mergeNestedIfNodes(ifInfo)
        return ifInfoMergeNestedIfNodes != null ? ifInfoMergeNestedIfNodes : ifInfo
    }

    private fun skipSimplePath(BlockNode blockNode, Set set) {
        while (blockNode != null && blockNode.getCleanSuccessors().size() < 2 && blockNode.getPredecessors().size() == 1) {
            set.add(blockNode)
            blockNode = BlockUtils.getNextBlock(blockNode)
        }
    }
}

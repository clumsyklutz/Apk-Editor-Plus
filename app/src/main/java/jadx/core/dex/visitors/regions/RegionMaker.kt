package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.EdgeInsnAttr
import jadx.core.dex.attributes.nodes.LoopInfo
import jadx.core.dex.attributes.nodes.LoopLabelAttr
import jadx.core.dex.instructions.IfNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.Edge
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.Region
import jadx.core.dex.regions.SynchronizedRegion
import jadx.core.dex.regions.conditions.IfInfo
import jadx.core.dex.regions.conditions.IfRegion
import jadx.core.dex.regions.loops.LoopRegion
import jadx.core.dex.trycatch.ExcHandlerAttr
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.SplitterBlockAttr
import jadx.core.dex.trycatch.TryCatchBlock
import jadx.core.utils.BlockUtils
import jadx.core.utils.ErrorsCounter
import jadx.core.utils.InstructionRemover
import jadx.core.utils.RegionUtils
import jadx.core.utils.exceptions.JadxOverflowException
import java.util.ArrayList
import java.util.BitSet
import java.util.Collections
import java.util.Comparator
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import java.util.Set

class RegionMaker {
    private static val REGIONS_LIMIT = 1000000
    private final MethodNode mth
    private BitSet processedBlocks
    private Int regionsCount

    constructor(MethodNode methodNode) {
        this.mth = methodNode
    }

    private fun addBreakLabel(Edge edge, BlockNode blockNode, InsnNode insnNode) {
        LoopInfo loopInfo
        BlockNode nextBlock = BlockUtils.getNextBlock(edge.getTarget())
        if (nextBlock != null && this.mth.getAllLoopsForBlock(nextBlock).isEmpty()) {
            List allLoopsForBlock = this.mth.getAllLoopsForBlock(edge.getSource())
            if (allLoopsForBlock.size() >= 2) {
                Iterator it = allLoopsForBlock.iterator()
                while (true) {
                    if (!it.hasNext()) {
                        loopInfo = null
                        break
                    } else {
                        loopInfo = (LoopInfo) it.next()
                        if (loopInfo.getParentLoop() == null) {
                            break
                        }
                    }
                }
                if (loopInfo == null || loopInfo.getEnd() == blockNode || loopInfo.getExitNodes().contains(blockNode)) {
                    return
                }
                LoopLabelAttr loopLabelAttr = LoopLabelAttr(loopInfo)
                insnNode.addAttr(loopLabelAttr)
                loopInfo.getStart().addAttr(loopLabelAttr)
            }
        }
    }

    private fun addEdgeInsn(IfInfo ifInfo, Region region, EdgeInsnAttr edgeInsnAttr) {
        Boolean z
        BlockNode start = edgeInsnAttr.getStart()
        if (start.contains(AFlag.SKIP)) {
            return
        }
        Iterator it = ifInfo.getMergedBlocks().iterator()
        while (true) {
            if (!it.hasNext()) {
                z = false
                break
            } else if (((BlockNode) it.next()).getSuccessors().contains(start)) {
                z = true
                break
            }
        }
        if (z) {
            region.add(start)
        }
    }

    private fun canInsertBreak(BlockNode blockNode) {
        if (blockNode.contains(AFlag.RETURN) || BlockUtils.checkLastInsnType(blockNode, InsnType.BREAK)) {
            return false
        }
        List listBuildSimplePath = BlockUtils.buildSimplePath(blockNode)
        if (!listBuildSimplePath.isEmpty()) {
            BlockNode blockNode2 = (BlockNode) listBuildSimplePath.get(listBuildSimplePath.size() - 1)
            if (blockNode2.contains(AFlag.RETURN) || blockNode2.getSuccessors().isEmpty()) {
                return false
            }
        }
        Iterator it = BlockUtils.getAllPathsBlocks(this.mth.getEnterBlock(), blockNode).iterator()
        while (it.hasNext()) {
            if (BlockUtils.checkLastInsnType((BlockNode) it.next(), InsnType.SWITCH)) {
                return false
            }
        }
        return true
    }

    private fun canInsertContinue(BlockNode blockNode, List list, BlockNode blockNode2, Set set) {
        Boolean z
        if (!blockNode.contains(AFlag.SYNTHETIC) || BlockUtils.checkLastInsnType(blockNode, InsnType.CONTINUE)) {
            return false
        }
        List predecessors = blockNode.getPredecessors()
        if (predecessors.isEmpty()) {
            return false
        }
        BlockNode blockNode3 = (BlockNode) predecessors.get(0)
        if (blockNode3.contains(AFlag.SKIP) || blockNode2.isDominator(blockNode3) || set.contains(blockNode3) || isDominatedOnBlocks(blockNode3, list)) {
            return false
        }
        Iterator it = set.iterator()
        while (true) {
            if (!it.hasNext()) {
                z = false
                break
            }
            if (BlockUtils.isPathExists(blockNode3, (BlockNode) it.next())) {
                z = true
                break
            }
        }
        return z
    }

    private fun inExceptionHandlerBlocks(BlockNode blockNode) {
        if (this.mth.getExceptionHandlersCount() == 0) {
            return false
        }
        Iterator it = this.mth.getExceptionHandlers().iterator()
        while (it.hasNext()) {
            if (((ExceptionHandler) it.next()).getBlocks().contains(blockNode)) {
                return true
            }
        }
        return false
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun insertBreak(jadx.core.dex.visitors.regions.RegionStack r9, jadx.core.dex.nodes.BlockNode r10, jadx.core.dex.nodes.Edge r11) {
        /*
            r8 = this
            r1 = 0
            r3 = 1
            r2 = 0
            jadx.core.dex.nodes.BlockNode r5 = r11.getTarget()
            if (r10 != r5) goto L88
            jadx.core.dex.nodes.BlockNode r0 = r11.getSource()
            jadx.core.dex.attributes.AType r4 = jadx.core.dex.attributes.AType.CATCH_BLOCK
            Boolean r4 = r0.contains(r4)
            if (r4 == 0) goto L88
            java.util.List r4 = r0.getSuccessors()
            Int r4 = r4.size()
            r6 = 2
            if (r4 != r6) goto L88
            java.util.List r4 = r0.getSuccessors()
            jadx.core.dex.nodes.BlockNode r4 = jadx.core.utils.BlockUtils.selectOther(r10, r4)
            if (r4 == 0) goto L88
            jadx.core.dex.nodes.BlockNode r4 = jadx.core.utils.BlockUtils.skipSyntheticSuccessor(r4)
            jadx.core.dex.attributes.AType r6 = jadx.core.dex.attributes.AType.EXC_HANDLER
            Boolean r4 = r4.contains(r6)
            if (r4 == 0) goto L88
            r4 = r3
        L37:
            if (r4 != 0) goto L84
        L39:
            if (r5 == 0) goto L84
            if (r0 == 0) goto L52
            Boolean r6 = jadx.core.utils.BlockUtils.isPathExists(r10, r5)
            if (r6 == 0) goto L52
            Boolean r1 = r8.canInsertBreak(r0)
            if (r1 == 0) goto L50
            r1 = r0
            r4 = r5
            r0 = r3
        L4c:
            if (r0 != 0) goto L68
            r0 = r2
        L4f:
            return r0
        L50:
            r0 = r2
            goto L4f
        L52:
            java.util.List r0 = r5.getCleanSuccessors()
            Int r6 = r0.size()
            if (r6 != r3) goto L66
            java.lang.Object r0 = r0.get(r2)
            jadx.core.dex.nodes.BlockNode r0 = (jadx.core.dex.nodes.BlockNode) r0
        L62:
            r7 = r5
            r5 = r0
            r0 = r7
            goto L39
        L66:
            r0 = r1
            goto L62
        L68:
            jadx.core.dex.nodes.InsnNode r5 = new jadx.core.dex.nodes.InsnNode
            jadx.core.dex.instructions.InsnType r0 = jadx.core.dex.instructions.InsnType.BREAK
            r5.<init>(r0, r2)
            java.util.List r0 = r1.getSuccessors()
            java.lang.Object r0 = r0.get(r2)
            jadx.core.dex.nodes.BlockNode r0 = (jadx.core.dex.nodes.BlockNode) r0
            jadx.core.dex.attributes.nodes.EdgeInsnAttr.addEdgeInsn(r1, r0, r5)
            r9.addExit(r4)
            r8.addBreakLabel(r11, r4, r5)
            r0 = r3
            goto L4f
        L84:
            r1 = r0
            r0 = r4
            r4 = r5
            goto L4c
        L88:
            r4 = r2
            r0 = r1
            goto L37
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.core.dex.visitors.regions.RegionMaker.insertBreak(jadx.core.dex.visitors.regions.RegionStack, jadx.core.dex.nodes.BlockNode, jadx.core.dex.nodes.Edge):Boolean")
    }

    private fun insertContinue(LoopInfo loopInfo) {
        BlockNode end = loopInfo.getEnd()
        List<BlockNode> predecessors = end.getPredecessors()
        if (predecessors.size() <= 1) {
            return
        }
        Set exitNodes = loopInfo.getExitNodes()
        for (BlockNode blockNode : predecessors) {
            if (canInsertContinue(blockNode, predecessors, end, exitNodes)) {
                blockNode.getInstructions().add(InsnNode(InsnType.CONTINUE, 0))
            }
        }
    }

    private fun insertContinueInSwitch(BlockNode blockNode, BlockNode blockNode2, BlockNode blockNode3) {
        Int id = blockNode3.getId()
        for (BlockNode blockNode4 : blockNode.getCleanSuccessors()) {
            if (blockNode4.getDomFrontier().get(id) && blockNode4 != blockNode2) {
                List listCollectBlocksDominatedBy = BlockUtils.collectBlocksDominatedBy(blockNode4, blockNode4)
                Iterator it = blockNode3.getPredecessors().iterator()
                while (true) {
                    if (it.hasNext()) {
                        BlockNode blockNode5 = (BlockNode) it.next()
                        if (listCollectBlocksDominatedBy.contains(blockNode5)) {
                            if (blockNode5.isSynthetic()) {
                                blockNode5.getInstructions().add(InsnNode(InsnType.CONTINUE, 0))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isBadCasesOrder(Map map, Map map2) {
        BlockNode blockNode = null
        Iterator it = map.keySet().iterator()
        while (true) {
            BlockNode blockNode2 = blockNode
            if (!it.hasNext()) {
                return blockNode2 != null
            }
            BlockNode blockNode3 = (BlockNode) it.next()
            if (blockNode2 != null && !blockNode3.equals(blockNode2)) {
                return true
            }
            blockNode = (BlockNode) map2.get(blockNode3)
        }
    }

    private fun isDominatedOnBlocks(BlockNode blockNode, List list) {
        Iterator it = list.iterator()
        while (it.hasNext()) {
            if (!((BlockNode) it.next()).isDominator(blockNode)) {
                return false
            }
        }
        return true
    }

    static Boolean isEqualPaths(BlockNode blockNode, BlockNode blockNode2) {
        if (blockNode == blockNode2) {
            return true
        }
        if (blockNode == null || blockNode2 == null) {
            return false
        }
        return isEqualReturnBlocks(blockNode, blockNode2) || isSyntheticPath(blockNode, blockNode2)
    }

    fun isEqualReturnBlocks(BlockNode blockNode, BlockNode blockNode2) {
        if (!blockNode.isReturnBlock() || !blockNode2.isReturnBlock()) {
            return false
        }
        List instructions = blockNode.getInstructions()
        List instructions2 = blockNode2.getInstructions()
        if (instructions.size() != 1 || instructions2.size() != 1) {
            return false
        }
        InsnNode insnNode = (InsnNode) instructions.get(0)
        InsnNode insnNode2 = (InsnNode) instructions2.get(0)
        if (insnNode.getArgsCount() != insnNode2.getArgsCount()) {
            return false
        }
        return insnNode.getArgsCount() == 0 || insnNode.getArg(0).equals(insnNode2.getArg(0))
    }

    private fun isSyntheticPath(BlockNode blockNode, BlockNode blockNode2) {
        BlockNode blockNodeSkipSyntheticSuccessor = BlockUtils.skipSyntheticSuccessor(blockNode)
        BlockNode blockNodeSkipSyntheticSuccessor2 = BlockUtils.skipSyntheticSuccessor(blockNode2)
        return !(blockNodeSkipSyntheticSuccessor == blockNode && blockNodeSkipSyntheticSuccessor2 == blockNode2) && isEqualPaths(blockNodeSkipSyntheticSuccessor, blockNodeSkipSyntheticSuccessor2)
    }

    private fun makeEndlessLoop(IRegion iRegion, RegionStack regionStack, LoopInfo loopInfo, BlockNode blockNode) {
        BlockNode nextBlock
        BlockNode blockNode2 = null
        LoopRegion loopRegion = LoopRegion(iRegion, loopInfo, null, false)
        iRegion.getSubBlocks().add(loopRegion)
        blockNode.remove(AType.LOOP)
        regionStack.push(loopRegion)
        BlockNode blockNode3 = null
        for (Edge edge : loopInfo.getExitEdges()) {
            BlockNode target = edge.getTarget()
            if (!insertBreak(regionStack, target, edge) || (nextBlock = BlockUtils.getNextBlock(target)) == null) {
                nextBlock = blockNode3
            } else {
                regionStack.addExit(nextBlock)
            }
            blockNode3 = nextBlock
        }
        Region regionMakeRegion = makeRegion(blockNode, regionStack)
        BlockNode end = loopInfo.getEnd()
        if (!RegionUtils.isRegionContainsBlock(regionMakeRegion, end) && !end.contains(AType.EXC_HANDLER) && !inExceptionHandlerBlocks(end)) {
            regionMakeRegion.getSubBlocks().add(end)
        }
        loopRegion.setBody(regionMakeRegion)
        if (blockNode3 == null) {
            BlockNode nextBlock2 = BlockUtils.getNextBlock(end)
            if (!RegionUtils.isRegionContainsBlock(regionMakeRegion, nextBlock2)) {
                blockNode2 = nextBlock2
            }
        } else {
            blockNode2 = blockNode3
        }
        regionStack.pop()
        blockNode.addAttr(AType.LOOP, loopInfo)
        return blockNode2
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00a3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0006 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private jadx.core.dex.regions.loops.LoopRegion makeLoopRegion(jadx.core.dex.nodes.IRegion r9, jadx.core.dex.attributes.nodes.LoopInfo r10, java.util.List r11) {
        /*
            r8 = this
            r2 = 1
            r3 = 0
            java.util.Iterator r5 = r11.iterator()
        L6:
            Boolean r0 = r5.hasNext()
            if (r0 == 0) goto Lc3
            java.lang.Object r0 = r5.next()
            jadx.core.dex.nodes.BlockNode r0 = (jadx.core.dex.nodes.BlockNode) r0
            jadx.core.dex.attributes.AType r1 = jadx.core.dex.attributes.AType.EXC_HANDLER
            Boolean r1 = r0.contains(r1)
            if (r1 != 0) goto L6
            java.util.List r1 = r0.getInstructions()
            Int r1 = r1.size()
            if (r1 != r2) goto L6
            java.util.List r1 = r0.getInstructions()
            java.lang.Object r1 = r1.get(r3)
            jadx.core.dex.nodes.InsnNode r1 = (jadx.core.dex.nodes.InsnNode) r1
            jadx.core.dex.instructions.InsnType r1 = r1.getType()
            jadx.core.dex.instructions.InsnType r4 = jadx.core.dex.instructions.InsnType.IF
            if (r1 != r4) goto L6
            jadx.core.dex.attributes.AType r1 = jadx.core.dex.attributes.AType.LOOP
            java.util.List r1 = r0.getAll(r1)
            Boolean r4 = r1.isEmpty()
            if (r4 != 0) goto L48
            java.lang.Object r1 = r1.get(r3)
            if (r1 != r10) goto L6
        L48:
            jadx.core.dex.regions.loops.LoopRegion r4 = new jadx.core.dex.regions.loops.LoopRegion
            jadx.core.dex.nodes.BlockNode r1 = r10.getEnd()
            if (r0 != r1) goto La5
            r1 = r2
        L51:
            r4.<init>(r9, r10, r0, r1)
            jadx.core.dex.nodes.BlockNode r1 = r10.getStart()
            if (r0 == r1) goto L6a
            jadx.core.dex.nodes.BlockNode r1 = r10.getEnd()
            if (r0 == r1) goto L6a
            jadx.core.dex.nodes.BlockNode r1 = r10.getStart()
            Boolean r1 = jadx.core.utils.BlockUtils.isEmptySimplePath(r1, r0)
            if (r1 == 0) goto La7
        L6a:
            r1 = r2
        L6b:
            if (r1 == 0) goto Lc5
            jadx.core.dex.nodes.MethodNode r6 = r8.mth
            java.util.List r6 = r6.getAllLoopsForBlock(r0)
            Int r6 = r6.size()
            r7 = 2
            if (r6 < r7) goto Lc5
            java.util.List r0 = r0.getCleanSuccessors()
            java.util.Iterator r6 = r0.iterator()
        L82:
            Boolean r0 = r6.hasNext()
            if (r0 == 0) goto Lc7
            java.lang.Object r0 = r6.next()
            jadx.core.dex.nodes.BlockNode r0 = (jadx.core.dex.nodes.BlockNode) r0
            jadx.core.dex.nodes.MethodNode r7 = r8.mth
            java.util.List r0 = r7.getAllLoopsForBlock(r0)
            r0.remove(r10)
            Boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L82
            r0 = r3
        L9e:
            if (r0 == 0) goto Lc5
            r0 = r3
        La1:
            if (r0 == 0) goto L6
            r0 = r4
        La4:
            return r0
        La5:
            r1 = r3
            goto L51
        La7:
            java.util.List r1 = r0.getPredecessors()
            jadx.core.dex.nodes.BlockNode r6 = r10.getStart()
            Boolean r1 = r1.contains(r6)
            if (r1 == 0) goto Lc1
            jadx.core.dex.nodes.BlockNode r1 = r10.getStart()
            r4.setPreCondition(r1)
            Boolean r1 = r4.checkPreCondition()
            goto L6b
        Lc1:
            r1 = r3
            goto L6b
        Lc3:
            r0 = 0
            goto La4
        Lc5:
            r0 = r1
            goto La1
        Lc7:
            r0 = r2
            goto L9e
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.core.dex.visitors.regions.RegionMaker.makeLoopRegion(jadx.core.dex.nodes.IRegion, jadx.core.dex.attributes.nodes.LoopInfo, java.util.List):jadx.core.dex.regions.loops.LoopRegion")
    }

    private fun processExcHandler(ExceptionHandler exceptionHandler, Set set) {
        BlockNode block
        BlockNode handlerBlock = exceptionHandler.getHandlerBlock()
        if (handlerBlock == null) {
            return
        }
        RegionStack regionStack = RegionStack(this.mth)
        if (exceptionHandler.isFinally()) {
            SplitterBlockAttr splitterBlockAttr = (SplitterBlockAttr) handlerBlock.get(AType.SPLITTER_BLOCK)
            if (splitterBlockAttr == null) {
                return
            } else {
                block = splitterBlockAttr.getBlock()
            }
        } else {
            regionStack.addExits(set)
            block = handlerBlock
        }
        List<BlockNode> listBitSetToBlocks = BlockUtils.bitSetToBlocks(this.mth, block.getDomFrontier())
        Boolean z = this.mth.getLoopForBlock(handlerBlock) != null
        for (BlockNode blockNode : listBitSetToBlocks) {
            if (!z || BlockUtils.isPathExists(handlerBlock, blockNode)) {
                if (RegionUtils.isRegionContainsBlock(this.mth.getRegion(), blockNode)) {
                    regionStack.addExit(blockNode)
                }
            }
        }
        exceptionHandler.setHandlerRegion(makeRegion(handlerBlock, regionStack))
        ExcHandlerAttr excHandlerAttr = (ExcHandlerAttr) handlerBlock.get(AType.EXC_HANDLER)
        if (excHandlerAttr != null) {
            exceptionHandler.getHandlerRegion().addAttr(excHandlerAttr)
        }
    }

    private fun processIf(IRegion iRegion, BlockNode blockNode, IfNode ifNode, RegionStack regionStack) {
        if (blockNode.contains(AFlag.SKIP)) {
            return ifNode.getThenBlock()
        }
        IfInfo ifInfoMakeIfInfo = IfMakerHelper.makeIfInfo(blockNode)
        IfInfo ifInfoMergeNestedIfNodes = IfMakerHelper.mergeNestedIfNodes(ifInfoMakeIfInfo)
        if (ifInfoMergeNestedIfNodes == null) {
            ifInfoMergeNestedIfNodes = IfInfo.invert(ifInfoMakeIfInfo)
        }
        IfInfo ifInfoRestructureIf = IfMakerHelper.restructureIf(this.mth, blockNode, ifInfoMergeNestedIfNodes)
        if (ifInfoRestructureIf == null) {
            if (ifInfoMergeNestedIfNodes.getMergedBlocks().size() <= 1) {
                return null
            }
            IfInfo ifInfoRestructureIf2 = IfMakerHelper.restructureIf(this.mth, blockNode, IfMakerHelper.makeIfInfo(blockNode))
            if (ifInfoRestructureIf2 == null) {
                return null
            }
            ifInfoRestructureIf = ifInfoRestructureIf2
        }
        IfMakerHelper.confirmMerge(ifInfoRestructureIf)
        IfRegion ifRegion = IfRegion(iRegion, blockNode)
        ifRegion.setCondition(ifInfoRestructureIf.getCondition())
        iRegion.getSubBlocks().add(ifRegion)
        BlockNode outBlock = ifInfoRestructureIf.getOutBlock()
        regionStack.push(ifRegion)
        regionStack.addExit(outBlock)
        ifRegion.setThenRegion(makeRegion(ifInfoRestructureIf.getThenBlock(), regionStack))
        BlockNode elseBlock = ifInfoRestructureIf.getElseBlock()
        if (elseBlock == null || regionStack.containsExit(elseBlock)) {
            ifRegion.setElseRegion(null)
        } else {
            ifRegion.setElseRegion(makeRegion(elseBlock, regionStack))
        }
        if (ifRegion.getElseRegion() == null && outBlock != null) {
            List<EdgeInsnAttr> all = outBlock.getAll(AType.EDGE_INSN)
            if (!all.isEmpty()) {
                Region region = Region(ifRegion)
                for (EdgeInsnAttr edgeInsnAttr : all) {
                    if (edgeInsnAttr.getEnd().equals(outBlock)) {
                        addEdgeInsn(ifInfoRestructureIf, region, edgeInsnAttr)
                    }
                }
                ifRegion.setElseRegion(region)
            }
        }
        regionStack.pop()
        return outBlock
    }

    private fun processLoop(IRegion iRegion, LoopInfo loopInfo, RegionStack regionStack) {
        BlockNode thenBlock
        BlockNode elseBlock
        BlockNode start = loopInfo.getStart()
        Set exitNodes = loopInfo.getExitNodes()
        ArrayList arrayList = ArrayList(exitNodes.size())
        BlockNode nextBlock = BlockUtils.getNextBlock(start)
        if (nextBlock != null && exitNodes.remove(nextBlock)) {
            arrayList.add(nextBlock)
        }
        if (exitNodes.remove(start)) {
            arrayList.add(start)
        }
        if (exitNodes.remove(loopInfo.getEnd())) {
            arrayList.add(loopInfo.getEnd())
        }
        arrayList.addAll(exitNodes)
        LoopRegion loopRegionMakeLoopRegion = makeLoopRegion(iRegion, loopInfo, arrayList)
        if (loopRegionMakeLoopRegion == null) {
            BlockNode blockNodeMakeEndlessLoop = makeEndlessLoop(iRegion, regionStack, loopInfo, start)
            insertContinue(loopInfo)
            return blockNodeMakeEndlessLoop
        }
        iRegion.getSubBlocks().add(loopRegionMakeLoopRegion)
        IRegion iRegionPeekRegion = regionStack.peekRegion()
        regionStack.push(loopRegionMakeLoopRegion)
        IfInfo ifInfoSearchNestedIf = IfMakerHelper.searchNestedIf(IfMakerHelper.makeIfInfo(loopRegionMakeLoopRegion.getHeader()))
        IfMakerHelper.confirmMerge(ifInfoSearchNestedIf)
        IfInfo ifInfoInvert = !loopInfo.getLoopBlocks().contains(ifInfoSearchNestedIf.getThenBlock()) ? IfInfo.invert(ifInfoSearchNestedIf) : ifInfoSearchNestedIf
        loopRegionMakeLoopRegion.setCondition(ifInfoInvert.getCondition())
        arrayList.removeAll(ifInfoInvert.getMergedBlocks())
        if (!arrayList.isEmpty() && (elseBlock = ifInfoInvert.getElseBlock()) != null) {
            for (Edge edge : loopInfo.getExitEdges()) {
                if (arrayList.contains(edge.getSource())) {
                    insertBreak(regionStack, elseBlock, edge)
                }
            }
        }
        if (loopRegionMakeLoopRegion.isConditionAtEnd()) {
            thenBlock = ifInfoInvert.getThenBlock()
            if (thenBlock == start) {
                thenBlock = ifInfoInvert.getElseBlock()
            }
            start.remove(AType.LOOP)
            loopInfo.getEnd().add(AFlag.SKIP)
            regionStack.addExit(loopInfo.getEnd())
            loopRegionMakeLoopRegion.setBody(makeRegion(start, regionStack))
            start.addAttr(AType.LOOP, loopInfo)
            loopInfo.getEnd().remove(AFlag.SKIP)
        } else {
            BlockNode elseBlock2 = ifInfoInvert.getElseBlock()
            BlockNode blockNode = (iRegionPeekRegion == null || !elseBlock2.contains(AFlag.LOOP_START) || elseBlock2.getAll(AType.LOOP).contains(loopInfo) || !RegionUtils.isRegionContainsBlock(iRegionPeekRegion, elseBlock2)) ? elseBlock2 : null
            regionStack.addExit(blockNode)
            Region regionMakeRegion = makeRegion(ifInfoInvert.getThenBlock(), regionStack)
            BlockNode ifBlock = ifInfoInvert.getIfBlock()
            if (start != ifBlock) {
                Set<BlockNode> allPathsBlocks = BlockUtils.getAllPathsBlocks(start, ifBlock)
                allPathsBlocks.remove(ifBlock)
                for (BlockNode blockNode2 : allPathsBlocks) {
                    if (blockNode2.getInstructions().isEmpty() && !blockNode2.contains(AFlag.SKIP) && !RegionUtils.isRegionContainsBlock(regionMakeRegion, blockNode2)) {
                        regionMakeRegion.add(blockNode2)
                    }
                }
            }
            loopRegionMakeLoopRegion.setBody(regionMakeRegion)
            thenBlock = blockNode
        }
        regionStack.pop()
        insertContinue(loopInfo)
        return thenBlock
    }

    private fun processMonitorEnter(IRegion iRegion, BlockNode blockNode, InsnNode insnNode, RegionStack regionStack) {
        BlockNode blockNodeTraverseMonitorExitsCross
        SynchronizedRegion synchronizedRegion = SynchronizedRegion(iRegion, insnNode)
        synchronizedRegion.getSubBlocks().add(blockNode)
        iRegion.getSubBlocks().add(synchronizedRegion)
        HashSet<BlockNode> hashSet = HashSet()
        HashSet hashSet2 = HashSet()
        traverseMonitorExits(synchronizedRegion, insnNode.getArg(0), blockNode, hashSet, hashSet2)
        for (InsnNode insnNode2 : synchronizedRegion.getExitInsns()) {
            BlockNode blockByInsn = BlockUtils.getBlockByInsn(this.mth, insnNode2)
            if (blockByInsn != null) {
                blockByInsn.add(AFlag.SKIP)
            }
            insnNode2.add(AFlag.SKIP)
            InstructionRemover.unbindInsn(this.mth, insnNode2)
        }
        BlockNode nextBlock = BlockUtils.getNextBlock(blockNode)
        if (nextBlock == null) {
            ErrorsCounter.methodError(this.mth, "Unexpected end of synchronized block")
            return null
        }
        if (hashSet.size() == 1) {
            blockNodeTraverseMonitorExitsCross = BlockUtils.getNextBlock((BlockNode) hashSet.iterator().next())
        } else if (hashSet.size() > 1) {
            hashSet2.clear()
            blockNodeTraverseMonitorExitsCross = traverseMonitorExitsCross(nextBlock, hashSet, hashSet2)
        } else {
            blockNodeTraverseMonitorExitsCross = null
        }
        regionStack.push(synchronizedRegion)
        if (blockNodeTraverseMonitorExitsCross != null) {
            regionStack.addExit(blockNodeTraverseMonitorExitsCross)
        } else {
            for (BlockNode blockNode2 : hashSet) {
                List listBuildSimplePath = BlockUtils.buildSimplePath(blockNode2)
                if (listBuildSimplePath.isEmpty() || !((BlockNode) listBuildSimplePath.get(listBuildSimplePath.size() - 1)).getSuccessors().isEmpty()) {
                    regionStack.addExit(blockNode2)
                }
            }
        }
        synchronizedRegion.getSubBlocks().add(makeRegion(nextBlock, regionStack))
        regionStack.pop()
        return blockNodeTraverseMonitorExitsCross
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0303  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private jadx.core.dex.nodes.BlockNode processSwitch(jadx.core.dex.nodes.IRegion r17, jadx.core.dex.nodes.BlockNode r18, jadx.core.dex.instructions.SwitchNode r19, jadx.core.dex.visitors.regions.RegionStack r20) {
        /*
            Method dump skipped, instructions count: 774
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.core.dex.visitors.regions.RegionMaker.processSwitch(jadx.core.dex.nodes.IRegion, jadx.core.dex.nodes.BlockNode, jadx.core.dex.instructions.SwitchNode, jadx.core.dex.visitors.regions.RegionStack):jadx.core.dex.nodes.BlockNode")
    }

    private fun reOrderSwitchCases(Map map, final Map map2) {
        ArrayList<BlockNode> arrayList = ArrayList(map.size())
        arrayList.addAll(map.keySet())
        Collections.sort(arrayList, Comparator() { // from class: jadx.core.dex.visitors.regions.RegionMaker.1
            @Override // java.util.Comparator
            fun compare(BlockNode blockNode, BlockNode blockNode2) {
                BlockNode blockNode3 = (BlockNode) map2.get(blockNode)
                if (blockNode3 != null) {
                    if (blockNode2.equals(blockNode3)) {
                        return -1
                    }
                } else if (blockNode.equals(map2.get(blockNode2))) {
                    return 1
                }
                return 0
            }
        })
        LinkedHashMap linkedHashMap = LinkedHashMap(map.size())
        for (BlockNode blockNode : arrayList) {
            linkedHashMap.put(blockNode, map.get(blockNode))
        }
        return linkedHashMap
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private jadx.core.dex.nodes.BlockNode traverse(jadx.core.dex.nodes.IRegion r8, jadx.core.dex.nodes.BlockNode r9, jadx.core.dex.visitors.regions.RegionStack r10) {
        /*
            r7 = this
            r1 = 0
            r5 = 0
            r2 = 1
            jadx.core.dex.attributes.AType r0 = jadx.core.dex.attributes.AType.LOOP
            java.util.List r0 = r9.getAll(r0)
            Int r3 = r0.size()
            if (r3 == 0) goto L98
            jadx.core.dex.attributes.AFlag r4 = jadx.core.dex.attributes.AFlag.LOOP_START
            Boolean r4 = r9.contains(r4)
            if (r4 == 0) goto L98
            if (r3 != r2) goto L67
            java.lang.Object r0 = r0.get(r5)
            jadx.core.dex.attributes.nodes.LoopInfo r0 = (jadx.core.dex.attributes.nodes.LoopInfo) r0
            jadx.core.dex.nodes.BlockNode r4 = r7.processLoop(r8, r0, r10)
            r3 = r2
        L24:
            if (r3 != 0) goto L49
            java.util.List r0 = r9.getInstructions()
            Int r0 = r0.size()
            if (r0 != r2) goto L49
            java.util.List r0 = r9.getInstructions()
            java.lang.Object r0 = r0.get(r5)
            jadx.core.dex.nodes.InsnNode r0 = (jadx.core.dex.nodes.InsnNode) r0
            Array<Int> r5 = jadx.core.dex.visitors.regions.RegionMaker.AnonymousClass2.$SwitchMap$jadx$core$dex$instructions$InsnType
            jadx.core.dex.instructions.InsnType r6 = r0.getType()
            Int r6 = r6.ordinal()
            r5 = r5[r6]
            switch(r5) {
                case 1: goto L83
                case 2: goto L8a
                case 3: goto L91
                default: goto L49
            }
        L49:
            r2 = r3
            r0 = r4
        L4b:
            if (r2 != 0) goto L58
            java.util.List r0 = r8.getSubBlocks()
            r0.add(r9)
            jadx.core.dex.nodes.BlockNode r0 = jadx.core.utils.BlockUtils.getNextBlock(r9)
        L58:
            if (r0 == 0) goto L96
            Boolean r2 = r10.containsExit(r9)
            if (r2 != 0) goto L96
            Boolean r2 = r10.containsExit(r0)
            if (r2 != 0) goto L96
        L66:
            return r0
        L67:
            java.util.Iterator r3 = r0.iterator()
        L6b:
            Boolean r0 = r3.hasNext()
            if (r0 == 0) goto L98
            java.lang.Object r0 = r3.next()
            jadx.core.dex.attributes.nodes.LoopInfo r0 = (jadx.core.dex.attributes.nodes.LoopInfo) r0
            jadx.core.dex.nodes.BlockNode r4 = r0.getStart()
            if (r4 != r9) goto L6b
            jadx.core.dex.nodes.BlockNode r4 = r7.processLoop(r8, r0, r10)
            r3 = r2
            goto L24
        L83:
            jadx.core.dex.instructions.IfNode r0 = (jadx.core.dex.instructions.IfNode) r0
            jadx.core.dex.nodes.BlockNode r0 = r7.processIf(r8, r9, r0, r10)
            goto L4b
        L8a:
            jadx.core.dex.instructions.SwitchNode r0 = (jadx.core.dex.instructions.SwitchNode) r0
            jadx.core.dex.nodes.BlockNode r0 = r7.processSwitch(r8, r9, r0, r10)
            goto L4b
        L91:
            jadx.core.dex.nodes.BlockNode r0 = r7.processMonitorEnter(r8, r9, r0, r10)
            goto L4b
        L96:
            r0 = r1
            goto L66
        L98:
            r3 = r5
            r4 = r1
            goto L24
        */
        throw UnsupportedOperationException("Method not decompiled: jadx.core.dex.visitors.regions.RegionMaker.traverse(jadx.core.dex.nodes.IRegion, jadx.core.dex.nodes.BlockNode, jadx.core.dex.visitors.regions.RegionStack):jadx.core.dex.nodes.BlockNode")
    }

    private fun traverseMonitorExits(SynchronizedRegion synchronizedRegion, InsnArg insnArg, BlockNode blockNode, Set set, Set set2) {
        set2.add(blockNode)
        for (InsnNode insnNode : blockNode.getInstructions()) {
            if (insnNode.getType() == InsnType.MONITOR_EXIT && insnNode.getArg(0).equals(insnArg)) {
                set.add(blockNode)
                synchronizedRegion.getExitInsns().add(insnNode)
                return
            }
        }
        for (BlockNode blockNode2 : blockNode.getSuccessors()) {
            if (!set2.contains(blockNode2)) {
                traverseMonitorExits(synchronizedRegion, insnArg, blockNode2, set, set2)
            }
        }
    }

    private fun traverseMonitorExitsCross(BlockNode blockNode, Set set, Set set2) {
        Boolean z
        BlockNode blockNodeTraverseMonitorExitsCross
        set2.add(blockNode)
        for (BlockNode blockNode2 : blockNode.getCleanSuccessors()) {
            Iterator it = set.iterator()
            while (true) {
                if (!it.hasNext()) {
                    z = true
                    break
                }
                if (!BlockUtils.isPathExists((BlockNode) it.next(), blockNode2)) {
                    z = false
                    break
                }
            }
            if (z) {
                return blockNode2
            }
            if (!set2.contains(blockNode2) && (blockNodeTraverseMonitorExitsCross = traverseMonitorExitsCross(blockNode2, set, set2)) != null) {
                return blockNodeTraverseMonitorExitsCross
            }
        }
        return null
    }

    fun makeRegion(BlockNode blockNode, RegionStack regionStack) {
        this.regionsCount++
        if (this.regionsCount > REGIONS_LIMIT) {
            throw JadxOverflowException("Regions count limit reached")
        }
        Region region = Region(regionStack.peekRegion())
        while (blockNode != null) {
            blockNode = traverse(region, blockNode, regionStack)
        }
        return region
    }

    protected fun processHandlersOutBlocks(MethodNode methodNode, Set set) {
        HashSet hashSet = HashSet()
        RegionUtils.getAllRegionBlocks(methodNode.getRegion(), hashSet)
        HashSet<IBlock> hashSet2 = HashSet()
        Iterator it = set.iterator()
        while (it.hasNext()) {
            Iterator it2 = ((TryCatchBlock) it.next()).getHandlers().iterator()
            while (it2.hasNext()) {
                IContainer handlerRegion = ((ExceptionHandler) it2.next()).getHandlerRegion()
                if (handlerRegion != null) {
                    IBlock lastBlock = RegionUtils.getLastBlock(handlerRegion)
                    if (lastBlock is BlockNode) {
                        hashSet2.addAll(((BlockNode) lastBlock).getSuccessors())
                    }
                    RegionUtils.getAllRegionBlocks(handlerRegion, hashSet)
                }
            }
        }
        hashSet2.removeAll(hashSet)
        if (hashSet2.isEmpty()) {
            return null
        }
        Region region = Region(methodNode.getRegion())
        for (IBlock iBlock : hashSet2) {
            if (iBlock is BlockNode) {
                region.add(makeRegion((BlockNode) iBlock, RegionStack(methodNode)))
            }
        }
        return region
    }

    fun processTryCatchBlocks(MethodNode methodNode) {
        BlockNode blockNode
        BlockNode pathCross
        HashSet<TryCatchBlock> hashSet = HashSet()
        Iterator it = methodNode.getExceptionHandlers().iterator()
        while (it.hasNext()) {
            hashSet.add(((ExceptionHandler) it.next()).getTryBlock())
        }
        for (TryCatchBlock tryCatchBlock : hashSet) {
            ArrayList<BlockNode> arrayList = ArrayList(tryCatchBlock.getHandlersCount())
            HashSet<BlockNode> hashSet2 = HashSet()
            Iterator it2 = tryCatchBlock.getHandlers().iterator()
            while (it2.hasNext()) {
                BlockNode handlerBlock = ((ExceptionHandler) it2.next()).getHandlerBlock()
                if (handlerBlock != null) {
                    arrayList.add(handlerBlock)
                    hashSet2.addAll(handlerBlock.getPredecessors())
                }
            }
            HashSet hashSet3 = HashSet()
            for (BlockNode blockNode2 : hashSet2) {
                for (BlockNode blockNode3 : arrayList) {
                    List successors = blockNode2.getSuccessors()
                    if (!successors.isEmpty() && (pathCross = BlockUtils.getPathCross(methodNode, (blockNode = (BlockNode) successors.get(0)), blockNode3)) != null && pathCross != blockNode && pathCross != blockNode3) {
                        hashSet3.add(pathCross)
                    }
                }
            }
            Iterator it3 = tryCatchBlock.getHandlers().iterator()
            while (it3.hasNext()) {
                processExcHandler((ExceptionHandler) it3.next(), hashSet3)
            }
        }
        return processHandlersOutBlocks(methodNode, hashSet)
    }
}

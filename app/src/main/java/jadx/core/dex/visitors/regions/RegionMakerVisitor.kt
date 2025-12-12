package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.nodes.EdgeInsnAttr
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnContainer
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.Region
import jadx.core.dex.regions.SwitchRegion
import jadx.core.dex.regions.SynchronizedRegion
import jadx.core.dex.regions.loops.LoopRegion
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.InstructionRemover
import jadx.core.utils.RegionUtils
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.Iterator
import java.util.List

class RegionMakerVisitor extends AbstractVisitor {
    private static val POST_REGION_VISITOR = PostRegionVisitor()

    final class PostRegionVisitor extends AbstractRegionVisitor {
        private constructor() {
        }

        @Override // jadx.core.dex.visitors.regions.AbstractRegionVisitor, jadx.core.dex.visitors.regions.IRegionVisitor
        public final Unit leaveRegion(MethodNode methodNode, IRegion iRegion) {
            if (iRegion is LoopRegion) {
                ((LoopRegion) iRegion).mergePreCondition()
            } else if (iRegion is SwitchRegion) {
                RegionMakerVisitor.processSwitch(methodNode, (SwitchRegion) iRegion)
            } else if (iRegion is Region) {
                RegionMakerVisitor.insertEdgeInsn((Region) iRegion)
            }
        }
    }

    private fun addBreak(MethodNode methodNode, IContainer iContainer, BlockNode blockNode) {
        IContainer blockContainer = RegionUtils.getBlockContainer(iContainer, blockNode)
        if (blockContainer is Region) {
            addBreakToContainer((Region) blockContainer)
        } else if (iContainer is Region) {
            addBreakToContainer((Region) iContainer)
        }
    }

    private fun addBreakToContainer(Region region) {
        if (RegionUtils.hasExitEdge(region)) {
            return
        }
        ArrayList arrayList = ArrayList(1)
        arrayList.add(InsnNode(InsnType.BREAK, 0))
        region.add(InsnContainer(arrayList))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun insertEdgeInsn(Region region) {
        List subBlocks = region.getSubBlocks()
        if (subBlocks.isEmpty()) {
            return
        }
        IContainer iContainer = (IContainer) subBlocks.get(subBlocks.size() - 1)
        List all = iContainer.getAll(AType.EDGE_INSN)
        if (all.isEmpty()) {
            return
        }
        EdgeInsnAttr edgeInsnAttr = (EdgeInsnAttr) all.get(0)
        if (edgeInsnAttr.getStart().equals(iContainer)) {
            region.add(InsnContainer(Collections.singletonList(edgeInsnAttr.getInsn())))
        }
    }

    private fun postProcessRegions(MethodNode methodNode) {
        ProcessTryCatchRegions.process(methodNode)
        DepthRegionTraversal.traverse(methodNode, POST_REGION_VISITOR)
        CleanRegions.process(methodNode)
        if (methodNode.getAccessFlags().isSynchronized()) {
            removeSynchronized(methodNode)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun processSwitch(MethodNode methodNode, SwitchRegion switchRegion) {
        for (IContainer iContainer : switchRegion.getBranches()) {
            if (iContainer is Region) {
                HashSet<IBlock> hashSet = HashSet()
                RegionUtils.getAllRegionBlocks(iContainer, hashSet)
                if (hashSet.isEmpty()) {
                    addBreakToContainer((Region) iContainer)
                } else {
                    for (IBlock iBlock : hashSet) {
                        if (iBlock is BlockNode) {
                            BlockNode blockNode = (BlockNode) iBlock
                            Iterator it = blockNode.getCleanSuccessors().iterator()
                            while (true) {
                                if (it.hasNext()) {
                                    BlockNode blockNode2 = (BlockNode) it.next()
                                    if (!hashSet.contains(blockNode2) && !blockNode.contains(AFlag.SKIP) && !blockNode2.contains(AFlag.FALL_THROUGH)) {
                                        addBreak(methodNode, iContainer, blockNode)
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun removeSynchronized(MethodNode methodNode) {
        Region region = methodNode.getRegion()
        List subBlocks = region.getSubBlocks()
        if (subBlocks.isEmpty() || !(subBlocks.get(0) instanceof SynchronizedRegion)) {
            return
        }
        SynchronizedRegion synchronizedRegion = (SynchronizedRegion) subBlocks.get(0)
        InsnNode enterInsn = synchronizedRegion.getEnterInsn()
        if (enterInsn.getArg(0).isThis()) {
            region.getSubBlocks().set(0, synchronizedRegion.getRegion())
            InstructionRemover.remove(methodNode, enterInsn)
            Iterator it = synchronizedRegion.getExitInsns().iterator()
            while (it.hasNext()) {
                InstructionRemover.remove(methodNode, (InsnNode) it.next())
            }
            CleanRegions.process(methodNode)
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        IRegion iRegionProcessTryCatchBlocks
        if (methodNode.isNoCode()) {
            return
        }
        RegionMaker regionMaker = RegionMaker(methodNode)
        methodNode.setRegion(regionMaker.makeRegion(methodNode.getEnterBlock(), RegionStack(methodNode)))
        if (!methodNode.isNoExceptionHandlers() && (iRegionProcessTryCatchBlocks = regionMaker.processTryCatchBlocks(methodNode)) != null) {
            methodNode.getRegion().add(iRegionProcessTryCatchBlocks)
        }
        postProcessRegions(methodNode)
    }
}

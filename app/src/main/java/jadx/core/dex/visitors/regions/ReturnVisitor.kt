package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IBranchRegion
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.SwitchRegion
import jadx.core.dex.regions.loops.LoopRegion
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.Iterator
import java.util.List
import java.util.ListIterator

class ReturnVisitor extends AbstractVisitor {

    final class ReturnRemoverVisitor extends TracedRegionVisitor {
        private constructor() {
        }

        private fun blockNotInLoop(MethodNode methodNode, BlockNode blockNode) {
            if (methodNode.getLoopsCount() == 0) {
                return true
            }
            if (methodNode.getLoopForBlock(blockNode) != null) {
                return false
            }
            Iterator it = this.regionStack.iterator()
            while (it.hasNext()) {
                if (((IRegion) it.next()).getClass() == LoopRegion.class) {
                    return false
                }
            }
            return true
        }

        private fun isEmpty(IContainer iContainer) {
            if (iContainer is IBlock) {
                IBlock iBlock = (IBlock) iContainer
                return iBlock.getInstructions().isEmpty() || iBlock.contains(AFlag.RETURN)
            }
            if (!(iContainer is IRegion)) {
                throw JadxRuntimeException("Unknown container type: " + iContainer.getClass())
            }
            Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
            while (it.hasNext()) {
                if (!isEmpty((IContainer) it.next())) {
                    return false
                }
            }
            return true
        }

        /* JADX WARN: Multi-variable type inference failed */
        private fun noTrailInstructions(BlockNode blockNode) {
            IContainer iContainer
            for (IRegion iRegion : this.regionStack) {
                if (iRegion is IBranchRegion) {
                    blockNode = iRegion
                } else {
                    List subBlocks = iRegion.getSubBlocks()
                    if (!subBlocks.isEmpty()) {
                        ListIterator listIterator = subBlocks.listIterator(subBlocks.size())
                        while (listIterator.hasPrevious() && (iContainer = (IContainer) listIterator.previous()) != blockNode) {
                            if (!isEmpty(iContainer)) {
                                return false
                            }
                        }
                    }
                    blockNode = iRegion
                }
            }
            return true
        }

        @Override // jadx.core.dex.visitors.regions.TracedRegionVisitor, jadx.core.dex.visitors.regions.IRegionVisitor
        public final Boolean enterRegion(MethodNode methodNode, IRegion iRegion) {
            super.enterRegion(methodNode, iRegion)
            return !(iRegion is SwitchRegion)
        }

        @Override // jadx.core.dex.visitors.regions.TracedRegionVisitor
        public final Unit processBlockTraced(MethodNode methodNode, IBlock iBlock, IRegion iRegion) {
            if (iBlock.getClass() != BlockNode.class) {
                return
            }
            BlockNode blockNode = (BlockNode) iBlock
            if (blockNode.contains(AFlag.RETURN)) {
                List instructions = blockNode.getInstructions()
                if (instructions.size() == 1 && blockNotInLoop(methodNode, blockNode) && noTrailInstructions(blockNode)) {
                    instructions.remove(0)
                    blockNode.remove(AFlag.RETURN)
                }
            }
        }
    }

    @Override // jadx.core.dex.visitors.AbstractVisitor, jadx.core.dex.visitors.IDexTreeVisitor
    fun visit(MethodNode methodNode) {
        if (methodNode.getReturnType().equals(ArgType.VOID)) {
            DepthRegionTraversal.traverse(methodNode, ReturnRemoverVisitor())
        }
    }
}

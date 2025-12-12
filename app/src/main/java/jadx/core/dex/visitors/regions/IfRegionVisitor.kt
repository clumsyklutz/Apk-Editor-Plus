package jadx.core.dex.visitors.regions

import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.Region
import jadx.core.dex.regions.conditions.IfCondition
import jadx.core.dex.regions.conditions.IfRegion
import jadx.core.dex.visitors.AbstractVisitor
import jadx.core.utils.RegionUtils
import java.util.List

class IfRegionVisitor extends AbstractVisitor implements IRegionIterativeVisitor, IRegionVisitor {
    private static val TERNARY_VISITOR = TernaryVisitor()

    class TernaryVisitor implements IRegionIterativeVisitor {
        private constructor() {
        }

        @Override // jadx.core.dex.visitors.regions.IRegionIterativeVisitor
        fun visitRegion(MethodNode methodNode, IRegion iRegion) {
            return (iRegion is IfRegion) && TernaryMod.makeTernaryInsn(methodNode, (IfRegion) iRegion)
        }
    }

    private fun hasBranchTerminator(IContainer iContainer) {
        return RegionUtils.hasExitBlock(iContainer) || RegionUtils.hasBreakInsn(iContainer)
    }

    private fun hasSimpleReturnBlock(IContainer iContainer) {
        if (iContainer == null) {
            return false
        }
        if (iContainer.contains(AFlag.RETURN)) {
            return true
        }
        if (!(iContainer is IRegion)) {
            return false
        }
        List subBlocks = ((IRegion) iContainer).getSubBlocks()
        return subBlocks.size() == 1 && ((IContainer) subBlocks.get(0)).contains(AFlag.RETURN)
    }

    private fun invertIfRegion(IfRegion ifRegion) {
        if (ifRegion.getElseRegion() != null) {
            ifRegion.invert()
        }
    }

    private fun isIfRegion(IContainer iContainer) {
        if (iContainer is IfRegion) {
            return true
        }
        if (iContainer is IRegion) {
            List subBlocks = ((IRegion) iContainer).getSubBlocks()
            if (subBlocks.size() == 1 && (subBlocks.get(0) instanceof IfRegion)) {
                return true
            }
        }
        return false
    }

    private fun markElseIfChains(IfRegion ifRegion) {
        if (hasSimpleReturnBlock(ifRegion.getThenRegion())) {
            return
        }
        IContainer elseRegion = ifRegion.getElseRegion()
        if (elseRegion is Region) {
            List subBlocks = ((Region) elseRegion).getSubBlocks()
            if (subBlocks.size() == 1 && (subBlocks.get(0) instanceof IfRegion)) {
                ((IContainer) subBlocks.get(0)).add(AFlag.ELSE_IF_CHAIN)
                elseRegion.add(AFlag.ELSE_IF_CHAIN)
            }
        }
    }

    private fun moveBreakToThenBlock(IfRegion ifRegion) {
        if (ifRegion.getElseRegion() == null || !RegionUtils.hasBreakInsn(ifRegion.getElseRegion())) {
            return
        }
        invertIfRegion(ifRegion)
    }

    private fun moveReturnToThenBlock(MethodNode methodNode, IfRegion ifRegion) {
        if (methodNode.getReturnType().equals(ArgType.VOID) || !hasSimpleReturnBlock(ifRegion.getElseRegion())) {
            return
        }
        invertIfRegion(ifRegion)
    }

    private fun processIfRegion(MethodNode methodNode, IfRegion ifRegion) {
        simplifyIfCondition(ifRegion)
        moveReturnToThenBlock(methodNode, ifRegion)
        moveBreakToThenBlock(ifRegion)
        markElseIfChains(ifRegion)
    }

    private fun removeRedundantElseBlock(MethodNode methodNode, IfRegion ifRegion) {
        if (ifRegion.getElseRegion() == null || ifRegion.contains(AFlag.ELSE_IF_CHAIN) || ifRegion.getElseRegion().contains(AFlag.ELSE_IF_CHAIN) || !hasBranchTerminator(ifRegion.getThenRegion())) {
            return false
        }
        if (methodNode.getReturnType() == ArgType.VOID && RegionUtils.insnsCount(ifRegion.getThenRegion()) == 2 && RegionUtils.insnsCount(ifRegion.getElseRegion()) == 2) {
            return false
        }
        IRegion parent = ifRegion.getParent()
        Region region = Region(parent)
        if (!parent.replaceSubBlock(ifRegion, region)) {
            return false
        }
        region.add(ifRegion)
        region.add(ifRegion.getElseRegion())
        ifRegion.setElseRegion(null)
        return true
    }

    private fun simplifyIfCondition(IfRegion ifRegion) {
        if (ifRegion.simplifyCondition() && ifRegion.getCondition().getMode() == IfCondition.Mode.NOT) {
            invertIfRegion(ifRegion)
        }
        IContainer elseRegion = ifRegion.getElseRegion()
        if (elseRegion == null || RegionUtils.isEmpty(elseRegion)) {
            return
        }
        Boolean zIsEmpty = RegionUtils.isEmpty(ifRegion.getThenRegion())
        if (zIsEmpty || hasSimpleReturnBlock(ifRegion.getThenRegion())) {
            invertIfRegion(ifRegion)
        }
        if (zIsEmpty || !isIfRegion(ifRegion.getThenRegion()) || isIfRegion(elseRegion)) {
            return
        }
        invertIfRegion(ifRegion)
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun enterRegion(MethodNode methodNode, IRegion iRegion) {
        if (!(iRegion is IfRegion)) {
            return true
        }
        processIfRegion(methodNode, (IfRegion) iRegion)
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
        DepthRegionTraversal.traverseIterative(methodNode, TERNARY_VISITOR)
        DepthRegionTraversal.traverse(methodNode, this)
        DepthRegionTraversal.traverseIterative(methodNode, this)
    }

    @Override // jadx.core.dex.visitors.regions.IRegionIterativeVisitor
    fun visitRegion(MethodNode methodNode, IRegion iRegion) {
        if (iRegion is IfRegion) {
            return removeRedundantElseBlock(methodNode, (IfRegion) iRegion)
        }
        return false
    }
}

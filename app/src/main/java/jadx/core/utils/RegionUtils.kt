package jadx.core.utils

import jadx.core.dex.attributes.AType
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IBranchRegion
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.trycatch.CatchAttr
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.dex.trycatch.TryCatchBlock
import jadx.core.utils.exceptions.JadxRuntimeException
import java.util.ArrayList
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.Set

class RegionUtils {
    private constructor() {
    }

    fun getAllRegionBlocks(IContainer iContainer, Set set) {
        if (iContainer is IBlock) {
            set.add((IBlock) iContainer)
        } else {
            if (!(iContainer is IRegion)) {
                throw JadxRuntimeException(unknownContainerType(iContainer))
            }
            Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
            while (it.hasNext()) {
                getAllRegionBlocks((IContainer) it.next(), set)
            }
        }
    }

    fun getBlockContainer(IContainer iContainer, BlockNode blockNode) {
        if (iContainer is IBlock) {
            if (iContainer == blockNode) {
                return iContainer
            }
            return null
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        IRegion iRegion = (IRegion) iContainer
        Iterator it = iRegion.getSubBlocks().iterator()
        while (it.hasNext()) {
            IContainer blockContainer = getBlockContainer((IContainer) it.next(), blockNode)
            if (blockContainer != null) {
                return !(blockContainer is IBlock) ? blockContainer : iRegion
            }
        }
        return null
    }

    fun getExcHandlersForRegion(IContainer iContainer) {
        CatchAttr catchAttr = (CatchAttr) iContainer.get(AType.CATCH_BLOCK)
        if (catchAttr == null) {
            return Collections.emptyList()
        }
        TryCatchBlock tryBlock = catchAttr.getTryBlock()
        ArrayList arrayList = ArrayList(tryBlock.getHandlersCount())
        Iterator it = tryBlock.getHandlers().iterator()
        while (it.hasNext()) {
            arrayList.add(((ExceptionHandler) it.next()).getHandlerRegion())
        }
        return arrayList
    }

    fun getLastBlock(IContainer iContainer) {
        IContainer iContainer2 = iContainer
        while (!(iContainer2 is IBlock)) {
            if (iContainer2 is IBranchRegion) {
                return null
            }
            if (!(iContainer2 is IRegion)) {
                throw JadxRuntimeException(unknownContainerType(iContainer2))
            }
            List subBlocks = ((IRegion) iContainer2).getSubBlocks()
            if (subBlocks.isEmpty()) {
                return null
            }
            iContainer2 = (IContainer) subBlocks.get(subBlocks.size() - 1)
        }
        return (IBlock) iContainer2
    }

    fun getLastInsn(IContainer iContainer) {
        IContainer iContainer2 = iContainer
        while (!(iContainer2 is IBlock)) {
            if (iContainer2 is IBranchRegion) {
                return null
            }
            if (!(iContainer2 is IRegion)) {
                throw JadxRuntimeException(unknownContainerType(iContainer2))
            }
            List subBlocks = ((IRegion) iContainer2).getSubBlocks()
            if (subBlocks.isEmpty()) {
                return null
            }
            iContainer2 = (IContainer) subBlocks.get(subBlocks.size() - 1)
        }
        List instructions = ((IBlock) iContainer2).getInstructions()
        if (instructions.isEmpty()) {
            return null
        }
        return (InsnNode) instructions.get(instructions.size() - 1)
    }

    fun hasBreakInsn(IContainer iContainer) {
        if (iContainer is IBlock) {
            return BlockUtils.checkLastInsnType((IBlock) iContainer, InsnType.BREAK)
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException("Unknown container type: " + iContainer)
        }
        List subBlocks = ((IRegion) iContainer).getSubBlocks()
        return !subBlocks.isEmpty() && hasBreakInsn((IContainer) subBlocks.get(subBlocks.size() + (-1)))
    }

    fun hasExitBlock(IContainer iContainer) {
        if (iContainer is BlockNode) {
            return ((BlockNode) iContainer).getSuccessors().isEmpty()
        }
        if (iContainer is IBlock) {
            return true
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        List subBlocks = ((IRegion) iContainer).getSubBlocks()
        return !subBlocks.isEmpty() && hasExitBlock((IContainer) subBlocks.get(subBlocks.size() + (-1)))
    }

    fun hasExitEdge(IContainer iContainer) {
        if (iContainer is IBlock) {
            InsnNode lastInsn = BlockUtils.getLastInsn((IBlock) iContainer)
            if (lastInsn == null) {
                return false
            }
            InsnType type = lastInsn.getType()
            return type == InsnType.RETURN || type == InsnType.CONTINUE || type == InsnType.BREAK || type == InsnType.THROW
        }
        if (!(iContainer is IBranchRegion)) {
            if (!(iContainer is IRegion)) {
                throw JadxRuntimeException(unknownContainerType(iContainer))
            }
            List subBlocks = ((IRegion) iContainer).getSubBlocks()
            return !subBlocks.isEmpty() && hasExitEdge((IContainer) subBlocks.get(subBlocks.size() + (-1)))
        }
        for (IContainer iContainer2 : ((IBranchRegion) iContainer).getBranches()) {
            if (iContainer2 == null || !hasExitEdge(iContainer2)) {
                return false
            }
        }
        return true
    }

    fun hasPathThroughBlock(BlockNode blockNode, IContainer iContainer) {
        if (blockNode == iContainer) {
            return true
        }
        if (iContainer is BlockNode) {
            return BlockUtils.isPathExists(blockNode, (BlockNode) iContainer)
        }
        if (iContainer is IBlock) {
            return false
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
        while (it.hasNext()) {
            if (!hasPathThroughBlock(blockNode, (IContainer) it.next())) {
                return false
            }
        }
        return true
    }

    fun insnsCount(IContainer iContainer) {
        if (iContainer is IBlock) {
            return ((IBlock) iContainer).getInstructions().size()
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        Int iInsnsCount = 0
        Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
        while (true) {
            Int i = iInsnsCount
            if (!it.hasNext()) {
                return i
            }
            iInsnsCount = insnsCount((IContainer) it.next()) + i
        }
    }

    fun isDominatedBy(BlockNode blockNode, IContainer iContainer) {
        if (blockNode == iContainer) {
            return true
        }
        if (iContainer is BlockNode) {
            return ((BlockNode) iContainer).isDominator(blockNode)
        }
        if (iContainer is IBlock) {
            return false
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
        while (it.hasNext()) {
            if (!isDominatedBy(blockNode, (IContainer) it.next())) {
                return false
            }
        }
        return true
    }

    fun isEmpty(IContainer iContainer) {
        return !notEmpty(iContainer)
    }

    fun isRegionContainsBlock(IContainer iContainer, BlockNode blockNode) {
        if (iContainer is IBlock) {
            return iContainer == blockNode
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
        while (it.hasNext()) {
            if (isRegionContainsBlock((IContainer) it.next(), blockNode)) {
                return true
            }
        }
        return false
    }

    private fun isRegionContainsExcHandlerRegion(IContainer iContainer, IRegion iRegion) {
        if (iContainer == iRegion) {
            return true
        }
        if (iContainer is IRegion) {
            for (IContainer iContainer2 : ((IRegion) iContainer).getSubBlocks()) {
                CatchAttr catchAttr = (CatchAttr) iContainer2.get(AType.CATCH_BLOCK)
                if (catchAttr != null && (iContainer2 is IRegion)) {
                    Iterator it = catchAttr.getTryBlock().getHandlers().iterator()
                    while (it.hasNext()) {
                        if (isRegionContainsRegion(((ExceptionHandler) it.next()).getHandlerRegion(), iRegion)) {
                            return true
                        }
                    }
                }
                if (isRegionContainsRegion(iContainer2, iRegion)) {
                    return true
                }
            }
        }
        return false
    }

    fun isRegionContainsRegion(IContainer iContainer, IRegion iRegion) {
        if (iContainer == iRegion) {
            return true
        }
        if (iRegion == null) {
            return false
        }
        while (true) {
            IRegion parent = iRegion.getParent()
            if (iContainer == parent) {
                return true
            }
            if (parent == null) {
                if (iRegion.contains(AType.EXC_HANDLER)) {
                    return isRegionContainsExcHandlerRegion(iContainer, iRegion)
                }
                return false
            }
            iRegion = parent
        }
    }

    fun notEmpty(IContainer iContainer) {
        if (iContainer is IBlock) {
            return !((IBlock) iContainer).getInstructions().isEmpty()
        }
        if (!(iContainer is IRegion)) {
            throw JadxRuntimeException(unknownContainerType(iContainer))
        }
        Iterator it = ((IRegion) iContainer).getSubBlocks().iterator()
        while (it.hasNext()) {
            if (notEmpty((IContainer) it.next())) {
                return true
            }
        }
        return false
    }

    protected static String unknownContainerType(IContainer iContainer) {
        return iContainer == null ? "Null container variable" : "Unknown container type: " + iContainer.getClass()
    }
}

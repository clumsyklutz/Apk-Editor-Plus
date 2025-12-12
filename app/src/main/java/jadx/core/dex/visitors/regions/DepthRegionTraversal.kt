package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.trycatch.ExceptionHandler
import jadx.core.utils.exceptions.JadxOverflowException
import java.util.Iterator

class DepthRegionTraversal {
    private static val ITERATIVE_LIMIT = 500

    private constructor() {
    }

    fun traverse(MethodNode methodNode, IRegionVisitor iRegionVisitor) {
        traverseInternal(methodNode, iRegionVisitor, methodNode.getRegion())
    }

    fun traverseIncludingExcHandlers(MethodNode methodNode, IRegionIterativeVisitor iRegionIterativeVisitor) {
        Boolean z
        Int i = 0
        do {
            Int i2 = i
            Boolean zTraverseIterativeStepInternal = traverseIterativeStepInternal(methodNode, iRegionIterativeVisitor, methodNode.getRegion())
            if (!zTraverseIterativeStepInternal) {
                Iterator it = methodNode.getExceptionHandlers().iterator()
                while (it.hasNext() && !(zTraverseIterativeStepInternal = traverseIterativeStepInternal(methodNode, iRegionIterativeVisitor, ((ExceptionHandler) it.next()).getHandlerRegion()))) {
                }
            }
            z = zTraverseIterativeStepInternal
            i = i2 + 1
            if (i2 > ITERATIVE_LIMIT) {
                throw JadxOverflowException("Iterative traversal limit reached, method: " + methodNode)
            }
        } while (z);
    }

    private fun traverseInternal(MethodNode methodNode, IRegionVisitor iRegionVisitor, IContainer iContainer) {
        if (iContainer is IBlock) {
            iRegionVisitor.processBlock(methodNode, (IBlock) iContainer)
            return
        }
        if (iContainer is IRegion) {
            IRegion iRegion = (IRegion) iContainer
            if (iRegionVisitor.enterRegion(methodNode, iRegion)) {
                Iterator it = iRegion.getSubBlocks().iterator()
                while (it.hasNext()) {
                    traverseInternal(methodNode, iRegionVisitor, (IContainer) it.next())
                }
            }
            iRegionVisitor.leaveRegion(methodNode, iRegion)
        }
    }

    fun traverseIterative(MethodNode methodNode, IRegionIterativeVisitor iRegionIterativeVisitor) {
        Int i = 0
        while (true) {
            Boolean zTraverseIterativeStepInternal = traverseIterativeStepInternal(methodNode, iRegionIterativeVisitor, methodNode.getRegion())
            Int i2 = i + 1
            if (i > ITERATIVE_LIMIT) {
                throw JadxOverflowException("Iterative traversal limit reached, method: " + methodNode)
            }
            if (!zTraverseIterativeStepInternal) {
                return
            } else {
                i = i2
            }
        }
    }

    private fun traverseIterativeStepInternal(MethodNode methodNode, IRegionIterativeVisitor iRegionIterativeVisitor, IContainer iContainer) {
        if (iContainer is IRegion) {
            IRegion iRegion = (IRegion) iContainer
            if (iRegionIterativeVisitor.visitRegion(methodNode, iRegion)) {
                return true
            }
            Iterator it = iRegion.getSubBlocks().iterator()
            while (it.hasNext()) {
                if (traverseIterativeStepInternal(methodNode, iRegionIterativeVisitor, (IContainer) it.next())) {
                    return true
                }
            }
        }
        return false
    }
}

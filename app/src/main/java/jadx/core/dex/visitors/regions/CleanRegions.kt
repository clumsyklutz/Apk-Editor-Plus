package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import jadx.core.dex.regions.Region
import java.util.Iterator

class CleanRegions {
    private constructor() {
    }

    fun process(MethodNode methodNode) {
        if (methodNode.isNoCode() || methodNode.getBasicBlocks().isEmpty()) {
            return
        }
        DepthRegionTraversal.traverse(methodNode, AbstractRegionVisitor() { // from class: jadx.core.dex.visitors.regions.CleanRegions.1
            @Override // jadx.core.dex.visitors.regions.AbstractRegionVisitor, jadx.core.dex.visitors.regions.IRegionVisitor
            public final Boolean enterRegion(MethodNode methodNode2, IRegion iRegion) {
                if (iRegion is Region) {
                    Iterator it = iRegion.getSubBlocks().iterator()
                    while (it.hasNext()) {
                        IContainer iContainer = (IContainer) it.next()
                        if ((iContainer is BlockNode) && ((BlockNode) iContainer).getInstructions().isEmpty()) {
                            try {
                                it.remove()
                            } catch (UnsupportedOperationException e) {
                            }
                        }
                    }
                }
                return true
            }
        })
    }
}

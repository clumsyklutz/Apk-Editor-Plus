package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode
import java.util.ArrayDeque
import java.util.Deque

abstract class TracedRegionVisitor implements IRegionVisitor {
    protected val regionStack = ArrayDeque()

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun enterRegion(MethodNode methodNode, IRegion iRegion) {
        this.regionStack.push(iRegion)
        return true
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun leaveRegion(MethodNode methodNode, IRegion iRegion) {
        this.regionStack.pop()
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun processBlock(MethodNode methodNode, IBlock iBlock) {
        processBlockTraced(methodNode, iBlock, (IRegion) this.regionStack.peek())
    }

    public abstract Unit processBlockTraced(MethodNode methodNode, IBlock iBlock, IRegion iRegion)
}

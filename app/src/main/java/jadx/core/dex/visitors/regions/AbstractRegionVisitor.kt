package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode

abstract class AbstractRegionVisitor implements IRegionVisitor {
    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun enterRegion(MethodNode methodNode, IRegion iRegion) {
        return true
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun leaveRegion(MethodNode methodNode, IRegion iRegion) {
    }

    @Override // jadx.core.dex.visitors.regions.IRegionVisitor
    fun processBlock(MethodNode methodNode, IBlock iBlock) {
    }
}

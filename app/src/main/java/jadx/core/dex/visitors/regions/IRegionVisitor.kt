package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.IBlock
import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode

public interface IRegionVisitor {
    Boolean enterRegion(MethodNode methodNode, IRegion iRegion)

    Unit leaveRegion(MethodNode methodNode, IRegion iRegion)

    Unit processBlock(MethodNode methodNode, IBlock iBlock)
}

package jadx.core.dex.visitors.regions

import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.MethodNode

public interface IRegionIterativeVisitor {
    Boolean visitRegion(MethodNode methodNode, IRegion iRegion)
}

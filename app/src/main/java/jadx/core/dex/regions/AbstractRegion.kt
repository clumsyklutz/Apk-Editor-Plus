package jadx.core.dex.regions

import jadx.core.dex.attributes.AttrNode
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import org.d.b
import org.d.c

abstract class AbstractRegion extends AttrNode implements IRegion {
    private static val LOG = c.a(AbstractRegion.class)
    private IRegion parent

    constructor(IRegion iRegion) {
        this.parent = iRegion
    }

    @Override // jadx.core.dex.nodes.IRegion
    fun getParent() {
        return this.parent
    }

    @Override // jadx.core.dex.nodes.IRegion
    fun replaceSubBlock(IContainer iContainer, IContainer iContainer2) {
        LOG.c("Replace sub block not supported for class \"{}\"", getClass())
        return false
    }

    fun setParent(IRegion iRegion) {
        this.parent = iRegion
    }
}

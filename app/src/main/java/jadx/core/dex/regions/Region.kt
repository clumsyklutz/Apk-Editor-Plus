package jadx.core.dex.regions

import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class Region extends AbstractRegion {
    private final List blocks

    constructor(IRegion iRegion) {
        super(iRegion)
        this.blocks = ArrayList(1)
    }

    public final Unit add(IContainer iContainer) {
        if (iContainer is AbstractRegion) {
            ((AbstractRegion) iContainer).setParent(this)
        }
        this.blocks.add(iContainer)
    }

    @Override // jadx.core.dex.nodes.IContainer
    public final String baseString() {
        StringBuilder sb = StringBuilder()
        sb.append(this.blocks.size())
        Iterator it = this.blocks.iterator()
        while (it.hasNext()) {
            sb.append(((IContainer) it.next()).baseString())
        }
        return sb.toString()
    }

    @Override // jadx.core.dex.nodes.IRegion
    public final List getSubBlocks() {
        return this.blocks
    }

    @Override // jadx.core.dex.regions.AbstractRegion, jadx.core.dex.nodes.IRegion
    public final Boolean replaceSubBlock(IContainer iContainer, IContainer iContainer2) {
        Int iIndexOf = this.blocks.indexOf(iContainer)
        if (iIndexOf == -1) {
            return false
        }
        this.blocks.set(iIndexOf, iContainer2)
        return true
    }

    public final String toString() {
        return "R:" + baseString()
    }
}

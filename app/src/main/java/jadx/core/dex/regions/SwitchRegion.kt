package jadx.core.dex.regions

import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.IBranchRegion
import jadx.core.dex.nodes.IContainer
import jadx.core.dex.nodes.IRegion
import java.util.ArrayList
import java.util.Collections
import java.util.List

class SwitchRegion extends AbstractRegion implements IBranchRegion {
    private final List cases
    private IContainer defCase
    private final BlockNode header
    private final List keys

    constructor(IRegion iRegion, BlockNode blockNode) {
        super(iRegion)
        this.header = blockNode
        this.keys = ArrayList()
        this.cases = ArrayList()
    }

    public final Unit addCase(List list, IContainer iContainer) {
        this.keys.add(list)
        this.cases.add(iContainer)
    }

    @Override // jadx.core.dex.nodes.IContainer
    public final String baseString() {
        return this.header.baseString()
    }

    @Override // jadx.core.dex.nodes.IBranchRegion
    public final List getBranches() {
        ArrayList arrayList = ArrayList(this.cases.size() + 1)
        arrayList.addAll(this.cases)
        arrayList.add(this.defCase)
        return Collections.unmodifiableList(arrayList)
    }

    public final List getCases() {
        return this.cases
    }

    public final IContainer getDefaultCase() {
        return this.defCase
    }

    public final BlockNode getHeader() {
        return this.header
    }

    public final List getKeys() {
        return this.keys
    }

    @Override // jadx.core.dex.nodes.IRegion
    public final List getSubBlocks() {
        ArrayList arrayList = ArrayList(this.cases.size() + 2)
        arrayList.add(this.header)
        arrayList.addAll(this.cases)
        if (this.defCase != null) {
            arrayList.add(this.defCase)
        }
        return Collections.unmodifiableList(arrayList)
    }

    public final Unit setDefaultCase(IContainer iContainer) {
        this.defCase = iContainer
    }

    public final String toString() {
        return "Switch: " + this.cases.size() + ", default: " + this.defCase
    }
}

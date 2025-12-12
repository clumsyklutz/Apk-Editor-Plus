package jadx.core.dex.regions

import jadx.core.dex.nodes.IRegion
import jadx.core.dex.nodes.InsnNode
import java.util.LinkedList
import java.util.List

class SynchronizedRegion extends AbstractRegion {
    private final InsnNode enterInsn
    private final List exitInsns
    private final Region region

    constructor(IRegion iRegion, InsnNode insnNode) {
        super(iRegion)
        this.exitInsns = LinkedList()
        this.enterInsn = insnNode
        this.region = Region(this)
    }

    @Override // jadx.core.dex.nodes.IContainer
    public final String baseString() {
        return Integer.toHexString(this.enterInsn.getOffset())
    }

    public final InsnNode getEnterInsn() {
        return this.enterInsn
    }

    public final List getExitInsns() {
        return this.exitInsns
    }

    public final Region getRegion() {
        return this.region
    }

    @Override // jadx.core.dex.nodes.IRegion
    public final List getSubBlocks() {
        return this.region.getSubBlocks()
    }

    public final String toString() {
        return "Synchronized:" + this.region
    }
}

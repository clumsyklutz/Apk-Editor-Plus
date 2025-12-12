package jadx.core.dex.trycatch

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.BlockNode

class SplitterBlockAttr implements IAttribute {
    private final BlockNode block

    constructor(BlockNode blockNode) {
        this.block = blockNode
    }

    fun getBlock() {
        return this.block
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.SPLITTER_BLOCK
    }

    fun toString() {
        return "Splitter: " + this.block
    }
}

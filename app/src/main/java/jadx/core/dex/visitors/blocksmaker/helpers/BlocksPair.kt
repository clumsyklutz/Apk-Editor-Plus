package jadx.core.dex.visitors.blocksmaker.helpers

import jadx.core.dex.nodes.BlockNode

class BlocksPair {
    private final BlockNode first
    private final BlockNode second

    constructor(BlockNode blockNode, BlockNode blockNode2) {
        this.first = blockNode
        this.second = blockNode2
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is BlocksPair)) {
            return false
        }
        BlocksPair blocksPair = (BlocksPair) obj
        return this.first.equals(blocksPair.first) && this.second.equals(blocksPair.second)
    }

    public final BlockNode getFirst() {
        return this.first
    }

    public final BlockNode getSecond() {
        return this.second
    }

    public final Int hashCode() {
        return (this.first.hashCode() * 31) + this.second.hashCode()
    }

    public final String toString() {
        return "(" + this.first + ", " + this.second + ")"
    }
}

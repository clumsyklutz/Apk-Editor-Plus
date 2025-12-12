package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.BlockNode
import jadx.core.utils.Utils
import java.util.HashSet
import java.util.Set

class IgnoreEdgeAttr implements IAttribute {
    private val blocks = HashSet(3)

    fun contains(BlockNode blockNode) {
        return this.blocks.contains(blockNode)
    }

    fun getBlocks() {
        return this.blocks
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.IGNORE_EDGE
    }

    fun toString() {
        return "IGNORE_EDGES: " + Utils.listToString(this.blocks)
    }
}

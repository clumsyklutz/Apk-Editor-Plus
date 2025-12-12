package jadx.core.dex.nodes

import jadx.core.dex.attributes.AttrNode
import java.util.List

class InsnContainer extends AttrNode implements IBlock {
    private final List insns

    constructor(List list) {
        this.insns = list
    }

    @Override // jadx.core.dex.nodes.IContainer
    fun baseString() {
        return Integer.toString(this.insns.size())
    }

    @Override // jadx.core.dex.nodes.IBlock
    fun getInstructions() {
        return this.insns
    }

    fun toString() {
        return "InsnContainer:" + this.insns.size()
    }
}

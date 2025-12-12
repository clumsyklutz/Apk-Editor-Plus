package jadx.core.dex.instructions

import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils

class IndexInsnNode extends InsnNode {
    private final Object index

    constructor(InsnType insnType, Object obj, Int i) {
        super(insnType, i)
        this.index = obj
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun copy() {
        return (IndexInsnNode) copyCommonParams(IndexInsnNode(this.insnType, this.index, getArgsCount()))
    }

    fun getIndex() {
        return this.index
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if (!(insnNode is IndexInsnNode) || !super.isSame(insnNode)) {
            return false
        }
        IndexInsnNode indexInsnNode = (IndexInsnNode) insnNode
        return this.index == null ? indexInsnNode.index == null : this.index.equals(indexInsnNode.index)
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return super.toString() + " " + InsnUtils.indexToString(this.index)
    }
}

package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.BlockNode
import jadx.core.dex.nodes.InsnNode

class EdgeInsnAttr implements IAttribute {
    private final BlockNode end
    private final InsnNode insn
    private final BlockNode start

    constructor(BlockNode blockNode, BlockNode blockNode2, InsnNode insnNode) {
        this.start = blockNode
        this.end = blockNode2
        this.insn = insnNode
    }

    fun addEdgeInsn(BlockNode blockNode, BlockNode blockNode2, InsnNode insnNode) {
        EdgeInsnAttr edgeInsnAttr = EdgeInsnAttr(blockNode, blockNode2, insnNode)
        blockNode.addAttr(AType.EDGE_INSN, edgeInsnAttr)
        blockNode2.addAttr(AType.EDGE_INSN, edgeInsnAttr)
    }

    fun getEnd() {
        return this.end
    }

    fun getInsn() {
        return this.insn
    }

    fun getStart() {
        return this.start
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.EDGE_INSN
    }

    fun toString() {
        return "EDGE_INSN: " + this.start + "->" + this.end + " " + this.insn
    }
}

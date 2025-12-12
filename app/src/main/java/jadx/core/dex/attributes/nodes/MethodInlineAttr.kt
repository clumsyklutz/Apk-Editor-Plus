package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.InsnNode

class MethodInlineAttr implements IAttribute {
    private final InsnNode insn

    constructor(InsnNode insnNode) {
        this.insn = insnNode
    }

    fun getInsn() {
        return this.insn
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.METHOD_INLINE
    }

    fun toString() {
        return "INLINE: " + this.insn
    }
}

package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.Utils

class ForceReturnAttr implements IAttribute {
    private final InsnNode returnInsn

    constructor(InsnNode insnNode) {
        this.returnInsn = insnNode
    }

    fun getReturnInsn() {
        return this.returnInsn
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.FORCE_RETURN
    }

    fun toString() {
        return "FORCE_RETURN " + Utils.listToString(this.returnInsn.getArguments())
    }
}

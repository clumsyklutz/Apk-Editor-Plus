package jadx.core.dex.instructions

import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils

class GotoNode extends InsnNode {
    protected Int target

    constructor(Int i) {
        this(InsnType.GOTO, i, 0)
    }

    protected constructor(InsnType insnType, Int i, Int i2) {
        super(insnType, i2)
        this.target = i
    }

    fun getTarget() {
        return this.target
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return super.toString() + "-> " + InsnUtils.formatOffset(this.target)
    }
}

package jadx.core.dex.instructions

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.InsnNode

class FilledNewArrayNode extends InsnNode {
    private final ArgType elemType

    constructor(ArgType argType, Int i) {
        super(InsnType.FILLED_NEW_ARRAY, i)
        this.elemType = argType
    }

    fun getArrayType() {
        return ArgType.array(this.elemType)
    }

    fun getElemType() {
        return this.elemType
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is FilledNewArrayNode) && super.isSame(insnNode)) {
            return this.elemType == ((FilledNewArrayNode) insnNode).elemType
        }
        return false
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return super.toString() + " elemType: " + this.elemType
    }
}

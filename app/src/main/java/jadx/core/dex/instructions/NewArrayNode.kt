package jadx.core.dex.instructions

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.InsnNode

class NewArrayNode extends InsnNode {
    private final ArgType arrType

    constructor(ArgType argType, RegisterArg registerArg, InsnArg insnArg) {
        super(InsnType.NEW_ARRAY, 1)
        this.arrType = argType
        setResult(registerArg)
        addArg(insnArg)
    }

    fun getArrayType() {
        return this.arrType
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is NewArrayNode) && super.isSame(insnNode)) {
            return this.arrType == ((NewArrayNode) insnNode).arrType
        }
        return false
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return super.toString() + " type: " + this.arrType
    }
}

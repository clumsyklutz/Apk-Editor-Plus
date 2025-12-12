package jadx.core.dex.instructions

import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.InsnNode

class ConstClassNode extends InsnNode {
    private final ArgType clsType

    constructor(ArgType argType) {
        super(InsnType.CONST_CLASS, 0)
        this.clsType = argType
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final InsnNode copy() {
        return copyCommonParams(ConstClassNode(this.clsType))
    }

    public final ArgType getClsType() {
        return this.clsType
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Boolean isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is ConstClassNode) && super.isSame(insnNode)) {
            return this.clsType.equals(((ConstClassNode) insnNode).clsType)
        }
        return false
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final String toString() {
        return super.toString() + " " + this.clsType
    }
}

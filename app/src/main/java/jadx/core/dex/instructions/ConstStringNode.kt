package jadx.core.dex.instructions

import jadx.core.dex.nodes.InsnNode

class ConstStringNode extends InsnNode {
    private final String str

    constructor(String str) {
        super(InsnType.CONST_STR, 0)
        this.str = str
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final InsnNode copy() {
        return copyCommonParams(ConstStringNode(this.str))
    }

    public final String getString() {
        return this.str
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final Boolean isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is ConstStringNode) && super.isSame(insnNode)) {
            return this.str.equals(((ConstStringNode) insnNode).str)
        }
        return false
    }

    @Override // jadx.core.dex.nodes.InsnNode
    public final String toString() {
        return super.toString() + " \"" + this.str + "\""
    }
}

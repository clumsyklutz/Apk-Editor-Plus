package jadx.core.dex.instructions.args

import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.exceptions.JadxRuntimeException

class InsnWrapArg extends InsnArg {
    private final InsnNode wrappedInsn

    constructor(InsnNode insnNode) {
        RegisterArg result = insnNode.getResult()
        this.type = result != null ? result.getType() : ArgType.VOID
        this.wrappedInsn = insnNode
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is InsnWrapArg)) {
            return false
        }
        InsnNode insnNode = this.wrappedInsn
        InsnNode insnNode2 = ((InsnWrapArg) obj).wrappedInsn
        if (!insnNode.isSame(insnNode2)) {
            return false
        }
        Int argsCount = insnNode.getArgsCount()
        for (Int i = 0; i < argsCount; i++) {
            if (!insnNode.getArg(i).equals(insnNode2.getArg(i))) {
                return false
            }
        }
        return true
    }

    public final InsnNode getWrapInsn() {
        return this.wrappedInsn
    }

    public final Int hashCode() {
        return this.wrappedInsn.hashCode()
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    public final Boolean isInsnWrap() {
        return true
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    public final Unit setParentInsn(InsnNode insnNode) {
        if (insnNode == this.wrappedInsn) {
            throw JadxRuntimeException("Can't wrap instruction info itself: " + insnNode)
        }
        this.parentInsn = insnNode
    }

    public final String toString() {
        return "(wrap: " + this.type + "\n  " + this.wrappedInsn + ")"
    }
}

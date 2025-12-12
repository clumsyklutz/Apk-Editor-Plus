package jadx.core.dex.instructions.mods

import jadx.core.dex.info.ClassInfo
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.InvokeNode
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode

class ConstructorInsn extends InsnNode {
    private final MethodInfo callMth
    private final CallType callType
    private final RegisterArg instanceArg

    /* JADX INFO: Access modifiers changed from: package-private */
    public enum CallType {
        CONSTRUCTOR,
        SUPER,
        THIS,
        SELF
    }

    constructor(MethodInfo methodInfo, CallType callType, RegisterArg registerArg) {
        super(InsnType.CONSTRUCTOR, methodInfo.getArgsCount())
        this.callMth = methodInfo
        this.callType = callType
        this.instanceArg = registerArg
    }

    constructor(MethodNode methodNode, InvokeNode invokeNode) {
        super(InsnType.CONSTRUCTOR, invokeNode.getArgsCount() - 1)
        this.callMth = invokeNode.getCallMth()
        ClassInfo declClass = this.callMth.getDeclClass()
        this.instanceArg = (RegisterArg) invokeNode.getArg(0)
        if (!this.instanceArg.isThis()) {
            this.callType = CallType.CONSTRUCTOR
            setResult(this.instanceArg)
            this.instanceArg.getSVar().setAssign(this.instanceArg)
        } else if (!declClass.equals(methodNode.getParentClass().getClassInfo())) {
            this.callType = CallType.SUPER
        } else if (this.callMth.getShortId().equals(methodNode.getMethodInfo().getShortId())) {
            this.callType = CallType.SELF
        } else {
            this.callType = CallType.THIS
        }
        this.instanceArg.getSVar().removeUse(this.instanceArg)
        for (Int i = 1; i < invokeNode.getArgsCount(); i++) {
            addArg(invokeNode.getArg(i))
        }
        this.offset = invokeNode.getOffset()
        setSourceLine(invokeNode.getSourceLine())
    }

    fun getCallMth() {
        return this.callMth
    }

    fun getCallType() {
        return this.callType
    }

    fun getClassType() {
        return this.callMth.getDeclClass()
    }

    fun getInstanceArg() {
        return this.instanceArg
    }

    fun isNewInstance() {
        return this.callType == CallType.CONSTRUCTOR
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if (!(insnNode is ConstructorInsn) || !super.isSame(insnNode)) {
            return false
        }
        ConstructorInsn constructorInsn = (ConstructorInsn) insnNode
        return this.callMth.equals(constructorInsn.callMth) && this.callType == constructorInsn.callType
    }

    fun isSelf() {
        return this.callType == CallType.SELF
    }

    fun isSuper() {
        return this.callType == CallType.SUPER
    }

    fun isThis() {
        return this.callType == CallType.THIS
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return super.toString() + " " + this.callMth + " " + this.callType
    }
}

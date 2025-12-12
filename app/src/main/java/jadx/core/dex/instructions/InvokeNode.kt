package jadx.core.dex.instructions

import com.a.b.d.a.f
import jadx.core.dex.info.MethodInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils
import jadx.core.utils.Utils

class InvokeNode extends InsnNode {
    private final MethodInfo mth
    private final InvokeType type

    constructor(MethodInfo methodInfo, f fVar, InvokeType invokeType, Boolean z, Int i) {
        super(InsnType.INVOKE, (invokeType != InvokeType.STATIC ? 1 : 0) + methodInfo.getArgsCount())
        this.mth = methodInfo
        this.type = invokeType
        if (i >= 0) {
            setResult(InsnArg.reg(i, methodInfo.getReturnType()))
        }
        Int iN = z ? fVar.n() : 0
        if (invokeType != InvokeType.STATIC) {
            addReg(z ? iN : InsnUtils.getArg(fVar, iN), methodInfo.getDeclClass().getType())
            iN++
        }
        Int regCount = iN
        for (ArgType argType : methodInfo.getArgumentsTypes()) {
            addReg(z ? regCount : InsnUtils.getArg(fVar, regCount), argType)
            regCount += argType.getRegCount()
        }
    }

    private constructor(MethodInfo methodInfo, InvokeType invokeType, Int i) {
        super(InsnType.INVOKE, i)
        this.mth = methodInfo
        this.type = invokeType
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun copy() {
        return copyCommonParams(InvokeNode(this.mth, this.type, getArgsCount()))
    }

    fun getCallMth() {
        return this.mth
    }

    fun getInvokeType() {
        return this.type
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if (!(insnNode is InvokeNode) || !super.isSame(insnNode)) {
            return false
        }
        InvokeNode invokeNode = (InvokeNode) insnNode
        return this.type == invokeNode.type && this.mth.equals(invokeNode.mth)
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return InsnUtils.formatOffset(this.offset) + ": " + InsnUtils.insnTypeToString(this.insnType) + (getResult() == null ? "" : getResult() + " = ") + Utils.listToString(getArguments()) + " " + this.mth + " type: " + this.type
    }
}

package jadx.core.dex.instructions.args

import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.PhiInsn
import jadx.core.dex.nodes.DexNode
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils
import org.d.b
import org.d.c

class RegisterArg extends InsnArg implements Named {
    private static val LOG = c.a(RegisterArg.class)
    protected final Int regNum
    private SSAVar sVar

    constructor(Int i) {
        this.regNum = i
    }

    constructor(Int i, ArgType argType) {
        this.type = argType
        this.regNum = i
    }

    fun duplicate() {
        return duplicate(getRegNum(), this.sVar)
    }

    fun duplicate(Int i, SSAVar sSAVar) {
        RegisterArg registerArg = RegisterArg(i, getType())
        if (sSAVar != null) {
            registerArg.setSVar(sSAVar)
        }
        registerArg.copyAttributesFrom(this)
        return registerArg
    }

    fun equalRegisterAndType(RegisterArg registerArg) {
        return this.regNum == registerArg.regNum && this.type.equals(registerArg.type)
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj != null && (obj is RegisterArg)) {
            RegisterArg registerArg = (RegisterArg) obj
            if (this.regNum == registerArg.regNum && this.type.equals(registerArg.type)) {
                return this.sVar == null || this.sVar.equals(registerArg.getSVar())
            }
            return false
        }
        return false
    }

    fun getAssignInsn() {
        if (this.sVar == null) {
            return null
        }
        return this.sVar.getAssign().getParentInsn()
    }

    fun getConstValue(DexNode dexNode) {
        InsnNode assignInsn = getAssignInsn()
        if (assignInsn == null) {
            return null
        }
        return InsnUtils.getConstValueByInsn(dexNode, assignInsn)
    }

    @Override // jadx.core.dex.instructions.args.Named
    fun getName() {
        if (this.sVar == null) {
            return null
        }
        return this.sVar.getName()
    }

    fun getPhiAssignInsn() {
        PhiInsn usedInPhi = this.sVar.getUsedInPhi()
        if (usedInPhi != null) {
            return usedInPhi
        }
        InsnNode parentInsn = this.sVar.getAssign().getParentInsn()
        if (parentInsn == null || parentInsn.getType() != InsnType.PHI) {
            return null
        }
        return parentInsn
    }

    fun getRegNum() {
        return this.regNum
    }

    fun getSVar() {
        return this.sVar
    }

    fun hashCode() {
        return (this.regNum * 31) + this.type.hashCode()
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun isNameEquals(InsnArg insnArg) {
        String name = getName()
        if (name == null || !(insnArg is Named)) {
            return false
        }
        return name.equals(((Named) insnArg).getName())
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    fun isRegister() {
        return true
    }

    @Override // jadx.core.dex.instructions.args.InsnArg
    fun isThis() {
        InsnArg arg
        if ("this".equals(getName())) {
            return true
        }
        InsnNode assignInsn = getAssignInsn()
        if (assignInsn == null || assignInsn.getType() != InsnType.MOVE || (arg = assignInsn.getArg(0)) == this) {
            return false
        }
        return arg.isThis()
    }

    fun mergeDebugInfo(ArgType argType, String str) {
        setType(argType)
        setName(str)
    }

    @Override // jadx.core.dex.instructions.args.Named
    fun setName(String str) {
        if (this.sVar != null) {
            this.sVar.setName(str)
        }
    }

    Unit setSVar(SSAVar sSAVar) {
        this.sVar = sSAVar
    }

    @Override // jadx.core.dex.instructions.args.Typed
    fun setType(ArgType argType) {
        if (this.sVar != null) {
            this.sVar.setType(argType)
        }
    }

    fun toString() {
        StringBuilder sb = StringBuilder()
        sb.append("(r")
        sb.append(this.regNum)
        if (this.sVar != null) {
            sb.append("_").append(this.sVar.getVersion())
        }
        if (getName() != null) {
            sb.append(" '").append(getName()).append("'")
        }
        sb.append(" ")
        sb.append(this.type)
        sb.append(")")
        return sb.toString()
    }
}

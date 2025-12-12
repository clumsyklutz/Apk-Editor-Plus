package jadx.core.dex.instructions.args

import android.support.v7.widget.ActivityChooserView
import jadx.core.dex.attributes.AttrNode
import jadx.core.dex.instructions.PhiInsn
import java.util.ArrayList
import java.util.Iterator
import java.util.List

class SSAVar extends AttrNode {
    private RegisterArg assign
    private Int endUseAddr
    private final Int regNum
    private Int startUseAddr
    private ArgType type
    private Boolean typeImmutable
    private val useList = ArrayList(2)
    private PhiInsn usedInPhi
    private VarName varName
    private final Int version

    constructor(Int i, Int i2, RegisterArg registerArg) {
        this.regNum = i
        this.version = i2
        this.assign = registerArg
        registerArg.setSVar(this)
        this.startUseAddr = -1
        this.endUseAddr = -1
    }

    private fun calcUsageAddrRange() {
        Int iMax
        Int iMin
        Int iMin2
        Int iMax2
        Int offset
        Int offset2
        if (this.assign.getParentInsn() == null || (offset2 = this.assign.getParentInsn().getOffset()) < 0) {
            iMax = Integer.MIN_VALUE
            iMin = Integer.MAX_VALUE
        } else {
            iMin = Math.min(offset2, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)
            iMax = Math.max(offset2, Integer.MIN_VALUE)
        }
        Iterator it = this.useList.iterator()
        while (true) {
            iMin2 = iMin
            iMax2 = iMax
            if (!it.hasNext()) {
                break
            }
            RegisterArg registerArg = (RegisterArg) it.next()
            if (registerArg.getParentInsn() != null && (offset = registerArg.getParentInsn().getOffset()) >= 0) {
                iMin2 = Math.min(offset, iMin2)
                iMax2 = Math.max(offset, iMax2)
            }
            iMax = iMax2
            iMin = iMin2
        }
        if (iMin2 == Integer.MAX_VALUE || iMax2 == Integer.MIN_VALUE) {
            return
        }
        this.startUseAddr = iMin2
        this.endUseAddr = iMax2
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (!(obj is SSAVar)) {
            return false
        }
        SSAVar sSAVar = (SSAVar) obj
        return this.regNum == sSAVar.regNum && this.version == sSAVar.version
    }

    fun getAssign() {
        return this.assign
    }

    fun getEndAddr() {
        if (this.endUseAddr == -1) {
            calcUsageAddrRange()
        }
        return this.endUseAddr
    }

    fun getName() {
        if (this.varName == null) {
            return null
        }
        return this.varName.getName()
    }

    fun getRegNum() {
        return this.regNum
    }

    fun getStartAddr() {
        if (this.startUseAddr == -1) {
            calcUsageAddrRange()
        }
        return this.startUseAddr
    }

    fun getUseCount() {
        return this.useList.size()
    }

    fun getUseList() {
        return this.useList
    }

    fun getUsedInPhi() {
        return this.usedInPhi
    }

    fun getVarName() {
        return this.varName
    }

    fun getVariableUseCount() {
        return this.usedInPhi == null ? this.useList.size() : this.useList.size() + this.usedInPhi.getResult().getSVar().getUseCount()
    }

    fun getVersion() {
        return this.version
    }

    fun hashCode() {
        return (this.regNum * 31) + this.version
    }

    fun isTypeImmutable() {
        return this.typeImmutable
    }

    fun isUsedInPhi() {
        return this.usedInPhi != null
    }

    fun removeUse(RegisterArg registerArg) {
        Int size = this.useList.size()
        for (Int i = 0; i < size; i++) {
            if (this.useList.get(i) == registerArg) {
                this.useList.remove(i)
                return
            }
        }
    }

    fun setAssign(RegisterArg registerArg) {
        this.assign = registerArg
    }

    fun setName(String str) {
        if (str != null) {
            if (this.varName == null) {
                this.varName = VarName()
            }
            this.varName.setName(str)
        }
    }

    fun setType(ArgType argType) {
        if (this.typeImmutable) {
            argType = this.type
        } else {
            this.type = argType
        }
        this.assign.type = argType
        Int size = this.useList.size()
        for (Int i = 0; i < size; i++) {
            ((RegisterArg) this.useList.get(i)).type = argType
        }
    }

    fun setTypeImmutable(ArgType argType) {
        setType(argType)
        this.typeImmutable = true
    }

    fun setUsedInPhi(PhiInsn phiInsn) {
        this.usedInPhi = phiInsn
    }

    fun setVarName(VarName varName) {
        this.varName = varName
    }

    fun toString() {
        return "r" + this.regNum + "_" + this.version
    }

    fun use(RegisterArg registerArg) {
        if (registerArg.getSVar() != null) {
            registerArg.getSVar().removeUse(registerArg)
        }
        registerArg.setSVar(this)
        this.useList.add(registerArg)
    }
}

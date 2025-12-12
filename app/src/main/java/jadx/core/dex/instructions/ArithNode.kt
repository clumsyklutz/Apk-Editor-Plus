package jadx.core.dex.instructions

import com.a.b.d.a.f
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils

class ArithNode extends InsnNode {
    private final ArithOp op

    constructor(f fVar, ArithOp arithOp, ArgType argType, Boolean z) {
        super(InsnType.ARITH, 2)
        this.op = arithOp
        setResult(InsnArg.reg(fVar, 0, argType))
        Int iM = fVar.m()
        if (z) {
            if (iM == 1) {
                addReg(fVar, 0, argType)
                addLit(fVar, argType)
                return
            } else {
                if (iM == 2) {
                    addReg(fVar, 1, argType)
                    addLit(fVar, argType)
                    return
                }
                return
            }
        }
        if (iM == 2) {
            addReg(fVar, 0, argType)
            addReg(fVar, 1, argType)
        } else if (iM == 3) {
            addReg(fVar, 1, argType)
            addReg(fVar, 2, argType)
        }
    }

    constructor(ArithOp arithOp, RegisterArg registerArg, InsnArg insnArg) {
        this(arithOp, registerArg, registerArg, insnArg)
        add(AFlag.ARITH_ONEARG)
    }

    constructor(ArithOp arithOp, RegisterArg registerArg, InsnArg insnArg, InsnArg insnArg2) {
        super(InsnType.ARITH, 2)
        this.op = arithOp
        setResult(registerArg)
        addArg(insnArg)
        addArg(insnArg2)
    }

    fun getOp() {
        return this.op
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun isSame(InsnNode insnNode) {
        if (this == insnNode) {
            return true
        }
        if ((insnNode is ArithNode) && super.isSame(insnNode)) {
            return this.op == ((ArithNode) insnNode).op
        }
        return false
    }

    @Override // jadx.core.dex.nodes.InsnNode
    fun toString() {
        return InsnUtils.formatOffset(this.offset) + ": " + InsnUtils.insnTypeToString(this.insnType) + getResult() + " = " + getArg(0) + " " + this.op.getSymbol() + " " + getArg(1)
    }
}

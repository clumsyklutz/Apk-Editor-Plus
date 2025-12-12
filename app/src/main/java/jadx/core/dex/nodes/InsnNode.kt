package jadx.core.dex.nodes

import com.a.b.d.a.f
import com.d.a.a
import jadx.core.dex.attributes.nodes.LineAttrNode
import jadx.core.dex.instructions.InsnType
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.instructions.args.InsnArg
import jadx.core.dex.instructions.args.InsnWrapArg
import jadx.core.dex.instructions.args.LiteralArg
import jadx.core.dex.instructions.args.NamedArg
import jadx.core.dex.instructions.args.RegisterArg
import jadx.core.dex.instructions.args.SSAVar
import jadx.core.utils.InsnUtils
import jadx.core.utils.Utils
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.List

class InsnNode extends LineAttrNode {
    private static final a INSN_CLONER
    private final List arguments
    protected final InsnType insnType
    protected Int offset = -1
    private RegisterArg result

    static {
        a aVar = a()
        INSN_CLONER = aVar
        aVar.a(ArgType.class, SSAVar.class, LiteralArg.class, NamedArg.class)
        INSN_CLONER.b(RegisterArg.class)
    }

    constructor(InsnType insnType, Int i) {
        this.insnType = insnType
        if (i == 0) {
            this.arguments = Collections.emptyList()
        } else {
            this.arguments = ArrayList(i)
        }
    }

    fun wrapArg(InsnArg insnArg) {
        InsnNode insnNode = InsnNode(InsnType.ONE_ARG, 1)
        insnNode.addArg(insnArg)
        return insnNode
    }

    fun addArg(InsnArg insnArg) {
        insnArg.setParentInsn(this)
        this.arguments.add(insnArg)
    }

    protected fun addLit(Long j, ArgType argType) {
        addArg(InsnArg.lit(j, argType))
    }

    protected fun addLit(f fVar, ArgType argType) {
        addArg(InsnArg.lit(fVar, argType))
    }

    protected fun addReg(Int i, ArgType argType) {
        addArg(InsnArg.reg(i, argType))
    }

    protected fun addReg(f fVar, Int i, ArgType argType) {
        addArg(InsnArg.reg(fVar, i, argType))
    }

    fun canReorder() {
        switch (getType()) {
            case CONST:
            case CONST_STR:
            case CONST_CLASS:
            case CAST:
            case MOVE:
            case ARITH:
            case NEG:
            case CMP_L:
            case CMP_G:
            case CHECK_CAST:
            case INSTANCE_OF:
            case FILL_ARRAY:
            case FILLED_NEW_ARRAY:
            case NEW_ARRAY:
            case NEW_MULTIDIM_ARRAY:
            case STR_CONCAT:
                return true
            default:
                return false
        }
    }

    fun canReorderRecursive() {
        if (!canReorder()) {
            return false
        }
        for (InsnArg insnArg : getArguments()) {
            if (insnArg.isInsnWrap() && !((InsnWrapArg) insnArg).getWrapInsn().canReorderRecursive()) {
                return false
            }
        }
        return true
    }

    fun containsArg(RegisterArg registerArg) {
        for (InsnArg insnArg : this.arguments) {
            if (insnArg == registerArg || (insnArg.isRegister() && ((RegisterArg) insnArg).getRegNum() == registerArg.getRegNum())) {
                return true
            }
        }
        return false
    }

    fun copy() {
        return getClass() == InsnNode.class ? copyCommonParams(InsnNode(this.insnType, getArgsCount())) : (InsnNode) INSN_CLONER.a(this)
    }

    protected fun copyCommonParams(InsnNode insnNode) {
        insnNode.setResult(this.result)
        if (insnNode.getArgsCount() == 0) {
            for (InsnArg insnArg : getArguments()) {
                if (insnArg.isInsnWrap()) {
                    insnNode.addArg(InsnArg.wrapArg(((InsnWrapArg) insnArg).getWrapInsn().copy()))
                } else {
                    insnNode.addArg(insnArg)
                }
            }
        }
        insnNode.copyAttributesFrom(this)
        insnNode.copyLines(this)
        insnNode.setOffset(getOffset())
        return insnNode
    }

    public final Boolean equals(Object obj) {
        return super.equals(obj)
    }

    fun getArg(Int i) {
        return (InsnArg) this.arguments.get(i)
    }

    fun getArgsCount() {
        return this.arguments.size()
    }

    fun getArguments() {
        return this.arguments
    }

    fun getOffset() {
        return this.offset
    }

    fun getRegisterArgs(Collection collection) {
        for (InsnArg insnArg : getArguments()) {
            if (insnArg.isRegister()) {
                collection.add((RegisterArg) insnArg)
            } else if (insnArg.isInsnWrap()) {
                ((InsnWrapArg) insnArg).getWrapInsn().getRegisterArgs(collection)
            }
        }
    }

    fun getResult() {
        return this.result
    }

    fun getType() {
        return this.insnType
    }

    public final Int hashCode() {
        return super.hashCode()
    }

    fun isConstInsn() {
        switch (getType()) {
            case CONST:
            case CONST_STR:
            case CONST_CLASS:
                return true
            default:
                return false
        }
    }

    fun isSame(InsnNode insnNode) {
        Int i
        if (this == insnNode) {
            return true
        }
        if (this.insnType != insnNode.insnType || this.arguments.size() != insnNode.arguments.size()) {
            return false
        }
        Int size = this.arguments.size()
        for (Int i = 0; i < size; i++) {
            InsnArg insnArg = (InsnArg) this.arguments.get(i)
            InsnArg insnArg2 = (InsnArg) insnNode.arguments.get(i)
            if (!insnArg.isInsnWrap() || (insnArg2.isInsnWrap() && ((InsnWrapArg) insnArg).getWrapInsn().isSame(((InsnWrapArg) insnArg2).getWrapInsn()))) {
                continue
            }
            return false
        }
        return true
    }

    protected fun removeArg(InsnArg insnArg) {
        Int argsCount = getArgsCount()
        for (Int i = 0; i < argsCount; i++) {
            if (insnArg == this.arguments.get(i)) {
                this.arguments.remove(i)
                if (insnArg is RegisterArg) {
                    RegisterArg registerArg = (RegisterArg) insnArg
                    registerArg.getSVar().removeUse(registerArg)
                }
                return true
            }
        }
        return false
    }

    fun replaceArg(InsnArg insnArg, InsnArg insnArg2) {
        Int argsCount = getArgsCount()
        for (Int i = 0; i < argsCount; i++) {
            InsnArg insnArg3 = (InsnArg) this.arguments.get(i)
            if (insnArg3 == insnArg) {
                setArg(i, insnArg2)
                return true
            }
            if (insnArg3.isInsnWrap() && ((InsnWrapArg) insnArg3).getWrapInsn().replaceArg(insnArg, insnArg2)) {
                return true
            }
        }
        return false
    }

    fun setArg(Int i, InsnArg insnArg) {
        insnArg.setParentInsn(this)
        this.arguments.set(i, insnArg)
    }

    fun setOffset(Int i) {
        this.offset = i
    }

    fun setResult(RegisterArg registerArg) {
        if (registerArg != null) {
            registerArg.setParentInsn(this)
        }
        this.result = registerArg
    }

    fun toString() {
        return InsnUtils.formatOffset(this.offset) + ": " + InsnUtils.insnTypeToString(this.insnType) + (this.result == null ? "" : this.result + " = ") + Utils.listToString(this.arguments)
    }
}

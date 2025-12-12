package jadx.core.dex.instructions.args

import com.a.b.d.a.f
import jadx.core.dex.attributes.AFlag
import jadx.core.dex.nodes.InsnNode
import jadx.core.utils.InsnUtils
import java.util.ArrayList
import java.util.Iterator

abstract class InsnArg extends Typed {
    protected InsnNode parentInsn

    private fun getArgIndex(InsnNode insnNode, InsnArg insnArg) {
        Int argsCount = insnNode.getArgsCount()
        for (Int i = 0; i < argsCount; i++) {
            if (insnNode.getArg(i) == insnArg) {
                return i
            }
        }
        return -1
    }

    fun lit(Long j, ArgType argType) {
        return LiteralArg(j, argType)
    }

    fun lit(f fVar, ArgType argType) {
        return lit(fVar.h(), argType)
    }

    fun reg(Int i, ArgType argType) {
        return RegisterArg(i, argType)
    }

    fun reg(Int i, ArgType argType, Boolean z) {
        return z ? TypeImmutableArg(i, argType) : RegisterArg(i, argType)
    }

    fun reg(f fVar, Int i, ArgType argType) {
        return reg(InsnUtils.getArg(fVar, i), argType)
    }

    fun typeImmutableReg(Int i, ArgType argType) {
        return TypeImmutableArg(i, argType)
    }

    fun updateParentInsn(InsnNode insnNode, InsnNode insnNode2) {
        ArrayList arrayList = ArrayList()
        insnNode.getRegisterArgs(arrayList)
        Iterator it = arrayList.iterator()
        while (it.hasNext()) {
            ((RegisterArg) it.next()).setParentInsn(insnNode2)
        }
    }

    private fun wrap(InsnNode insnNode) {
        return InsnWrapArg(insnNode)
    }

    fun wrapArg(InsnNode insnNode) {
        switch (insnNode.getType()) {
            case MOVE:
            case CONST:
                return insnNode.getArg(0)
            case CONST_STR:
                InsnWrapArg insnWrapArgWrap = wrap(insnNode)
                insnWrapArgWrap.setType(ArgType.STRING)
                return insnWrapArgWrap
            case CONST_CLASS:
                InsnWrapArg insnWrapArgWrap2 = wrap(insnNode)
                insnWrapArgWrap2.setType(ArgType.CLASS)
                return insnWrapArgWrap2
            default:
                return wrap(insnNode)
        }
    }

    fun getParentInsn() {
        return this.parentInsn
    }

    fun isField() {
        return false
    }

    fun isInsnWrap() {
        return false
    }

    fun isLiteral() {
        return false
    }

    fun isNamed() {
        return false
    }

    fun isRegister() {
        return false
    }

    fun isThis() {
        return false
    }

    fun setParentInsn(InsnNode insnNode) {
        this.parentInsn = insnNode
    }

    fun wrapInstruction(InsnNode insnNode) {
        Int argIndex
        InsnNode insnNode2 = this.parentInsn
        if (insnNode2 == null || insnNode2 == insnNode || (argIndex = getArgIndex(insnNode2, this)) == -1) {
            return null
        }
        insnNode.add(AFlag.WRAPPED)
        InsnArg insnArgWrapArg = wrapArg(insnNode)
        insnNode2.setArg(argIndex, insnArgWrapArg)
        return insnArgWrapArg
    }
}

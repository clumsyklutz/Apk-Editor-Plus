package jadx.core.dex.nodes.parser

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.nodes.InsnNode
import jadx.core.dex.nodes.MethodNode

class FieldInitAttr implements IAttribute {
    public static val NULL_VALUE = constValue(null)
    private final MethodNode insnMth
    private final Object value
    private final InitType valueType

    public enum InitType {
        CONST,
        INSN
    }

    private constructor(InitType initType, Object obj, MethodNode methodNode) {
        this.value = obj
        this.valueType = initType
        this.insnMth = methodNode
    }

    fun constValue(Object obj) {
        return FieldInitAttr(InitType.CONST, obj, null)
    }

    fun insnValue(MethodNode methodNode, InsnNode insnNode) {
        return FieldInitAttr(InitType.INSN, insnNode, methodNode)
    }

    fun getInsn() {
        return (InsnNode) this.value
    }

    fun getInsnMth() {
        return this.insnMth
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.FIELD_INIT
    }

    fun getValue() {
        return this.value
    }

    fun getValueType() {
        return this.valueType
    }

    fun toString() {
        return "V=" + this.value
    }
}

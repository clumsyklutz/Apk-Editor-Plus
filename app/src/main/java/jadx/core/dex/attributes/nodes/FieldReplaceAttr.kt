package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.info.ClassInfo
import jadx.core.dex.instructions.args.InsnArg

class FieldReplaceAttr implements IAttribute {
    private final Object replaceObj
    private final ReplaceWith replaceType

    public enum ReplaceWith {
        CLASS_INSTANCE,
        VAR
    }

    constructor(ClassInfo classInfo) {
        this.replaceType = ReplaceWith.CLASS_INSTANCE
        this.replaceObj = classInfo
    }

    constructor(InsnArg insnArg) {
        this.replaceType = ReplaceWith.VAR
        this.replaceObj = insnArg
    }

    fun getClsRef() {
        return (ClassInfo) this.replaceObj
    }

    fun getReplaceType() {
        return this.replaceType
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.FIELD_REPLACE
    }

    fun getVarRef() {
        return (InsnArg) this.replaceObj
    }

    fun toString() {
        return "REPLACE: " + this.replaceType + " " + this.replaceObj
    }
}

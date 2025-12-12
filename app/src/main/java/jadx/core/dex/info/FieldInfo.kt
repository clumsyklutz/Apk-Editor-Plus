package jadx.core.dex.info

import com.a.a.w
import jadx.core.codegen.TypeGen
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.DexNode

class FieldInfo {
    private String alias
    private final ClassInfo declClass
    private final String name
    private final ArgType type

    private constructor(ClassInfo classInfo, String str, ArgType argType) {
        this.declClass = classInfo
        this.name = str
        this.type = argType
        this.alias = str
    }

    fun from(DexNode dexNode, ClassInfo classInfo, String str, ArgType argType) {
        return dexNode.getInfoStorage().getField(FieldInfo(classInfo, str, argType))
    }

    fun fromDex(DexNode dexNode, Int i) {
        w fieldId = dexNode.getFieldId(i)
        return from(dexNode, ClassInfo.fromDex(dexNode, fieldId.a()), dexNode.getString(fieldId.c()), dexNode.getType(fieldId.b()))
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        FieldInfo fieldInfo = (FieldInfo) obj
        return this.name.equals(fieldInfo.name) && this.type.equals(fieldInfo.type) && this.declClass.equals(fieldInfo.declClass)
    }

    public final String getAlias() {
        return this.alias
    }

    public final ClassInfo getDeclClass() {
        return this.declClass
    }

    public final String getFullId() {
        return this.declClass.getFullName() + Deobfuscator.CLASS_NAME_SEPARATOR + this.name + ":" + TypeGen.signature(this.type)
    }

    public final String getName() {
        return this.name
    }

    public final ArgType getType() {
        return this.type
    }

    public final Int hashCode() {
        return (((this.name.hashCode() * 31) + this.type.hashCode()) * 31) + this.declClass.hashCode()
    }

    public final Boolean isRenamed() {
        return !this.name.equals(this.alias)
    }

    public final Unit setAlias(String str) {
        this.alias = str
    }

    public final String toString() {
        return this.declClass + Deobfuscator.CLASS_NAME_SEPARATOR + this.name + " " + this.type
    }
}

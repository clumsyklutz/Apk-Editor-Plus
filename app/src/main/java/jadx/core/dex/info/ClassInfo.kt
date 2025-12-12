package jadx.core.dex.info

import jadx.core.deobf.Deobfuscator
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.DexNode
import jadx.core.utils.exceptions.JadxRuntimeException
import java.io.File

class ClassInfo {
    private ClassInfo alias
    private String fullName
    private String name
    private ClassInfo parentClass
    private String pkg
    private final ArgType type

    private constructor(DexNode dexNode, ArgType argType) {
        this(dexNode, argType, true)
    }

    private constructor(DexNode dexNode, ArgType argType, Boolean z) {
        if (!argType.isObject() || argType.isGeneric()) {
            throw JadxRuntimeException("Not class type: " + argType)
        }
        this.type = argType
        this.alias = this
        splitNames(dexNode, z)
    }

    fun extCls(DexNode dexNode, ArgType argType) {
        return fromName(dexNode, argType.getObject()).alias
    }

    fun fromDex(DexNode dexNode, Int i) {
        if (i == -1) {
            return null
        }
        return fromType(dexNode, dexNode.getType(i))
    }

    fun fromName(DexNode dexNode, String str) {
        return fromType(dexNode, ArgType.object(str))
    }

    fun fromType(DexNode dexNode, ArgType argType) {
        if (argType.isArray()) {
            argType = ArgType.OBJECT
        }
        ClassInfo cls = dexNode.getInfoStorage().getCls(argType)
        if (cls != null) {
            return cls
        }
        return dexNode.getInfoStorage().putCls(ClassInfo(dexNode, argType))
    }

    private fun splitNames(DexNode dexNode, Boolean z) {
        String object = this.type.getObject()
        Int iLastIndexOf = object.lastIndexOf(46)
        if (iLastIndexOf == -1) {
            this.pkg = ""
        } else {
            this.pkg = object.substring(0, iLastIndexOf)
            object = object.substring(iLastIndexOf + 1)
        }
        Int iLastIndexOf2 = object.lastIndexOf(36)
        if (!z || iLastIndexOf2 <= 0 || iLastIndexOf2 == object.length() - 1) {
            this.parentClass = null
        } else {
            this.parentClass = fromName(dexNode, this.pkg + Deobfuscator.CLASS_NAME_SEPARATOR + object.substring(0, iLastIndexOf2))
            object = object.substring(iLastIndexOf2 + 1)
        }
        this.name = object
        this.fullName = makeFullClsName(object, false)
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj is ClassInfo) {
            return this.type.equals(((ClassInfo) obj).type)
        }
        return false
    }

    public final ClassInfo getAlias() {
        return this.alias
    }

    public final String getFullName() {
        return this.fullName
    }

    public final String getFullPath() {
        ClassInfo alias = getAlias()
        return alias.getPackage().replace('.', File.separatorChar) + File.separatorChar + alias.getNameWithoutPackage().replace('.', '_')
    }

    public final String getNameWithoutPackage() {
        return this.parentClass == null ? this.name : this.parentClass.getNameWithoutPackage() + Deobfuscator.CLASS_NAME_SEPARATOR + this.name
    }

    public final String getPackage() {
        return this.pkg
    }

    public final ClassInfo getParentClass() {
        return this.parentClass
    }

    public final String getRawName() {
        return this.type.getObject()
    }

    public final String getShortName() {
        return this.name
    }

    public final ClassInfo getTopParentClass() {
        if (this.parentClass == null) {
            return null
        }
        ClassInfo topParentClass = this.parentClass.getTopParentClass()
        return topParentClass != null ? topParentClass : this.parentClass
    }

    public final ArgType getType() {
        return this.type
    }

    public final Int hashCode() {
        return this.fullName.hashCode()
    }

    public final Boolean isInner() {
        return this.parentClass != null
    }

    public final Boolean isRenamed() {
        return this.alias != this
    }

    public final String makeFullClsName(String str, Boolean z) {
        if (this.parentClass != null) {
            return this.parentClass.makeFullClsName(this.parentClass.getShortName(), z) + (z ? Deobfuscator.INNER_CLASS_SEPARATOR : Deobfuscator.CLASS_NAME_SEPARATOR) + str
        }
        return !this.pkg.isEmpty() ? this.pkg + Deobfuscator.CLASS_NAME_SEPARATOR + str : str
    }

    public final Unit notInner(DexNode dexNode) {
        splitNames(dexNode, false)
    }

    public final Unit rename(DexNode dexNode, String str) {
        ClassInfo classInfo = ClassInfo(dexNode, ArgType.object(str), isInner())
        if (this.alias.getFullName().equals(classInfo.getFullName())) {
            return
        }
        this.alias = classInfo
    }

    public final String toString() {
        return this.fullName
    }
}

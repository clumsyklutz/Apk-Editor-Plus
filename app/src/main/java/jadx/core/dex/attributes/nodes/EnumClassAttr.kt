package jadx.core.dex.attributes.nodes

import jadx.core.dex.attributes.AType
import jadx.core.dex.attributes.IAttribute
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.mods.ConstructorInsn
import jadx.core.dex.nodes.ClassNode
import jadx.core.dex.nodes.MethodNode
import java.util.ArrayList
import java.util.List

class EnumClassAttr implements IAttribute {
    private final List fields
    private MethodNode staticMethod

    class EnumField {
        private ClassNode cls
        private final ConstructorInsn constrInsn
        private final FieldInfo field
        private final Int startArg

        constructor(FieldInfo fieldInfo, ConstructorInsn constructorInsn, Int i) {
            this.field = fieldInfo
            this.constrInsn = constructorInsn
            this.startArg = i
        }

        fun getCls() {
            return this.cls
        }

        fun getConstrInsn() {
            return this.constrInsn
        }

        fun getField() {
            return this.field
        }

        fun getStartArg() {
            return this.startArg
        }

        fun setCls(ClassNode classNode) {
            this.cls = classNode
        }

        fun toString() {
            return this.field + "(" + this.constrInsn + ") " + this.cls
        }
    }

    constructor(Int i) {
        this.fields = ArrayList(i)
    }

    fun getFields() {
        return this.fields
    }

    fun getStaticMethod() {
        return this.staticMethod
    }

    @Override // jadx.core.dex.attributes.IAttribute
    fun getType() {
        return AType.ENUM_CLASS
    }

    fun setStaticMethod(MethodNode methodNode) {
        this.staticMethod = methodNode
    }

    fun toString() {
        return "Enum fields: " + this.fields
    }
}

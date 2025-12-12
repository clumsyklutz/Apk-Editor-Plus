package jadx.core.dex.nodes

import com.a.a.c
import jadx.core.deobf.Deobfuscator
import jadx.core.dex.attributes.nodes.LineAttrNode
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.info.FieldInfo
import jadx.core.dex.instructions.args.ArgType

class FieldNode extends LineAttrNode {
    private final AccessInfo accFlags
    private final FieldInfo fieldInfo
    private final ClassNode parent
    private ArgType type

    constructor(ClassNode classNode, c cVar) {
        this.parent = classNode
        this.fieldInfo = FieldInfo.fromDex(classNode.dex(), cVar.a())
        this.type = this.fieldInfo.getType()
        this.accFlags = AccessInfo(cVar.b(), AccessInfo.AFType.FIELD)
    }

    constructor(ClassNode classNode, FieldInfo fieldInfo, Int i) {
        this.parent = classNode
        this.fieldInfo = fieldInfo
        this.type = fieldInfo.getType()
        this.accFlags = AccessInfo(i, AccessInfo.AFType.FIELD)
    }

    fun equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return this.fieldInfo.equals(((FieldNode) obj).fieldInfo)
    }

    fun getAccessFlags() {
        return this.accFlags
    }

    fun getAlias() {
        return this.fieldInfo.getAlias()
    }

    fun getFieldInfo() {
        return this.fieldInfo
    }

    fun getName() {
        return this.fieldInfo.getName()
    }

    fun getParentClass() {
        return this.parent
    }

    fun getType() {
        return this.type
    }

    fun hashCode() {
        return this.fieldInfo.hashCode()
    }

    fun setType(ArgType argType) {
        this.type = argType
    }

    fun toString() {
        return this.fieldInfo.getDeclClass() + Deobfuscator.CLASS_NAME_SEPARATOR + this.fieldInfo.getName() + " :" + this.type
    }
}

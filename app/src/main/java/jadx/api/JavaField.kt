package jadx.api

import jadx.core.deobf.Deobfuscator
import jadx.core.dex.info.AccessInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.FieldNode

class JavaField implements JavaNode {
    private final FieldNode field
    private final JavaClass parent

    JavaField(FieldNode fieldNode, JavaClass javaClass) {
        this.field = fieldNode
        this.parent = javaClass
    }

    public final Boolean equals(Object obj) {
        return this == obj || ((obj is JavaField) && this.field.equals(((JavaField) obj).field))
    }

    public final AccessInfo getAccessFlags() {
        return this.field.getAccessFlags()
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getDeclaringClass() {
        return this.parent
    }

    @Override // jadx.api.JavaNode
    public final Int getDecompiledLine() {
        return this.field.getDecompiledLine()
    }

    @Override // jadx.api.JavaNode
    public final String getFullName() {
        return this.parent.getFullName() + Deobfuscator.CLASS_NAME_SEPARATOR + getName()
    }

    @Override // jadx.api.JavaNode
    public final String getName() {
        return this.field.getAlias()
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getTopParentClass() {
        return this.parent.getTopParentClass()
    }

    public final ArgType getType() {
        return this.field.getType()
    }

    public final Int hashCode() {
        return this.field.hashCode()
    }

    public final String toString() {
        return this.field.toString()
    }
}

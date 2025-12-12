package jadx.api

import jadx.core.dex.info.AccessInfo
import jadx.core.dex.instructions.args.ArgType
import jadx.core.dex.nodes.MethodNode
import java.util.List

class JavaMethod implements JavaNode {
    private final MethodNode mth
    private final JavaClass parent

    JavaMethod(JavaClass javaClass, MethodNode methodNode) {
        this.parent = javaClass
        this.mth = methodNode
    }

    public final Boolean equals(Object obj) {
        return this == obj || ((obj is JavaMethod) && this.mth.equals(((JavaMethod) obj).mth))
    }

    public final AccessInfo getAccessFlags() {
        return this.mth.getAccessFlags()
    }

    public final List getArguments() {
        return this.mth.getMethodInfo().getArgumentsTypes()
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getDeclaringClass() {
        return this.parent
    }

    @Override // jadx.api.JavaNode
    public final Int getDecompiledLine() {
        return this.mth.getDecompiledLine()
    }

    @Override // jadx.api.JavaNode
    public final String getFullName() {
        return this.mth.getMethodInfo().getFullName()
    }

    @Override // jadx.api.JavaNode
    public final String getName() {
        return this.mth.getAlias()
    }

    public final ArgType getReturnType() {
        return this.mth.getReturnType()
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getTopParentClass() {
        return this.parent.getTopParentClass()
    }

    public final Int hashCode() {
        return this.mth.hashCode()
    }

    public final Boolean isClassInit() {
        return this.mth.getMethodInfo().isClassInit()
    }

    public final Boolean isConstructor() {
        return this.mth.getMethodInfo().isConstructor()
    }

    public final String toString() {
        return this.mth.toString()
    }
}

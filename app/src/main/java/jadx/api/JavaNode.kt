package jadx.api

public interface JavaNode {
    JavaClass getDeclaringClass()

    Int getDecompiledLine()

    String getFullName()

    String getName()

    JavaClass getTopParentClass()
}

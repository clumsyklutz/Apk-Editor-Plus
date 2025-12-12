package jadx.api

import java.util.List

class JavaPackage implements JavaNode, Comparable {
    private final List classes
    private final String name

    JavaPackage(String str, List list) {
        this.name = str
        this.classes = list
    }

    @Override // java.lang.Comparable
    public final Int compareTo(JavaPackage javaPackage) {
        return this.name.compareTo(javaPackage.name)
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        return this.name.equals(((JavaPackage) obj).name)
    }

    public final List getClasses() {
        return this.classes
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getDeclaringClass() {
        return null
    }

    @Override // jadx.api.JavaNode
    public final Int getDecompiledLine() {
        return 0
    }

    @Override // jadx.api.JavaNode
    public final String getFullName() {
        return this.name
    }

    @Override // jadx.api.JavaNode
    public final String getName() {
        return this.name
    }

    @Override // jadx.api.JavaNode
    public final JavaClass getTopParentClass() {
        return null
    }

    public final Int hashCode() {
        return this.name.hashCode()
    }

    public final String toString() {
        return this.name
    }
}

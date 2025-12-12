package jadx.core.deobf

import jadx.core.dex.info.MethodInfo
import java.util.Set

class OverridedMethodsNode {
    private Set methods

    constructor(Set set) {
        this.methods = set
    }

    fun add(MethodInfo methodInfo) {
        this.methods.add(methodInfo)
    }

    fun contains(MethodInfo methodInfo) {
        return this.methods.contains(methodInfo)
    }

    fun getMethods() {
        return this.methods
    }
}

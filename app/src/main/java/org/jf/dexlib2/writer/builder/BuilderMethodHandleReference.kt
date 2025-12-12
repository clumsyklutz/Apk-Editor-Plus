package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.reference.BaseMethodHandleReference

class BuilderMethodHandleReference extends BaseMethodHandleReference implements BuilderReference {
    public Int index = -1
    public final BuilderReference memberReference
    public final Int methodHandleType

    constructor(Int i, BuilderReference builderReference) {
        this.methodHandleType = i
        this.memberReference = builderReference
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    fun getMemberReference() {
        return this.memberReference
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    fun getMethodHandleType() {
        return this.methodHandleType
    }
}

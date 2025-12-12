package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.reference.BaseMethodReference

class BuilderMethodReference extends BaseMethodReference implements BuilderReference {
    public final BuilderTypeReference definingClass
    public Int index = -1
    public final BuilderStringReference name
    public final BuilderMethodProtoReference proto

    constructor(BuilderTypeReference builderTypeReference, BuilderStringReference builderStringReference, BuilderMethodProtoReference builderMethodProtoReference) {
        this.definingClass = builderTypeReference
        this.name = builderStringReference
        this.proto = builderMethodProtoReference
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.definingClass.getType()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getName() {
        return this.name.getString()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    fun getParameterTypes() {
        return this.proto.parameterTypes
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.proto.returnType.getType()
    }
}

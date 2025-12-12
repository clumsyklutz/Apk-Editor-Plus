package org.jf.dexlib2.writer.builder

import java.util.List
import org.jf.dexlib2.base.reference.BaseMethodProtoReference

class BuilderMethodProtoReference extends BaseMethodProtoReference implements BuilderReference {
    public Int index = -1
    public final BuilderTypeList parameterTypes
    public final BuilderTypeReference returnType
    public final BuilderStringReference shorty

    constructor(BuilderStringReference builderStringReference, BuilderTypeList builderTypeList, BuilderTypeReference builderTypeReference) {
        this.shorty = builderStringReference
        this.parameterTypes = builderTypeList
        this.returnType = builderTypeReference
    }

    fun getIndex() {
        return this.index
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public List<? extends CharSequence> getParameterTypes() {
        return this.parameterTypes
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    fun getReturnType() {
        return this.returnType.getType()
    }
}

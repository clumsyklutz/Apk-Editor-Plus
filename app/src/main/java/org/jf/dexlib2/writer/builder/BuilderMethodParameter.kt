package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.BaseMethodParameter

class BuilderMethodParameter extends BaseMethodParameter {
    public final BuilderAnnotationSet annotations
    public final BuilderStringReference name
    public final BuilderTypeReference type

    constructor(BuilderTypeReference builderTypeReference, BuilderStringReference builderStringReference, BuilderAnnotationSet builderAnnotationSet) {
        this.type = builderTypeReference
        this.name = builderStringReference
        this.annotations = builderAnnotationSet
    }

    @Override // org.jf.dexlib2.iface.MethodParameter
    fun getAnnotations() {
        return this.annotations
    }

    @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    fun getName() {
        BuilderStringReference builderStringReference = this.name
        if (builderStringReference == null) {
            return null
        }
        return builderStringReference.getString()
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.type.getType()
    }
}

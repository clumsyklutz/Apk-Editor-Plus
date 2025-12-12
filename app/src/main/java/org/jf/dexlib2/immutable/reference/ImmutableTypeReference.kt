package org.jf.dexlib2.immutable.reference

import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.iface.reference.TypeReference
import org.jf.util.ImmutableConverter

class ImmutableTypeReference extends BaseTypeReference implements ImmutableReference {
    public final String type

    static {
        new ImmutableConverter<ImmutableTypeReference, TypeReference>() { // from class: org.jf.dexlib2.immutable.reference.ImmutableTypeReference.1
            @Override // org.jf.util.ImmutableConverter
            fun isImmutable(TypeReference typeReference) {
                return typeReference is ImmutableTypeReference
            }

            @Override // org.jf.util.ImmutableConverter
            fun makeImmutable(TypeReference typeReference) {
                return ImmutableTypeReference.of(typeReference)
            }
        }
    }

    constructor(String str) {
        this.type = str
    }

    fun of(TypeReference typeReference) {
        return typeReference is ImmutableTypeReference ? (ImmutableTypeReference) typeReference : ImmutableTypeReference(typeReference.getType())
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.type
    }
}

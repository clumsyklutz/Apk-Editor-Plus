package org.jf.dexlib2.immutable.reference

import org.jf.dexlib2.base.reference.BaseFieldReference
import org.jf.dexlib2.iface.reference.FieldReference

class ImmutableFieldReference extends BaseFieldReference implements ImmutableReference {
    public final String definingClass
    public final String name
    public final String type

    constructor(String str, String str2, String str3) {
        this.definingClass = str
        this.name = str2
        this.type = str3
    }

    fun of(FieldReference fieldReference) {
        return fieldReference is ImmutableFieldReference ? (ImmutableFieldReference) fieldReference : ImmutableFieldReference(fieldReference.getDefiningClass(), fieldReference.getName(), fieldReference.getType())
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getDefiningClass() {
        return this.definingClass
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getType() {
        return this.type
    }
}

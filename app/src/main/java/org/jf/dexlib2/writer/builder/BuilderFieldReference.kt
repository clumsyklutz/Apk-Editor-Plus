package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.reference.BaseFieldReference

class BuilderFieldReference extends BaseFieldReference implements BuilderReference {
    public final BuilderTypeReference definingClass
    public final BuilderTypeReference fieldType
    public Int index = -1
    public final BuilderStringReference name

    constructor(BuilderTypeReference builderTypeReference, BuilderStringReference builderStringReference, BuilderTypeReference builderTypeReference2) {
        this.definingClass = builderTypeReference
        this.name = builderStringReference
        this.fieldType = builderTypeReference2
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getDefiningClass() {
        return this.definingClass.getType()
    }

    fun getIndex() {
        return this.index
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getName() {
        return this.name.getString()
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getType() {
        return this.fieldType.getType()
    }
}

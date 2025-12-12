package org.jf.dexlib2.dexbacked.reference

import org.jf.dexlib2.base.reference.BaseFieldReference
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.reference.Reference

class DexBackedFieldReference extends BaseFieldReference {
    public final DexBackedDexFile dexFile
    public final Int fieldIndex

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.fieldIndex = i
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getDefiningClass() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readUshort(this.dexFile.getFieldSection().getOffset(this.fieldIndex) + 0))
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getName() {
        return this.dexFile.getStringSection().get(this.dexFile.getBuffer().readSmallUint(this.dexFile.getFieldSection().getOffset(this.fieldIndex) + 4))
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readUshort(this.dexFile.getFieldSection().getOffset(this.fieldIndex) + 2))
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
        Int i = this.fieldIndex
        if (i < 0 || i >= this.dexFile.getFieldSection().size()) {
            throw new Reference.InvalidReferenceException("field@" + this.fieldIndex)
        }
    }
}

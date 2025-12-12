package org.jf.dexlib2.dexbacked.reference

import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.reference.Reference

class DexBackedTypeReference extends BaseTypeReference {
    public final DexBackedDexFile dexFile
    public final Int typeIndex

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.typeIndex = i
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.dexFile.getTypeSection().get(this.typeIndex)
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
        Int i = this.typeIndex
        if (i < 0 || i >= this.dexFile.getTypeSection().size()) {
            throw new Reference.InvalidReferenceException("type@" + this.typeIndex)
        }
    }
}

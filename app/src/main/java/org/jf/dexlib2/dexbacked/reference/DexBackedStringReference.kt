package org.jf.dexlib2.dexbacked.reference

import org.jf.dexlib2.base.reference.BaseStringReference
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.iface.reference.Reference

class DexBackedStringReference extends BaseStringReference {
    public final DexBackedDexFile dexFile
    public final Int stringIndex

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        this.stringIndex = i
    }

    @Override // org.jf.dexlib2.iface.reference.StringReference
    fun getString() {
        return this.dexFile.getStringSection().get(this.stringIndex)
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    fun validateReference() throws Reference.InvalidReferenceException {
        Int i = this.stringIndex
        if (i < 0 || i >= this.dexFile.getStringSection().size()) {
            throw new Reference.InvalidReferenceException("string@" + this.stringIndex)
        }
    }
}

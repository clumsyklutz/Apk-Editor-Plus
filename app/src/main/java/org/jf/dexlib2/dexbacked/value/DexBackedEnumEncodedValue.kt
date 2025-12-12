package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.base.value.BaseEnumEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference
import org.jf.dexlib2.iface.reference.FieldReference

class DexBackedEnumEncodedValue extends BaseEnumEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int fieldIndex

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, Int i) {
        this.dexFile = dexBackedDexFile
        this.fieldIndex = dexReader.readSizedSmallUint(i + 1)
    }

    @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
    fun getValue() {
        return DexBackedFieldReference(this.dexFile, this.fieldIndex)
    }
}

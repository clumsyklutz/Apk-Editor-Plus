package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.base.value.BaseTypeEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader

class DexBackedTypeEncodedValue extends BaseTypeEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int typeIndex

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, Int i) {
        this.dexFile = dexBackedDexFile
        this.typeIndex = dexReader.readSizedSmallUint(i + 1)
    }

    @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
    fun getValue() {
        return this.dexFile.getTypeSection().get(this.typeIndex)
    }
}

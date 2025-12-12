package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.base.value.BaseStringEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader

class DexBackedStringEncodedValue extends BaseStringEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int stringIndex

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, Int i) {
        this.dexFile = dexBackedDexFile
        this.stringIndex = dexReader.readSizedSmallUint(i + 1)
    }

    @Override // org.jf.dexlib2.iface.value.StringEncodedValue
    fun getValue() {
        return this.dexFile.getStringSection().get(this.stringIndex)
    }
}

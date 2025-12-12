package org.jf.dexlib2.dexbacked.value

import java.util.List
import org.jf.dexlib2.base.value.BaseArrayEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.util.VariableSizeList
import org.jf.dexlib2.iface.value.EncodedValue

class DexBackedArrayEncodedValue extends BaseArrayEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int elementCount
    public final Int encodedArrayOffset

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        this.dexFile = dexBackedDexFile
        Int smallUleb128 = dexReader.readSmallUleb128()
        this.elementCount = smallUleb128
        this.encodedArrayOffset = dexReader.getOffset()
        skipElementsFrom(dexReader, smallUleb128)
    }

    fun skipElementsFrom(DexReader dexReader, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            DexBackedEncodedValue.skipFrom(dexReader)
        }
    }

    fun skipFrom(DexReader dexReader) {
        skipElementsFrom(dexReader, dexReader.readSmallUleb128())
    }

    @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
    public List<? extends EncodedValue> getValue() {
        return new VariableSizeList<EncodedValue>(this.dexFile.getDataBuffer(), this.encodedArrayOffset, this.elementCount) { // from class: org.jf.dexlib2.dexbacked.value.DexBackedArrayEncodedValue.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeList
            fun readNextItem(DexReader dexReader, Int i) {
                return DexBackedEncodedValue.readFrom(DexBackedArrayEncodedValue.this.dexFile, dexReader)
            }
        }
    }
}

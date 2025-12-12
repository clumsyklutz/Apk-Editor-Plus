package org.jf.dexlib2.dexbacked.value

import java.util.Set
import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue
import org.jf.dexlib2.dexbacked.DexBackedAnnotationElement
import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.util.VariableSizeSet

class DexBackedAnnotationEncodedValue extends BaseAnnotationEncodedValue {
    public final DexBackedDexFile dexFile
    public final Int elementCount
    public final Int elementsOffset
    public final String type

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        this.dexFile = dexBackedDexFile
        this.type = dexBackedDexFile.getTypeSection().get(dexReader.readSmallUleb128())
        Int smallUleb128 = dexReader.readSmallUleb128()
        this.elementCount = smallUleb128
        this.elementsOffset = dexReader.getOffset()
        skipElements(dexReader, smallUleb128)
    }

    fun skipElements(DexReader dexReader, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            dexReader.skipUleb128()
            DexBackedEncodedValue.skipFrom(dexReader)
        }
    }

    fun skipFrom(DexReader dexReader) {
        dexReader.skipUleb128()
        skipElements(dexReader, dexReader.readSmallUleb128())
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
    public Set<? extends DexBackedAnnotationElement> getElements() {
        return new VariableSizeSet<DexBackedAnnotationElement>(this.dexFile.getDataBuffer(), this.elementsOffset, this.elementCount) { // from class: org.jf.dexlib2.dexbacked.value.DexBackedAnnotationEncodedValue.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeSet
            fun readNextItem(DexReader dexReader, Int i) {
                return DexBackedAnnotationElement(DexBackedAnnotationEncodedValue.this.dexFile, dexReader)
            }
        }
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue
    fun getType() {
        return this.type
    }
}

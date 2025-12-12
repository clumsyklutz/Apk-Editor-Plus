package org.jf.dexlib2.dexbacked

import org.jf.dexlib2.base.BaseAnnotationElement
import org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue

class DexBackedAnnotationElement extends BaseAnnotationElement {
    public final DexBackedDexFile dexFile
    public final Int nameIndex
    public final EncodedValue value

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        this.dexFile = dexBackedDexFile
        this.nameIndex = dexReader.readSmallUleb128()
        this.value = DexBackedEncodedValue.readFrom(dexBackedDexFile, dexReader)
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    fun getName() {
        return this.dexFile.getStringSection().get(this.nameIndex)
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    fun getValue() {
        return this.value
    }
}

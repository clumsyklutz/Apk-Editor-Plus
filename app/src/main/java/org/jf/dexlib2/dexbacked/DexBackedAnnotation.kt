package org.jf.dexlib2.dexbacked

import java.util.Set
import org.jf.dexlib2.base.BaseAnnotation
import org.jf.dexlib2.dexbacked.util.VariableSizeSet

class DexBackedAnnotation extends BaseAnnotation {
    public final DexBackedDexFile dexFile
    public final Int elementsOffset
    public final Int typeIndex
    public final Int visibility

    constructor(DexBackedDexFile dexBackedDexFile, Int i) {
        this.dexFile = dexBackedDexFile
        DexReader<? extends DexBuffer> dexReader = dexBackedDexFile.getDataBuffer().readerAt(i)
        this.visibility = dexReader.readUbyte()
        this.typeIndex = dexReader.readSmallUleb128()
        this.elementsOffset = dexReader.getOffset()
    }

    @Override // org.jf.dexlib2.iface.Annotation
    public Set<? extends DexBackedAnnotationElement> getElements() {
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.elementsOffset)
        return new VariableSizeSet<DexBackedAnnotationElement>(this.dexFile.getDataBuffer(), dexReader.getOffset(), dexReader.readSmallUleb128()) { // from class: org.jf.dexlib2.dexbacked.DexBackedAnnotation.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeSet
            fun readNextItem(DexReader dexReader2, Int i) {
                return DexBackedAnnotationElement(DexBackedAnnotation.this.dexFile, dexReader2)
            }
        }
    }

    @Override // org.jf.dexlib2.iface.Annotation
    fun getType() {
        return this.dexFile.getTypeSection().get(this.typeIndex)
    }

    @Override // org.jf.dexlib2.iface.Annotation
    fun getVisibility() {
        return this.visibility
    }
}

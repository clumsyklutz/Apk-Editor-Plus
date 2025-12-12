package org.jf.dexlib2.dexbacked

import com.google.common.collect.ImmutableSet
import java.util.Collection
import java.util.EnumSet
import java.util.Set
import org.jf.dexlib2.HiddenApiRestriction
import org.jf.dexlib2.base.reference.BaseFieldReference
import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
import org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.Field
import org.jf.dexlib2.iface.value.EncodedValue

class DexBackedField extends BaseFieldReference implements Field {
    public final Int accessFlags
    public final Int annotationSetOffset
    public final ClassDef classDef
    public final DexBackedDexFile dexFile
    public Int fieldIdItemOffset
    public final Int fieldIndex
    public final Int hiddenApiRestrictions
    public final EncodedValue initialValue

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, DexBackedClassDef dexBackedClassDef, Int i, AnnotationsDirectory.AnnotationIterator annotationIterator, Int i2) {
        this.dexFile = dexBackedDexFile
        this.classDef = dexBackedClassDef
        dexReader.getOffset()
        Int largeUleb128 = dexReader.readLargeUleb128() + i
        this.fieldIndex = largeUleb128
        this.accessFlags = dexReader.readSmallUleb128()
        this.annotationSetOffset = annotationIterator.seekTo(largeUleb128)
        this.initialValue = null
        this.hiddenApiRestrictions = i2
    }

    constructor(DexBackedDexFile dexBackedDexFile, DexReader dexReader, DexBackedClassDef dexBackedClassDef, Int i, EncodedArrayItemIterator encodedArrayItemIterator, AnnotationsDirectory.AnnotationIterator annotationIterator, Int i2) {
        this.dexFile = dexBackedDexFile
        this.classDef = dexBackedClassDef
        dexReader.getOffset()
        Int largeUleb128 = dexReader.readLargeUleb128() + i
        this.fieldIndex = largeUleb128
        this.accessFlags = dexReader.readSmallUleb128()
        this.annotationSetOffset = annotationIterator.seekTo(largeUleb128)
        encodedArrayItemIterator.getReaderOffset()
        this.initialValue = encodedArrayItemIterator.getNextOrNull()
        this.hiddenApiRestrictions = i2
    }

    fun skipFields(DexReader dexReader, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            dexReader.skipUleb128()
            dexReader.skipUleb128()
        }
    }

    @Override // org.jf.dexlib2.iface.Field
    fun getAccessFlags() {
        return this.accessFlags
    }

    @Override // org.jf.dexlib2.iface.Field
    public Set<? extends DexBackedAnnotation> getAnnotations() {
        return AnnotationsDirectory.getAnnotations(this.dexFile, this.annotationSetOffset)
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getDefiningClass() {
        return this.classDef.getType()
    }

    public final Int getFieldIdItemOffset() {
        if (this.fieldIdItemOffset == 0) {
            this.fieldIdItemOffset = this.dexFile.getFieldSection().getOffset(this.fieldIndex)
        }
        return this.fieldIdItemOffset
    }

    @Override // org.jf.dexlib2.iface.Field
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        Int i = this.hiddenApiRestrictions
        return i == 7 ? ImmutableSet.of() : EnumSet.copyOf((Collection) HiddenApiRestriction.getAllFlags(i))
    }

    @Override // org.jf.dexlib2.iface.Field
    fun getInitialValue() {
        return this.initialValue
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getName() {
        return this.dexFile.getStringSection().get(this.dexFile.getBuffer().readSmallUint(getFieldIdItemOffset() + 4))
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    fun getType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readUshort(getFieldIdItemOffset() + 2))
    }
}

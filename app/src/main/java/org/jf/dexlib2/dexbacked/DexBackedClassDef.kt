package org.jf.dexlib2.dexbacked

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import java.util.AbstractList
import java.util.Iterator
import java.util.List
import java.util.Set
import org.jf.dexlib2.base.reference.BaseTypeReference
import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
import org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
import org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
import org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference

class DexBackedClassDef extends BaseTypeReference implements ClassDef {
    public AnnotationsDirectory annotationsDirectory
    public final Int classDefOffset
    public final DexBackedDexFile dexFile
    public final Int directMethodCount
    public final HiddenApiRestrictionsReader hiddenApiRestrictionsReader
    public final Int instanceFieldCount
    public final Int staticFieldCount
    public final Int staticFieldsOffset
    public final Int virtualMethodCount
    public Int instanceFieldsOffset = 0
    public Int directMethodsOffset = 0
    public Int virtualMethodsOffset = 0

    /* renamed from: org.jf.dexlib2.dexbacked.DexBackedClassDef$5, reason: invalid class name */
    class AnonymousClass5 implements Iterable<DexBackedMethod> {
        public final AnnotationsDirectory.AnnotationIterator methodAnnotationIterator
        public final AnnotationsDirectory.AnnotationIterator parameterAnnotationIterator
        public final /* synthetic */ AnnotationsDirectory val$annotationsDirectory
        public final /* synthetic */ Iterator val$hiddenApiRestrictionIterator
        public final /* synthetic */ Int val$methodsStartOffset
        public final /* synthetic */ Boolean val$skipDuplicates

        constructor(AnnotationsDirectory annotationsDirectory, Int i, Iterator it, Boolean z) {
            this.val$annotationsDirectory = annotationsDirectory
            this.val$methodsStartOffset = i
            this.val$hiddenApiRestrictionIterator = it
            this.val$skipDuplicates = z
            this.methodAnnotationIterator = annotationsDirectory.getMethodAnnotationIterator()
            this.parameterAnnotationIterator = annotationsDirectory.getParameterAnnotationIterator()
        }

        @Override // java.lang.Iterable
        public Iterator<DexBackedMethod> iterator() {
            return new VariableSizeLookaheadIterator<DexBackedMethod>(DexBackedClassDef.this.dexFile.getDataBuffer(), this.val$methodsStartOffset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.5.1
                public Int count
                public Int previousIndex
                public MethodReference previousMethod

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                fun readNextItem(DexReader dexReader) {
                    DexBackedMethod dexBackedMethod
                    MethodReference methodReference
                    ImmutableMethodReference immutableMethodReferenceOf
                    do {
                        Int i = this.count + 1
                        this.count = i
                        if (i > DexBackedClassDef.this.virtualMethodCount) {
                            return endOfData()
                        }
                        Iterator it = AnonymousClass5.this.val$hiddenApiRestrictionIterator
                        Int iIntValue = it != null ? ((Integer) it.next()).intValue() : 7
                        AnonymousClass5 anonymousClass5 = AnonymousClass5.this
                        DexBackedClassDef dexBackedClassDef = DexBackedClassDef.this
                        dexBackedMethod = DexBackedMethod(dexBackedClassDef.dexFile, dexReader, dexBackedClassDef, this.previousIndex, anonymousClass5.methodAnnotationIterator, anonymousClass5.parameterAnnotationIterator, iIntValue)
                        methodReference = this.previousMethod
                        immutableMethodReferenceOf = ImmutableMethodReference.of(dexBackedMethod)
                        this.previousMethod = immutableMethodReferenceOf
                        this.previousIndex = dexBackedMethod.methodIndex
                        if (!AnonymousClass5.this.val$skipDuplicates || methodReference == null) {
                            break
                        }
                    } while (methodReference.equals(immutableMethodReferenceOf));
                    return dexBackedMethod
                }
            }
        }
    }

    class HiddenApiRestrictionsReader {
        public Int directMethodsStartOffset
        public Int instanceFieldsStartOffset
        public final Int startOffset
        public Int virtualMethodsStartOffset

        constructor(Int i) {
            this.startOffset = i
        }

        public final Int getDirectMethodsStartOffset() {
            if (this.directMethodsStartOffset == 0) {
                DexReader<? extends DexBuffer> dexReader = DexBackedClassDef.this.dexFile.getDataBuffer().readerAt(getInstanceFieldsStartOffset())
                for (Int i = 0; i < DexBackedClassDef.this.instanceFieldCount; i++) {
                    dexReader.readSmallUleb128()
                }
                this.directMethodsStartOffset = dexReader.getOffset()
            }
            return this.directMethodsStartOffset
        }

        public final Int getInstanceFieldsStartOffset() {
            if (this.instanceFieldsStartOffset == 0) {
                DexReader<? extends DexBuffer> dexReader = DexBackedClassDef.this.dexFile.getDataBuffer().readerAt(this.startOffset)
                for (Int i = 0; i < DexBackedClassDef.this.staticFieldCount; i++) {
                    dexReader.readSmallUleb128()
                }
                this.instanceFieldsStartOffset = dexReader.getOffset()
            }
            return this.instanceFieldsStartOffset
        }

        public final Iterator<Integer> getRestrictionsForDirectMethods() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), getDirectMethodsStartOffset(), DexBackedClassDef.this.directMethodCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.3
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator, java.util.ListIterator, java.util.Iterator
                fun next() {
                    if (nextIndex() == DexBackedClassDef.this.directMethodCount) {
                        HiddenApiRestrictionsReader.this.virtualMethodsStartOffset = getReaderOffset()
                    }
                    return (Integer) super.next()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                fun readNextItem(DexReader<? extends DexBuffer> dexReader, Int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128())
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                public /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, Int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i)
                }
            }
        }

        public final Iterator<Integer> getRestrictionsForInstanceFields() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), getInstanceFieldsStartOffset(), DexBackedClassDef.this.instanceFieldCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.2
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator, java.util.ListIterator, java.util.Iterator
                fun next() {
                    if (nextIndex() == DexBackedClassDef.this.instanceFieldCount) {
                        HiddenApiRestrictionsReader.this.directMethodsStartOffset = getReaderOffset()
                    }
                    return (Integer) super.next()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                fun readNextItem(DexReader<? extends DexBuffer> dexReader, Int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128())
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                public /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, Int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i)
                }
            }
        }

        public final VariableSizeListIterator<Integer> getRestrictionsForStaticFields() {
            return new VariableSizeListIterator<Integer>(DexBackedClassDef.this.dexFile.getDataBuffer(), this.startOffset, DexBackedClassDef.this.staticFieldCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.1
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator, java.util.ListIterator, java.util.Iterator
                fun next() {
                    if (nextIndex() == DexBackedClassDef.this.staticFieldCount) {
                        HiddenApiRestrictionsReader.this.instanceFieldsStartOffset = getReaderOffset()
                    }
                    return (Integer) super.next()
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                fun readNextItem(DexReader<? extends DexBuffer> dexReader, Int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128())
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                public /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, Int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i)
                }
            }
        }

        public final Iterator<Integer> getRestrictionsForVirtualMethods() {
            return new VariableSizeListIterator<Integer>(this, DexBackedClassDef.this.dexFile.getDataBuffer(), getVirtualMethodsStartOffset(), DexBackedClassDef.this.virtualMethodCount) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.HiddenApiRestrictionsReader.4
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                fun readNextItem(DexReader<? extends DexBuffer> dexReader, Int i) {
                    return Integer.valueOf(dexReader.readSmallUleb128())
                }

                @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
                public /* bridge */ /* synthetic */ Integer readNextItem(DexReader dexReader, Int i) {
                    return readNextItem((DexReader<? extends DexBuffer>) dexReader, i)
                }
            }
        }

        public final Int getVirtualMethodsStartOffset() {
            if (this.virtualMethodsStartOffset == 0) {
                DexReader<? extends DexBuffer> dexReader = DexBackedClassDef.this.dexFile.getDataBuffer().readerAt(getDirectMethodsStartOffset())
                for (Int i = 0; i < DexBackedClassDef.this.directMethodCount; i++) {
                    dexReader.readSmallUleb128()
                }
                this.virtualMethodsStartOffset = dexReader.getOffset()
            }
            return this.virtualMethodsStartOffset
        }
    }

    constructor(DexBackedDexFile dexBackedDexFile, Int i, Int i2) {
        this.dexFile = dexBackedDexFile
        this.classDefOffset = i
        Int smallUint = dexBackedDexFile.getBuffer().readSmallUint(i + 24)
        if (smallUint == 0) {
            this.staticFieldsOffset = -1
            this.staticFieldCount = 0
            this.instanceFieldCount = 0
            this.directMethodCount = 0
            this.virtualMethodCount = 0
        } else {
            DexReader<? extends DexBuffer> dexReader = dexBackedDexFile.getDataBuffer().readerAt(smallUint)
            this.staticFieldCount = dexReader.readSmallUleb128()
            this.instanceFieldCount = dexReader.readSmallUleb128()
            this.directMethodCount = dexReader.readSmallUleb128()
            this.virtualMethodCount = dexReader.readSmallUleb128()
            this.staticFieldsOffset = dexReader.getOffset()
        }
        if (i2 != 0) {
            this.hiddenApiRestrictionsReader = HiddenApiRestrictionsReader(i2)
        } else {
            this.hiddenApiRestrictionsReader = null
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getAccessFlags() {
        return this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 4)
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends DexBackedAnnotation> getAnnotations() {
        return getAnnotationsDirectory().getClassAnnotations()
    }

    public final AnnotationsDirectory getAnnotationsDirectory() {
        if (this.annotationsDirectory == null) {
            this.annotationsDirectory = AnnotationsDirectory.newOrEmpty(this.dexFile, this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 20))
        }
        return this.annotationsDirectory
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedMethod> getDirectMethods() {
        return getDirectMethods(true)
    }

    public Iterable<? extends DexBackedMethod> getDirectMethods(final Boolean z) {
        if (this.directMethodCount <= 0) {
            Int i = this.directMethodsOffset
            if (i > 0) {
                this.virtualMethodsOffset = i
            }
            return ImmutableSet.of()
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getDirectMethodsOffset())
        val annotationsDirectory = getAnnotationsDirectory()
        val offset = dexReader.getOffset()
        HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader
        val restrictionsForDirectMethods = hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForDirectMethods()
        return new Iterable<DexBackedMethod>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.4
            @Override // java.lang.Iterable
            public Iterator<DexBackedMethod> iterator() {
                final AnnotationsDirectory.AnnotationIterator methodAnnotationIterator = annotationsDirectory.getMethodAnnotationIterator()
                final AnnotationsDirectory.AnnotationIterator parameterAnnotationIterator = annotationsDirectory.getParameterAnnotationIterator()
                return new VariableSizeLookaheadIterator<DexBackedMethod>(DexBackedClassDef.this.dexFile.getDataBuffer(), offset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.4.1
                    public Int count
                    public Int previousIndex
                    public MethodReference previousMethod

                    @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                    fun readNextItem(DexReader dexReader2) {
                        DexBackedMethod dexBackedMethod
                        MethodReference methodReference
                        ImmutableMethodReference immutableMethodReferenceOf
                        do {
                            Int i2 = this.count + 1
                            this.count = i2
                            if (i2 > DexBackedClassDef.this.directMethodCount) {
                                DexBackedClassDef.this.virtualMethodsOffset = dexReader2.getOffset()
                                return endOfData()
                            }
                            Iterator it = restrictionsForDirectMethods
                            Int iIntValue = it != null ? ((Integer) it.next()).intValue() : 7
                            DexBackedClassDef dexBackedClassDef = DexBackedClassDef.this
                            dexBackedMethod = DexBackedMethod(dexBackedClassDef.dexFile, dexReader2, dexBackedClassDef, this.previousIndex, methodAnnotationIterator, parameterAnnotationIterator, iIntValue)
                            methodReference = this.previousMethod
                            immutableMethodReferenceOf = ImmutableMethodReference.of(dexBackedMethod)
                            this.previousMethod = immutableMethodReferenceOf
                            this.previousIndex = dexBackedMethod.methodIndex
                            if (!z || methodReference == null) {
                                break
                            }
                        } while (methodReference.equals(immutableMethodReferenceOf));
                        return dexBackedMethod
                    }
                }
            }
        }
    }

    public final Int getDirectMethodsOffset() {
        Int i = this.directMethodsOffset
        if (i > 0) {
            return i
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getInstanceFieldsOffset())
        DexBackedField.skipFields(dexReader, this.instanceFieldCount)
        Int offset = dexReader.getOffset()
        this.directMethodsOffset = offset
        return offset
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedField> getInstanceFields() {
        return getInstanceFields(true)
    }

    public Iterable<? extends DexBackedField> getInstanceFields(final Boolean z) {
        if (this.instanceFieldCount <= 0) {
            Int i = this.instanceFieldsOffset
            if (i > 0) {
                this.directMethodsOffset = i
            }
            return ImmutableSet.of()
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getInstanceFieldsOffset())
        val annotationsDirectory = getAnnotationsDirectory()
        val offset = dexReader.getOffset()
        HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader
        val restrictionsForInstanceFields = hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForInstanceFields()
        return new Iterable<DexBackedField>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.3
            @Override // java.lang.Iterable
            public Iterator<DexBackedField> iterator() {
                final AnnotationsDirectory.AnnotationIterator fieldAnnotationIterator = annotationsDirectory.getFieldAnnotationIterator()
                return new VariableSizeLookaheadIterator<DexBackedField>(DexBackedClassDef.this.dexFile.getDataBuffer(), offset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.3.1
                    public Int count
                    public FieldReference previousField
                    public Int previousIndex

                    @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                    fun readNextItem(DexReader dexReader2) {
                        DexBackedField dexBackedField
                        FieldReference fieldReference
                        ImmutableFieldReference immutableFieldReferenceOf
                        do {
                            Int i2 = this.count + 1
                            this.count = i2
                            if (i2 > DexBackedClassDef.this.instanceFieldCount) {
                                DexBackedClassDef.this.directMethodsOffset = dexReader2.getOffset()
                                return endOfData()
                            }
                            Iterator it = restrictionsForInstanceFields
                            Int iIntValue = it != null ? ((Integer) it.next()).intValue() : 7
                            DexBackedClassDef dexBackedClassDef = DexBackedClassDef.this
                            dexBackedField = DexBackedField(dexBackedClassDef.dexFile, dexReader2, dexBackedClassDef, this.previousIndex, fieldAnnotationIterator, iIntValue)
                            fieldReference = this.previousField
                            immutableFieldReferenceOf = ImmutableFieldReference.of(dexBackedField)
                            this.previousField = immutableFieldReferenceOf
                            this.previousIndex = dexBackedField.fieldIndex
                            if (!z || fieldReference == null) {
                                break
                            }
                        } while (fieldReference.equals(immutableFieldReferenceOf));
                        return dexBackedField
                    }
                }
            }
        }
    }

    public final Int getInstanceFieldsOffset() {
        Int i = this.instanceFieldsOffset
        if (i > 0) {
            return i
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.staticFieldsOffset)
        DexBackedField.skipFields(dexReader, this.staticFieldCount)
        Int offset = dexReader.getOffset()
        this.instanceFieldsOffset = offset
        return offset
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        val smallUint = this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 12)
        if (smallUint <= 0) {
            return ImmutableList.of()
        }
        val smallUint2 = this.dexFile.getDataBuffer().readSmallUint(smallUint)
        return new AbstractList<String>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.1
            @Override // java.util.AbstractList, java.util.List
            fun get(Int i) {
                return DexBackedClassDef.this.dexFile.getTypeSection().get(DexBackedClassDef.this.dexFile.getDataBuffer().readUshort(smallUint + 4 + (i * 2)))
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            fun size() {
                return smallUint2
            }
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSourceFile() {
        return this.dexFile.getStringSection().getOptional(this.dexFile.getBuffer().readOptionalUint(this.classDefOffset + 16))
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedField> getStaticFields() {
        return getStaticFields(true)
    }

    public Iterable<? extends DexBackedField> getStaticFields(final Boolean z) {
        if (this.staticFieldCount <= 0) {
            this.instanceFieldsOffset = this.staticFieldsOffset
            return ImmutableSet.of()
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.staticFieldsOffset)
        val annotationsDirectory = getAnnotationsDirectory()
        val smallUint = this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 28)
        val offset = dexReader.getOffset()
        HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader
        val restrictionsForStaticFields = hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForStaticFields()
        return new Iterable<DexBackedField>() { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.2
            @Override // java.lang.Iterable
            public Iterator<DexBackedField> iterator() {
                final AnnotationsDirectory.AnnotationIterator fieldAnnotationIterator = annotationsDirectory.getFieldAnnotationIterator()
                val encodedArrayItemIteratorNewOrEmpty = EncodedArrayItemIterator.newOrEmpty(DexBackedClassDef.this.dexFile, smallUint)
                return new VariableSizeLookaheadIterator<DexBackedField>(DexBackedClassDef.this.dexFile.getDataBuffer(), offset) { // from class: org.jf.dexlib2.dexbacked.DexBackedClassDef.2.1
                    public Int count
                    public FieldReference previousField
                    public Int previousIndex

                    @Override // org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator
                    fun readNextItem(DexReader dexReader2) {
                        DexBackedField dexBackedField
                        FieldReference fieldReference
                        ImmutableFieldReference immutableFieldReferenceOf
                        do {
                            Int i = this.count + 1
                            this.count = i
                            if (i > DexBackedClassDef.this.staticFieldCount) {
                                DexBackedClassDef.this.instanceFieldsOffset = dexReader2.getOffset()
                                return endOfData()
                            }
                            Iterator it = restrictionsForStaticFields
                            Int iIntValue = it != null ? ((Integer) it.next()).intValue() : 7
                            DexBackedClassDef dexBackedClassDef = DexBackedClassDef.this
                            dexBackedField = DexBackedField(dexBackedClassDef.dexFile, dexReader2, dexBackedClassDef, this.previousIndex, encodedArrayItemIteratorNewOrEmpty, fieldAnnotationIterator, iIntValue)
                            fieldReference = this.previousField
                            immutableFieldReferenceOf = ImmutableFieldReference.of(dexBackedField)
                            this.previousField = immutableFieldReferenceOf
                            this.previousIndex = dexBackedField.fieldIndex
                            if (!z || fieldReference == null) {
                                break
                            }
                        } while (fieldReference.equals(immutableFieldReferenceOf));
                        return dexBackedField
                    }
                }
            }
        }
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    fun getSuperclass() {
        return this.dexFile.getTypeSection().getOptional(this.dexFile.getBuffer().readOptionalUint(this.classDefOffset + 8))
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readSmallUint(this.classDefOffset + 0))
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends DexBackedMethod> getVirtualMethods() {
        return getVirtualMethods(true)
    }

    public Iterable<? extends DexBackedMethod> getVirtualMethods(Boolean z) {
        if (this.virtualMethodCount <= 0) {
            return ImmutableSet.of()
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getVirtualMethodsOffset())
        AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory()
        Int offset = dexReader.getOffset()
        HiddenApiRestrictionsReader hiddenApiRestrictionsReader = this.hiddenApiRestrictionsReader
        return AnonymousClass5(annotationsDirectory, offset, hiddenApiRestrictionsReader == null ? null : hiddenApiRestrictionsReader.getRestrictionsForVirtualMethods(), z)
    }

    public final Int getVirtualMethodsOffset() {
        Int i = this.virtualMethodsOffset
        if (i > 0) {
            return i
        }
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(getDirectMethodsOffset())
        DexBackedMethod.skipMethods(dexReader, this.directMethodCount)
        Int offset = dexReader.getOffset()
        this.virtualMethodsOffset = offset
        return offset
    }
}

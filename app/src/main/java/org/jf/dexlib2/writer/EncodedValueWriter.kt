package org.jf.dexlib2.writer

import com.google.common.collect.Ordering
import java.io.IOException
import java.util.Collection
import java.util.Iterator
import org.jf.dexlib2.base.BaseAnnotationElement
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodReference

abstract class EncodedValueWriter<StringKey, TypeKey, FieldRefKey extends FieldReference, MethodRefKey extends MethodReference, AnnotationElement extends AnnotationElement, ProtoRefKey, MethodHandleKey extends MethodHandleReference, EncodedValue> {
    public final AnnotationSection<StringKey, TypeKey, ?, AnnotationElement, EncodedValue> annotationSection
    public final FieldSection<?, ?, FieldRefKey, ?> fieldSection
    public final MethodHandleSection<MethodHandleKey, ?, ?> methodHandleSection
    public final MethodSection<?, ?, ?, MethodRefKey, ?> methodSection
    public final ProtoSection<?, ?, ProtoRefKey, ?> protoSection
    public final StringSection<StringKey, ?> stringSection
    public final TypeSection<?, TypeKey, ?> typeSection
    public final DexDataWriter writer

    constructor(DexDataWriter dexDataWriter, StringSection<StringKey, ?> stringSection, TypeSection<?, TypeKey, ?> typeSection, FieldSection<?, ?, FieldRefKey, ?> fieldSection, MethodSection<?, ?, ?, MethodRefKey, ?> methodSection, ProtoSection<?, ?, ProtoRefKey, ?> protoSection, MethodHandleSection<MethodHandleKey, ?, ?> methodHandleSection, AnnotationSection<StringKey, TypeKey, ?, AnnotationElement, EncodedValue> annotationSection) {
        this.writer = dexDataWriter
        this.stringSection = stringSection
        this.typeSection = typeSection
        this.fieldSection = fieldSection
        this.methodSection = methodSection
        this.protoSection = protoSection
        this.methodHandleSection = methodHandleSection
        this.annotationSection = annotationSection
    }

    /* JADX WARN: Multi-variable type inference failed */
    fun writeAnnotation(TypeKey typekey, Collection<? extends AnnotationElement> collection) throws IOException {
        this.writer.writeEncodedValueHeader(29, 0)
        this.writer.writeUleb128(this.typeSection.getItemIndex((TypeSection<?, TypeKey, ?>) typekey))
        this.writer.writeUleb128(collection.size())
        for (AnnotationElement annotationElement : Ordering.from(BaseAnnotationElement.BY_NAME).immutableSortedCopy(collection)) {
            this.writer.writeUleb128(this.stringSection.getItemIndex((StringSection<StringKey, ?>) this.annotationSection.getElementName(annotationElement)))
            writeEncodedValue(this.annotationSection.getElementValue(annotationElement))
        }
    }

    fun writeArray(Collection<? extends EncodedValue> collection) throws IOException {
        this.writer.writeEncodedValueHeader(28, 0)
        this.writer.writeUleb128(collection.size())
        Iterator<? extends EncodedValue> it = collection.iterator()
        while (it.hasNext()) {
            writeEncodedValue(it.next())
        }
    }

    fun writeBoolean(Boolean z) throws IOException {
        this.writer.writeEncodedValueHeader(31, z ? 1 : 0)
    }

    fun writeByte(Byte b2) throws IOException {
        this.writer.writeEncodedInt(0, b2)
    }

    fun writeChar(Char c) throws IOException {
        this.writer.writeEncodedUint(3, c)
    }

    fun writeDouble(Double d) throws IOException {
        this.writer.writeEncodedDouble(17, d)
    }

    public abstract Unit writeEncodedValue(EncodedValue encodedvalue) throws IOException

    fun writeEnum(FieldRefKey fieldrefkey) throws IOException {
        this.writer.writeEncodedUint(27, this.fieldSection.getItemIndex(fieldrefkey))
    }

    fun writeField(FieldRefKey fieldrefkey) throws IOException {
        this.writer.writeEncodedUint(25, this.fieldSection.getItemIndex(fieldrefkey))
    }

    fun writeFloat(Float f) throws IOException {
        this.writer.writeEncodedFloat(16, f)
    }

    fun writeInt(Int i) throws IOException {
        this.writer.writeEncodedInt(4, i)
    }

    fun writeLong(Long j) throws IOException {
        this.writer.writeEncodedLong(6, j)
    }

    fun writeMethod(MethodRefKey methodrefkey) throws IOException {
        this.writer.writeEncodedUint(26, this.methodSection.getItemIndex(methodrefkey))
    }

    fun writeMethodHandle(MethodHandleKey methodhandlekey) throws IOException {
        this.writer.writeEncodedUint(22, this.methodHandleSection.getItemIndex(methodhandlekey))
    }

    fun writeMethodType(ProtoRefKey protorefkey) throws IOException {
        this.writer.writeEncodedUint(21, this.protoSection.getItemIndex(protorefkey))
    }

    fun writeNull() throws IOException {
        this.writer.write(30)
    }

    fun writeShort(Int i) throws IOException {
        this.writer.writeEncodedInt(2, i)
    }

    fun writeString(StringKey stringkey) throws IOException {
        this.writer.writeEncodedUint(23, this.stringSection.getItemIndex((StringSection<StringKey, ?>) stringkey))
    }

    fun writeType(TypeKey typekey) throws IOException {
        this.writer.writeEncodedUint(24, this.typeSection.getItemIndex((TypeSection<?, TypeKey, ?>) typekey))
    }
}

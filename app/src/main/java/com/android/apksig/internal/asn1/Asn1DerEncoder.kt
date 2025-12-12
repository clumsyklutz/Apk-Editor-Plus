package com.android.apksig.internal.asn1

import com.android.apksig.internal.asn1.ber.BerEncoding
import com.android.apksig.internal.util.ClassCompat
import jadx.core.deobf.Deobfuscator
import java.io.ByteArrayOutputStream
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.Iterator
import java.util.List

class Asn1DerEncoder {
    public static val ASN1_DER_NULL = Asn1OpaqueObject(new Array<Byte>{5, 0})

    /* renamed from: com.android.apksig.internal.asn1.Asn1DerEncoder$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$asn1$Asn1Tagging
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$asn1$Asn1Type

        static {
            Array<Int> iArr = new Int[Asn1Tagging.values().length]
            $SwitchMap$com$android$apksig$internal$asn1$Asn1Tagging = iArr
            try {
                iArr[Asn1Tagging.NORMAL.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Tagging[Asn1Tagging.EXPLICIT.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Tagging[Asn1Tagging.IMPLICIT.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            Array<Int> iArr2 = new Int[Asn1Type.values().length]
            $SwitchMap$com$android$apksig$internal$asn1$Asn1Type = iArr2
            try {
                iArr2[Asn1Type.CHOICE.ordinal()] = 1
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SEQUENCE.ordinal()] = 2
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.UNENCODED_CONTAINER.ordinal()] = 3
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.OCTET_STRING.ordinal()] = 4
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.BIT_STRING.ordinal()] = 5
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.INTEGER.ordinal()] = 6
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.BOOLEAN.ordinal()] = 7
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.UTC_TIME.ordinal()] = 8
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.GENERALIZED_TIME.ordinal()] = 9
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.OBJECT_IDENTIFIER.ordinal()] = 10
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SET_OF.ordinal()] = 11
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SEQUENCE_OF.ordinal()] = 12
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    public static final class AnnotatedField {
        public final Asn1Field mAnnotation
        public final Asn1Type mDataType
        public final Int mDerTagClass
        public final Int mDerTagNumber
        public final Asn1Type mElementDataType
        public final Field mField
        public final Object mObject
        public final Boolean mOptional
        public final Asn1TagClass mTagClass
        public final Asn1Tagging mTagging

        constructor(Object obj, Field field, Asn1Field asn1Field) throws Asn1EncodingException {
            this.mObject = obj
            this.mField = field
            this.mAnnotation = asn1Field
            Asn1Type asn1TypeType = asn1Field.type()
            this.mDataType = asn1TypeType
            this.mElementDataType = asn1Field.elementType()
            Asn1TagClass asn1TagClassCls = asn1Field.cls()
            asn1TagClassCls = asn1TagClassCls == Asn1TagClass.AUTOMATIC ? asn1Field.tagNumber() != -1 ? Asn1TagClass.CONTEXT_SPECIFIC : Asn1TagClass.UNIVERSAL : asn1TagClassCls
            this.mTagClass = asn1TagClassCls
            this.mDerTagClass = BerEncoding.getTagClass(asn1TagClassCls)
            this.mDerTagNumber = asn1Field.tagNumber() != -1 ? asn1Field.tagNumber() : (asn1TypeType == Asn1Type.CHOICE || asn1TypeType == Asn1Type.ANY) ? -1 : BerEncoding.getTagNumber(asn1TypeType)
            Asn1Tagging asn1TaggingTagging = asn1Field.tagging()
            this.mTagging = asn1TaggingTagging
            if ((asn1TaggingTagging != Asn1Tagging.EXPLICIT && asn1TaggingTagging != Asn1Tagging.IMPLICIT) || asn1Field.tagNumber() != -1) {
                this.mOptional = asn1Field.optional()
                return
            }
            throw Asn1EncodingException("Tag number must be specified when tagging mode is " + asn1TaggingTagging)
        }

        fun getAnnotation() {
            return this.mAnnotation
        }

        fun getField() {
            return this.mField
        }

        public Array<Byte> toDer() throws Asn1EncodingException {
            Object memberFieldValue = Asn1DerEncoder.getMemberFieldValue(this.mObject, this.mField)
            if (memberFieldValue == null) {
                if (this.mOptional) {
                    return null
                }
                throw Asn1EncodingException("Required field not set")
            }
            Array<Byte> der = JavaToDerConverter.toDer(memberFieldValue, this.mDataType, this.mElementDataType)
            Int i = AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Tagging[this.mTagging.ordinal()]
            if (i == 1) {
                return der
            }
            if (i == 2) {
                return Asn1DerEncoder.createTag(this.mDerTagClass, true, this.mDerTagNumber, der)
            }
            if (i != 3) {
                throw RuntimeException("Unknown tagging mode: " + this.mTagging)
            }
            if (BerEncoding.getTagNumber(der[0]) == 31) {
                throw Asn1EncodingException("High-tag-number form not supported")
            }
            Int i2 = this.mDerTagNumber
            if (i2 < 31) {
                der[0] = BerEncoding.setTagNumber(der[0], i2)
                der[0] = BerEncoding.setTagClass(der[0], this.mDerTagClass)
                return der
            }
            throw Asn1EncodingException("Unsupported high tag number: " + this.mDerTagNumber)
        }
    }

    public static class ByteArrayLexicographicComparator implements Comparator<Array<Byte>> {
        public static val INSTANCE = ByteArrayLexicographicComparator()

        @Override // java.util.Comparator
        fun compare(Array<Byte> bArr, Array<Byte> bArr2) {
            Int iMin = Math.min(bArr.length, bArr2.length)
            for (Int i = 0; i < iMin; i++) {
                Int i2 = (bArr[i] & 255) - (bArr2[i] & 255)
                if (i2 != 0) {
                    return i2
                }
            }
            return bArr.length - bArr2.length
        }
    }

    public static final class JavaToDerConverter {
        public static Array<Byte> toDer(Object obj, Asn1Type asn1Type, Asn1Type asn1Type2) throws Asn1EncodingException {
            Class<?> cls = obj.getClass()
            if (Asn1OpaqueObject.class.equals(cls)) {
                ByteBuffer encoded = ((Asn1OpaqueObject) obj).getEncoded()
                Array<Byte> bArr = new Byte[encoded.remaining()]
                encoded.get(bArr)
                return bArr
            }
            if (asn1Type == null || asn1Type == Asn1Type.ANY) {
                return Asn1DerEncoder.encode(obj)
            }
            switch (AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[asn1Type.ordinal()]) {
                case 1:
                    Asn1Class asn1Class = (Asn1Class) ClassCompat.getDeclaredAnnotation(cls, Asn1Class.class)
                    if (asn1Class != null && asn1Class.type() == Asn1Type.CHOICE) {
                        return Asn1DerEncoder.toChoice(obj)
                    }
                    break
                case 2:
                    Asn1Class asn1Class2 = (Asn1Class) ClassCompat.getDeclaredAnnotation(cls, Asn1Class.class)
                    if (asn1Class2 != null && asn1Class2.type() == Asn1Type.SEQUENCE) {
                        return Asn1DerEncoder.toSequence(obj)
                    }
                    break
                case 4:
                case 5:
                    Array<Byte> bArr2 = null
                    if (obj is ByteBuffer) {
                        ByteBuffer byteBuffer = (ByteBuffer) obj
                        bArr2 = new Byte[byteBuffer.remaining()]
                        byteBuffer.slice().get(bArr2)
                    } else if (obj is Array<Byte>) {
                        bArr2 = (Array<Byte>) obj
                    }
                    if (bArr2 != null) {
                        return Asn1DerEncoder.createTag(0, false, BerEncoding.getTagNumber(asn1Type), bArr2)
                    }
                    break
                case 6:
                    if (obj is Integer) {
                        return Asn1DerEncoder.toInteger(((Integer) obj).intValue())
                    }
                    if (obj is Long) {
                        return Asn1DerEncoder.toInteger(((Long) obj).longValue())
                    }
                    if (obj is BigInteger) {
                        return Asn1DerEncoder.toInteger((BigInteger) obj)
                    }
                    break
                case 7:
                    if (obj is Boolean) {
                        return Asn1DerEncoder.toBoolean(((Boolean) obj).booleanValue())
                    }
                    break
                case 8:
                case 9:
                    if (obj is String) {
                        return Asn1DerEncoder.createTag(0, false, BerEncoding.getTagNumber(asn1Type), ((String) obj).getBytes())
                    }
                    break
                case 10:
                    if (obj is String) {
                        return Asn1DerEncoder.toOid((String) obj)
                    }
                    break
                case 11:
                    return Asn1DerEncoder.toSetOf((Collection) obj, asn1Type2)
                case 12:
                    return Asn1DerEncoder.toSequenceOf((Collection) obj, asn1Type2)
            }
            throw Asn1EncodingException("Unsupported conversion: " + cls.getName() + " to ASN.1 " + asn1Type)
        }
    }

    public static Array<Byte> createTag(Int i, Boolean z, Int i2, Array<Byte>... bArr) {
        Array<Byte> bArr2
        if (i2 >= 31) {
            throw IllegalArgumentException("High tag numbers not supported: " + i2)
        }
        Int i3 = 6
        Byte b2 = (Byte) ((i << 6) | (z ? 32 : 0) | i2)
        Int length = 0
        for (Array<Byte> bArr3 : bArr) {
            length += bArr3.length
        }
        Int length2 = 2
        if (length < 128) {
            bArr2 = new Byte[length + 2]
            bArr2[0] = b2
            bArr2[1] = (Byte) length
        } else {
            if (length <= 255) {
                bArr2 = new Byte[length + 3]
                bArr2[1] = -127
                bArr2[2] = (Byte) length
                i3 = 3
            } else if (length <= 65535) {
                bArr2 = new Byte[length + 4]
                bArr2[1] = -126
                bArr2[2] = (Byte) (length >> 8)
                bArr2[3] = (Byte) (length & 255)
                i3 = 4
            } else if (length <= 16777215) {
                Array<Byte> bArr4 = new Byte[length + 5]
                bArr4[1] = -125
                bArr4[2] = (Byte) (length >> 16)
                bArr4[3] = (Byte) ((length >> 8) & 255)
                bArr4[4] = (Byte) (length & 255)
                bArr2 = bArr4
                i3 = 5
            } else {
                Array<Byte> bArr5 = new Byte[length + 6]
                bArr5[1] = -124
                bArr5[2] = (Byte) (length >> 24)
                bArr5[3] = (Byte) ((length >> 16) & 255)
                bArr5[4] = (Byte) ((length >> 8) & 255)
                bArr5[5] = (Byte) (length & 255)
                bArr2 = bArr5
            }
            bArr2[0] = b2
            length2 = i3
        }
        for (Array<Byte> bArr6 : bArr) {
            System.arraycopy(bArr6, 0, bArr2, length2, bArr6.length)
            length2 += bArr6.length
        }
        return bArr2
    }

    public static Array<Byte> encode(Object obj) throws Asn1EncodingException {
        Class<?> cls = obj.getClass()
        Asn1Class asn1Class = (Asn1Class) ClassCompat.getDeclaredAnnotation(cls, Asn1Class.class)
        if (asn1Class == null) {
            throw Asn1EncodingException(cls.getName() + " not annotated with " + Asn1Class.class.getName())
        }
        Asn1Type asn1TypeType = asn1Class.type()
        Int i = AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[asn1TypeType.ordinal()]
        if (i == 1) {
            return toChoice(obj)
        }
        if (i == 2) {
            return toSequence(obj)
        }
        if (i == 3) {
            return toSequence(obj, true)
        }
        throw Asn1EncodingException("Unsupported container type: " + asn1TypeType)
    }

    public static List<AnnotatedField> getAnnotatedFields(Object obj) throws Asn1EncodingException {
        Class<?> cls = obj.getClass()
        Array<Field> declaredFields = cls.getDeclaredFields()
        ArrayList arrayList = ArrayList(declaredFields.length)
        for (Field field : declaredFields) {
            Asn1Field asn1Field = (Asn1Field) field.getAnnotation(Asn1Field.class)
            if (asn1Field != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw Asn1EncodingException(Asn1Field.class.getName() + " used on a static field: " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName())
                }
                try {
                    arrayList.add(AnnotatedField(obj, field, asn1Field))
                } catch (Asn1EncodingException e) {
                    throw Asn1EncodingException("Invalid ASN.1 annotation on " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName(), e)
                }
            }
        }
        return arrayList
    }

    fun getMemberFieldValue(Object obj, Field field) throws Asn1EncodingException {
        try {
            return field.get(obj)
        } catch (IllegalAccessException e) {
            throw Asn1EncodingException("Failed to read " + obj.getClass().getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName(), e)
        }
    }

    public static Array<Byte> toBoolean(Boolean z) {
        Array<Byte> bArr = new Byte[1]
        if (z) {
            bArr[0] = 1
        } else {
            bArr[0] = 0
        }
        return createTag(0, false, 1, bArr)
    }

    public static Array<Byte> toChoice(Object obj) throws Asn1EncodingException {
        Class<?> cls = obj.getClass()
        List<AnnotatedField> annotatedFields = getAnnotatedFields(obj)
        if (annotatedFields.isEmpty()) {
            throw Asn1EncodingException("No fields annotated with " + Asn1Field.class.getName() + " in CHOICE class " + cls.getName())
        }
        AnnotatedField annotatedField = null
        for (AnnotatedField annotatedField2 : annotatedFields) {
            if (getMemberFieldValue(obj, annotatedField2.getField()) != null) {
                if (annotatedField != null) {
                    throw Asn1EncodingException("Multiple non-null fields in CHOICE class " + cls.getName() + ": " + annotatedField.getField().getName() + ", " + annotatedField2.getField().getName())
                }
                annotatedField = annotatedField2
            }
        }
        if (annotatedField != null) {
            return annotatedField.toDer()
        }
        throw Asn1EncodingException("No non-null fields in CHOICE class " + cls.getName())
    }

    public static Array<Byte> toInteger(Int i) {
        return toInteger(i)
    }

    public static Array<Byte> toInteger(Long j) {
        return toInteger(BigInteger.valueOf(j))
    }

    public static Array<Byte> toInteger(BigInteger bigInteger) {
        return createTag(0, false, 2, bigInteger.toByteArray())
    }

    public static Array<Byte> toOid(String str) throws Asn1EncodingException, NumberFormatException {
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        Array<String> strArrSplit = str.split("\\.")
        if (strArrSplit.length < 2) {
            throw Asn1EncodingException("OBJECT IDENTIFIER must contain at least two nodes: " + str)
        }
        try {
            Int i = Integer.parseInt(strArrSplit[0])
            if (i > 6 || i < 0) {
                throw Asn1EncodingException("Invalid value for node #1: " + i)
            }
            try {
                Int i2 = Integer.parseInt(strArrSplit[1])
                if (i2 >= 40 || i2 < 0) {
                    throw Asn1EncodingException("Invalid value for node #2: " + i2)
                }
                Int i3 = (i * 40) + i2
                if (i3 > 255) {
                    throw Asn1EncodingException("First two nodes out of range: " + i + Deobfuscator.CLASS_NAME_SEPARATOR + i2)
                }
                byteArrayOutputStream.write(i3)
                for (Int i4 = 2; i4 < strArrSplit.length; i4++) {
                    String str2 = strArrSplit[i4]
                    try {
                        Int i5 = Integer.parseInt(str2)
                        if (i5 < 0) {
                            throw Asn1EncodingException("Invalid value for node #" + (i4 + 1) + ": " + i5)
                        }
                        if (i5 <= 127) {
                            byteArrayOutputStream.write(i5)
                        } else if (i5 < 16384) {
                            byteArrayOutputStream.write((i5 >> 7) | 128)
                            byteArrayOutputStream.write(i5 & 127)
                        } else {
                            if (i5 >= 2097152) {
                                throw Asn1EncodingException("Node #" + (i4 + 1) + " too large: " + i5)
                            }
                            byteArrayOutputStream.write((i5 >> 14) | 128)
                            byteArrayOutputStream.write((127 & (i5 >> 7)) | 128)
                            byteArrayOutputStream.write(i5 & 127)
                        }
                    } catch (NumberFormatException unused) {
                        throw Asn1EncodingException("Node #" + (i4 + 1) + " not numeric: " + str2)
                    }
                }
                return createTag(0, false, 6, byteArrayOutputStream.toByteArray())
            } catch (NumberFormatException unused2) {
                throw Asn1EncodingException("Node #2 not numeric: " + strArrSplit[1])
            }
        } catch (NumberFormatException unused3) {
            throw Asn1EncodingException("Node #1 not numeric: " + strArrSplit[0])
        }
    }

    public static Array<Byte> toSequence(Object obj) throws Asn1EncodingException {
        return toSequence(obj, false)
    }

    public static Array<Byte> toSequence(Object obj, Boolean z) throws Asn1EncodingException {
        Class<?> cls = obj.getClass()
        List<AnnotatedField> annotatedFields = getAnnotatedFields(obj)
        Collections.sort(annotatedFields, new Comparator<AnnotatedField>() { // from class: com.android.apksig.internal.asn1.Asn1DerEncoder.1
            @Override // java.util.Comparator
            fun compare(AnnotatedField annotatedField, AnnotatedField annotatedField2) {
                return annotatedField.getAnnotation().index() - annotatedField2.getAnnotation().index()
            }
        })
        if (annotatedFields.size() > 1) {
            AnnotatedField annotatedField = null
            for (AnnotatedField annotatedField2 : annotatedFields) {
                if (annotatedField != null && annotatedField.getAnnotation().index() == annotatedField2.getAnnotation().index()) {
                    throw Asn1EncodingException("Fields have the same index: " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + annotatedField.getField().getName() + " and ." + annotatedField2.getField().getName())
                }
                annotatedField = annotatedField2
            }
        }
        ArrayList<Array<Byte>> arrayList = ArrayList(annotatedFields.size())
        Int length = 0
        for (AnnotatedField annotatedField3 : annotatedFields) {
            try {
                Array<Byte> der = annotatedField3.toDer()
                if (der != null) {
                    arrayList.add(der)
                    length += der.length
                }
            } catch (Asn1EncodingException e) {
                throw Asn1EncodingException("Failed to encode " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + annotatedField3.getField().getName(), e)
            }
        }
        if (!z) {
            return createTag(0, true, 16, (Array<Byte>[]) arrayList.toArray(new Byte[0][]))
        }
        Array<Byte> bArr = new Byte[length]
        Int length2 = 0
        for (Array<Byte> bArr2 : arrayList) {
            System.arraycopy(bArr2, 0, bArr, length2, bArr2.length)
            length2 += bArr2.length
        }
        return bArr
    }

    public static Array<Byte> toSequenceOf(Collection<?> collection, Asn1Type asn1Type) throws Asn1EncodingException {
        return toSequenceOrSetOf(collection, asn1Type, false)
    }

    public static Array<Byte> toSequenceOrSetOf(Collection<?> collection, Asn1Type asn1Type, Boolean z) throws Asn1EncodingException {
        Int i
        ArrayList arrayList = ArrayList(collection.size())
        Iterator<?> it = collection.iterator()
        while (it.hasNext()) {
            arrayList.add(JavaToDerConverter.toDer(it.next(), asn1Type, null))
        }
        if (z) {
            if (arrayList.size() > 1) {
                Collections.sort(arrayList, ByteArrayLexicographicComparator.INSTANCE)
            }
            i = 17
        } else {
            i = 16
        }
        return createTag(0, true, i, (Array<Byte>[]) arrayList.toArray(new Byte[0][]))
    }

    public static Array<Byte> toSetOf(Collection<?> collection, Asn1Type asn1Type) throws Asn1EncodingException {
        return toSequenceOrSetOf(collection, asn1Type, true)
    }
}

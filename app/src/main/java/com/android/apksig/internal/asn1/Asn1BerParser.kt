package com.android.apksig.internal.asn1

import android.os.Build
import com.android.apksig.internal.asn1.ber.BerDataValue
import com.android.apksig.internal.asn1.ber.BerDataValueFormatException
import com.android.apksig.internal.asn1.ber.BerDataValueReader
import com.android.apksig.internal.asn1.ber.BerEncoding
import com.android.apksig.internal.asn1.ber.ByteBufferBerDataValueReader
import com.android.apksig.internal.util.ByteBufferUtils
import com.android.apksig.internal.util.ClassCompat
import jadx.core.deobf.Deobfuscator
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.Iterator
import java.util.List

class Asn1BerParser {

    /* renamed from: com.android.apksig.internal.asn1.Asn1BerParser$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ Array<Int> $SwitchMap$com$android$apksig$internal$asn1$Asn1Type

        static {
            Array<Int> iArr = new Int[Asn1Type.values().length]
            $SwitchMap$com$android$apksig$internal$asn1$Asn1Type = iArr
            try {
                iArr[Asn1Type.CHOICE.ordinal()] = 1
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SEQUENCE.ordinal()] = 2
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.UNENCODED_CONTAINER.ordinal()] = 3
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SET_OF.ordinal()] = 4
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.SEQUENCE_OF.ordinal()] = 5
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.INTEGER.ordinal()] = 6
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.OBJECT_IDENTIFIER.ordinal()] = 7
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.UTC_TIME.ordinal()] = 8
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.GENERALIZED_TIME.ordinal()] = 9
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$asn1$Asn1Type[Asn1Type.BOOLEAN.ordinal()] = 10
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    public static final class AnnotatedField {
        public final Asn1Field mAnnotation
        public final Int mBerTagClass
        public final Int mBerTagNumber
        public final Asn1Type mDataType
        public final Field mField
        public final Boolean mOptional
        public final Asn1TagClass mTagClass
        public final Asn1Tagging mTagging

        constructor(Field field, Asn1Field asn1Field) throws Asn1DecodingException {
            this.mField = field
            this.mAnnotation = asn1Field
            Asn1Type asn1TypeType = asn1Field.type()
            this.mDataType = asn1TypeType
            Asn1TagClass asn1TagClassCls = asn1Field.cls()
            asn1TagClassCls = asn1TagClassCls == Asn1TagClass.AUTOMATIC ? asn1Field.tagNumber() != -1 ? Asn1TagClass.CONTEXT_SPECIFIC : Asn1TagClass.UNIVERSAL : asn1TagClassCls
            this.mTagClass = asn1TagClassCls
            this.mBerTagClass = BerEncoding.getTagClass(asn1TagClassCls)
            this.mBerTagNumber = asn1Field.tagNumber() != -1 ? asn1Field.tagNumber() : (asn1TypeType == Asn1Type.CHOICE || asn1TypeType == Asn1Type.ANY) ? -1 : BerEncoding.getTagNumber(asn1TypeType)
            Asn1Tagging asn1TaggingTagging = asn1Field.tagging()
            this.mTagging = asn1TaggingTagging
            if ((asn1TaggingTagging != Asn1Tagging.EXPLICIT && asn1TaggingTagging != Asn1Tagging.IMPLICIT) || asn1Field.tagNumber() != -1) {
                this.mOptional = asn1Field.optional()
                return
            }
            throw Asn1DecodingException("Tag number must be specified when tagging mode is " + asn1TaggingTagging)
        }

        fun getAnnotation() {
            return this.mAnnotation
        }

        fun getBerTagClass() {
            return this.mBerTagClass
        }

        fun getBerTagNumber() {
            return this.mBerTagNumber
        }

        fun getField() {
            return this.mField
        }

        fun isOptional() {
            return this.mOptional
        }

        fun setValueFrom(BerDataValue berDataValue, Object obj) throws IllegalAccessException, IllegalArgumentException, Asn1DecodingException {
            Int tagClass = berDataValue.getTagClass()
            if (this.mBerTagNumber != -1) {
                Int tagNumber = berDataValue.getTagNumber()
                if (tagClass != this.mBerTagClass || tagNumber != this.mBerTagNumber) {
                    throw Asn1UnexpectedTagException("Tag mismatch. Expected: " + BerEncoding.tagClassAndNumberToString(this.mBerTagClass, this.mBerTagNumber) + ", but found " + BerEncoding.tagClassAndNumberToString(tagClass, tagNumber))
                }
            } else if (tagClass != this.mBerTagClass) {
                throw Asn1UnexpectedTagException("Tag mismatch. Expected class: " + BerEncoding.tagClassToString(this.mBerTagClass) + ", but found " + BerEncoding.tagClassToString(tagClass))
            }
            if (this.mTagging == Asn1Tagging.EXPLICIT) {
                try {
                    berDataValue = berDataValue.contentsReader().readDataValue()
                } catch (BerDataValueFormatException e) {
                    throw Asn1DecodingException("Failed to read contents of EXPLICIT data value", e)
                }
            }
            BerToJavaConverter.setFieldValue(obj, this.mField, this.mDataType, berDataValue)
        }
    }

    public static class Asn1UnexpectedTagException extends Asn1DecodingException {
        constructor(String str) {
            super(str)
        }
    }

    public static final class BerToJavaConverter {
        public static final Array<Byte> EMPTY_BYTE_ARRAY = new Byte[0]

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v22, types: [T, Array<Byte>] */
        public static <T> T convert(Asn1Type asn1Type, BerDataValue berDataValue, Class<T> cls) throws Asn1DecodingException {
            if (ByteBuffer.class.equals(cls)) {
                return (T) berDataValue.getEncodedContents()
            }
            if (Array<Byte>.class.equals(cls)) {
                ByteBuffer encodedContents = berDataValue.getEncodedContents()
                if (!encodedContents.hasRemaining()) {
                    return (T) EMPTY_BYTE_ARRAY
                }
                Array<Byte> r6 = new Byte[encodedContents.remaining()]
                encodedContents.get(r6)
                return (T) r6
            }
            if (Asn1OpaqueObject.class.equals(cls)) {
                return (T) Asn1OpaqueObject(berDataValue.getEncoded())
            }
            ByteBuffer encodedContents2 = berDataValue.getEncodedContents()
            Int i = AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[asn1Type.ordinal()]
            if (i == 1) {
                Asn1Class asn1Class = (Asn1Class) ClassCompat.getDeclaredAnnotation(cls, Asn1Class.class)
                if (asn1Class != null && asn1Class.type() == Asn1Type.CHOICE) {
                    return (T) Asn1BerParser.parseChoice(berDataValue, cls)
                }
            } else if (i != 2) {
                switch (i) {
                    case 6:
                        if (Integer.TYPE.equals(cls) || Integer.class.equals(cls)) {
                            return (T) Integer.valueOf(Asn1BerParser.integerToInt(encodedContents2))
                        }
                        if (Long.TYPE.equals(cls) || Long.class.equals(cls)) {
                            return (T) Long.valueOf(Asn1BerParser.integerToLong(encodedContents2))
                        }
                        if (BigInteger.class.equals(cls)) {
                            return (T) Asn1BerParser.integerToBigInteger(encodedContents2)
                        }
                        break
                    case 7:
                        if (String.class.equals(cls)) {
                            return (T) Asn1BerParser.oidToString(encodedContents2)
                        }
                        break
                    case 8:
                    case 9:
                        if (String.class.equals(cls)) {
                            return (T) String(ByteBufferUtils.toByteArray(encodedContents2))
                        }
                        break
                    case 10:
                        if (Boolean.TYPE.equals(cls)) {
                            if (encodedContents2.remaining() == 1) {
                                return (T) Boolean.valueOf(encodedContents2.get() != 0)
                            }
                            throw Asn1DecodingException("Incorrect encoded size of Boolean value: " + encodedContents2.remaining())
                        }
                        break
                }
            } else {
                Asn1Class asn1Class2 = (Asn1Class) ClassCompat.getDeclaredAnnotation(cls, Asn1Class.class)
                if (asn1Class2 != null && asn1Class2.type() == Asn1Type.SEQUENCE) {
                    return (T) Asn1BerParser.parseSequence(berDataValue, cls)
                }
            }
            throw Asn1DecodingException("Unsupported conversion: ASN.1 " + asn1Type + " to " + cls.getName())
        }

        fun setFieldValue(Object obj, Field field, Asn1Type asn1Type, BerDataValue berDataValue) throws IllegalAccessException, IllegalArgumentException, Asn1DecodingException {
            try {
                Int i = AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[asn1Type.ordinal()]
                if (i != 4 && i != 5) {
                    field.set(obj, convert(asn1Type, berDataValue, field.getType()))
                } else if (Asn1OpaqueObject.class.equals(field.getType())) {
                    field.set(obj, convert(asn1Type, berDataValue, field.getType()))
                } else {
                    field.set(obj, Asn1BerParser.parseSetOf(berDataValue, Asn1BerParser.getElementType(field)))
                }
            } catch (ClassNotFoundException e) {
                throw Asn1DecodingException("Failed to set value of " + obj.getClass().getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName(), e)
            } catch (IllegalAccessException e2) {
                throw Asn1DecodingException("Failed to set value of " + obj.getClass().getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName(), e2)
            }
        }
    }

    fun decodeBase128UnsignedLong(ByteBuffer byteBuffer) throws Asn1DecodingException {
        Long j = 0
        if (!byteBuffer.hasRemaining()) {
            return 0L
        }
        while (byteBuffer.hasRemaining()) {
            if (j > 72057594037927935L) {
                throw Asn1DecodingException("Base-128 number too large")
            }
            j = (j << 7) | (r0 & 127)
            if ((byteBuffer.get() & 255 & 128) == 0) {
                return j
            }
        }
        throw Asn1DecodingException("Truncated base-128 encoded input: missing terminating Byte, with highest bit not set")
    }

    public static List<AnnotatedField> getAnnotatedFields(Class<?> cls) throws Asn1DecodingException {
        Array<Field> declaredFields = cls.getDeclaredFields()
        ArrayList arrayList = ArrayList(declaredFields.length)
        for (Field field : declaredFields) {
            Asn1Field asn1Field = (Asn1Field) field.getAnnotation(Asn1Field.class)
            if (asn1Field != null) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw Asn1DecodingException(Asn1Field.class.getName() + " used on a static field: " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName())
                }
                try {
                    arrayList.add(AnnotatedField(field, asn1Field))
                } catch (Asn1DecodingException e) {
                    throw Asn1DecodingException("Invalid ASN.1 annotation on " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + field.getName(), e)
                }
            }
        }
        return arrayList
    }

    fun getContainerAsn1Type(Class<?> cls) throws Asn1DecodingException {
        Asn1Class asn1Class = (Asn1Class) ClassCompat.getDeclaredAnnotation(cls, Asn1Class.class)
        if (asn1Class == null) {
            throw Asn1DecodingException(cls.getName() + " is not annotated with " + Asn1Class.class.getName())
        }
        Int i = AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[asn1Class.type().ordinal()]
        if (i == 1 || i == 2 || i == 3) {
            return asn1Class.type()
        }
        throw Asn1DecodingException("Unsupported ASN.1 container annotation type: " + asn1Class.type())
    }

    public static Class<?> getElementType(Field field) throws ClassNotFoundException, Asn1DecodingException {
        String typeName = Build.VERSION.SDK_INT >= 28 ? field.getGenericType().getTypeName() : field.getGenericType().toString()
        Int iIndexOf = typeName.indexOf(60)
        if (iIndexOf == -1) {
            throw Asn1DecodingException("Not a container type: " + field.getGenericType())
        }
        Int i = iIndexOf + 1
        Int iIndexOf2 = typeName.indexOf(62, i)
        if (iIndexOf2 != -1) {
            return Class.forName(typeName.substring(i, iIndexOf2))
        }
        throw Asn1DecodingException("Not a container type: " + field.getGenericType())
    }

    fun integerToBigInteger(ByteBuffer byteBuffer) {
        return !byteBuffer.hasRemaining() ? BigInteger.ZERO : BigInteger(ByteBufferUtils.toByteArray(byteBuffer))
    }

    fun integerToInt(ByteBuffer byteBuffer) throws Asn1DecodingException {
        BigInteger bigIntegerIntegerToBigInteger = integerToBigInteger(byteBuffer)
        if (bigIntegerIntegerToBigInteger.compareTo(BigInteger.valueOf(-2147483648L)) < 0 || bigIntegerIntegerToBigInteger.compareTo(BigInteger.valueOf(2147483647L)) > 0) {
            throw Asn1DecodingException(String.format("INTEGER cannot be represented as Int: %1$d (0x%1$x)", bigIntegerIntegerToBigInteger))
        }
        return bigIntegerIntegerToBigInteger.intValue()
    }

    fun integerToLong(ByteBuffer byteBuffer) throws Asn1DecodingException {
        BigInteger bigIntegerIntegerToBigInteger = integerToBigInteger(byteBuffer)
        if (bigIntegerIntegerToBigInteger.compareTo(BigInteger.valueOf(Long.MIN_VALUE)) < 0 || bigIntegerIntegerToBigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            throw Asn1DecodingException(String.format("INTEGER cannot be represented as Long: %1$d (0x%1$x)", bigIntegerIntegerToBigInteger))
        }
        return bigIntegerIntegerToBigInteger.longValue()
    }

    fun oidToString(ByteBuffer byteBuffer) throws Asn1DecodingException {
        if (!byteBuffer.hasRemaining()) {
            throw Asn1DecodingException("Empty OBJECT IDENTIFIER")
        }
        Long jDecodeBase128UnsignedLong = decodeBase128UnsignedLong(byteBuffer)
        Int iMin = (Int) Math.min(jDecodeBase128UnsignedLong / 40, 2L)
        StringBuilder sb = StringBuilder()
        sb.append(Long.toString(iMin))
        sb.append('.')
        sb.append(Long.toString(jDecodeBase128UnsignedLong - (iMin * 40)))
        while (byteBuffer.hasRemaining()) {
            Long jDecodeBase128UnsignedLong2 = decodeBase128UnsignedLong(byteBuffer)
            sb.append('.')
            sb.append(Long.toString(jDecodeBase128UnsignedLong2))
        }
        return sb.toString()
    }

    public static <T> T parse(BerDataValue berDataValue, Class<T> cls) throws Asn1DecodingException {
        if (berDataValue == null) {
            throw NullPointerException("container == null")
        }
        if (cls == null) {
            throw NullPointerException("containerClass == null")
        }
        Asn1Type containerAsn1Type = getContainerAsn1Type(cls)
        Int i = AnonymousClass2.$SwitchMap$com$android$apksig$internal$asn1$Asn1Type[containerAsn1Type.ordinal()]
        if (i == 1) {
            return (T) parseChoice(berDataValue, cls)
        }
        if (i != 2) {
            if (i == 3) {
                return (T) parseSequence(berDataValue, cls, true)
            }
            throw Asn1DecodingException("Parsing container " + containerAsn1Type + " not supported")
        }
        Int tagNumber = BerEncoding.getTagNumber(containerAsn1Type)
        if (berDataValue.getTagClass() == 0 && berDataValue.getTagNumber() == tagNumber) {
            return (T) parseSequence(berDataValue, cls)
        }
        throw Asn1UnexpectedTagException("Unexpected data value read as " + cls.getName() + ". Expected " + BerEncoding.tagClassAndNumberToString(0, tagNumber) + ", but read: " + BerEncoding.tagClassAndNumberToString(berDataValue.getTagClass(), berDataValue.getTagNumber()))
    }

    public static <T> T parse(ByteBuffer byteBuffer, Class<T> cls) throws Asn1DecodingException {
        try {
            BerDataValue dataValue = ByteBufferBerDataValueReader(byteBuffer).readDataValue()
            if (dataValue != null) {
                return (T) parse(dataValue, cls)
            }
            throw Asn1DecodingException("Empty input")
        } catch (BerDataValueFormatException e) {
            throw Asn1DecodingException("Failed to decode top-level data value", e)
        }
    }

    public static <T> T parseChoice(BerDataValue berDataValue, Class<T> cls) throws IllegalAccessException, InstantiationException, IllegalArgumentException, Asn1DecodingException, InvocationTargetException {
        List<AnnotatedField> annotatedFields = getAnnotatedFields(cls)
        if (annotatedFields.isEmpty()) {
            throw Asn1DecodingException("No fields annotated with " + Asn1Field.class.getName() + " in CHOICE class " + cls.getName())
        }
        Int i = 0
        while (i < annotatedFields.size() - 1) {
            AnnotatedField annotatedField = annotatedFields.get(i)
            Int berTagNumber = annotatedField.getBerTagNumber()
            Int berTagClass = annotatedField.getBerTagClass()
            i++
            for (Int i2 = i; i2 < annotatedFields.size(); i2++) {
                AnnotatedField annotatedField2 = annotatedFields.get(i2)
                Int berTagNumber2 = annotatedField2.getBerTagNumber()
                Int berTagClass2 = annotatedField2.getBerTagClass()
                if (berTagNumber == berTagNumber2 && berTagClass == berTagClass2) {
                    throw Asn1DecodingException("CHOICE fields are indistinguishable because they have the same tag class and number: " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + annotatedField.getField().getName() + " and ." + annotatedField2.getField().getName())
                }
            }
        }
        try {
            T tNewInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0])
            Iterator<AnnotatedField> it = annotatedFields.iterator()
            while (it.hasNext()) {
                try {
                    it.next().setValueFrom(berDataValue, tNewInstance)
                    return tNewInstance
                } catch (Asn1UnexpectedTagException unused) {
                }
            }
            throw Asn1DecodingException("No options of CHOICE " + cls.getName() + " matched")
        } catch (Exception e) {
            throw Asn1DecodingException("Failed to instantiate " + cls.getName(), e)
        }
    }

    public static <T> List<T> parseImplicitSetOf(ByteBuffer byteBuffer, Class<T> cls) throws Asn1DecodingException {
        try {
            BerDataValue dataValue = ByteBufferBerDataValueReader(byteBuffer).readDataValue()
            if (dataValue != null) {
                return parseSetOf(dataValue, cls)
            }
            throw Asn1DecodingException("Empty input")
        } catch (BerDataValueFormatException e) {
            throw Asn1DecodingException("Failed to decode top-level data value", e)
        }
    }

    public static <T> T parseSequence(BerDataValue berDataValue, Class<T> cls) throws Asn1DecodingException {
        return (T) parseSequence(berDataValue, cls, false)
    }

    public static <T> T parseSequence(BerDataValue berDataValue, Class<T> cls, Boolean z) throws IllegalAccessException, InstantiationException, IllegalArgumentException, Asn1DecodingException, InvocationTargetException {
        BerDataValue dataValue
        List<AnnotatedField> annotatedFields = getAnnotatedFields(cls)
        Collections.sort(annotatedFields, new Comparator<AnnotatedField>() { // from class: com.android.apksig.internal.asn1.Asn1BerParser.1
            @Override // java.util.Comparator
            fun compare(AnnotatedField annotatedField, AnnotatedField annotatedField2) {
                return annotatedField.getAnnotation().index() - annotatedField2.getAnnotation().index()
            }
        })
        if (annotatedFields.size() > 1) {
            AnnotatedField annotatedField = null
            for (AnnotatedField annotatedField2 : annotatedFields) {
                if (annotatedField != null && annotatedField.getAnnotation().index() == annotatedField2.getAnnotation().index()) {
                    throw Asn1DecodingException("Fields have the same index: " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + annotatedField.getField().getName() + " and ." + annotatedField2.getField().getName())
                }
                annotatedField = annotatedField2
            }
        }
        Int i = 0
        try {
            T tNewInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0])
            BerDataValueReader berDataValueReaderContentsReader = berDataValue.contentsReader()
            while (i < annotatedFields.size()) {
                if (z && i == 0) {
                    dataValue = berDataValue
                } else {
                    try {
                        dataValue = berDataValueReaderContentsReader.readDataValue()
                    } catch (BerDataValueFormatException e) {
                        throw Asn1DecodingException("Malformed data value", e)
                    }
                }
                if (dataValue == null) {
                    break
                }
                for (Int i2 = i; i2 < annotatedFields.size(); i2++) {
                    AnnotatedField annotatedField3 = annotatedFields.get(i2)
                    try {
                        if (annotatedField3.isOptional()) {
                            try {
                                annotatedField3.setValueFrom(dataValue, tNewInstance)
                            } catch (Asn1UnexpectedTagException unused) {
                            }
                        } else {
                            annotatedField3.setValueFrom(dataValue, tNewInstance)
                        }
                        i = i2 + 1
                        break
                    } catch (Asn1DecodingException e2) {
                        throw Asn1DecodingException("Failed to parse " + cls.getName() + Deobfuscator.CLASS_NAME_SEPARATOR + annotatedField3.getField().getName(), e2)
                    }
                }
            }
            return tNewInstance
        } catch (Exception e3) {
            throw Asn1DecodingException("Failed to instantiate " + cls.getName(), e3)
        }
    }

    public static <T> List<T> parseSetOf(BerDataValue berDataValue, Class<T> cls) throws Asn1DecodingException {
        ArrayList arrayList = ArrayList()
        BerDataValueReader berDataValueReaderContentsReader = berDataValue.contentsReader()
        while (true) {
            try {
                BerDataValue dataValue = berDataValueReaderContentsReader.readDataValue()
                if (dataValue == null) {
                    return arrayList
                }
                arrayList.add(ByteBuffer.class.equals(cls) ? dataValue.getEncodedContents() : Asn1OpaqueObject.class.equals(cls) ? Asn1OpaqueObject(dataValue.getEncoded()) : parse(dataValue, cls))
            } catch (BerDataValueFormatException e) {
                throw Asn1DecodingException("Malformed data value", e)
            }
        }
    }
}

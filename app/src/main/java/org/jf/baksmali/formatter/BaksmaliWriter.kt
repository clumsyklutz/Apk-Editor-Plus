package org.jf.baksmali.formatter

import androidx.appcompat.R
import java.io.IOException
import java.io.Writer
import java.util.Collection
import java.util.List
import org.jf.dexlib2.formatter.DexFormattedWriter
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.value.AnnotationEncodedValue
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.iface.value.BooleanEncodedValue
import org.jf.dexlib2.iface.value.ByteEncodedValue
import org.jf.dexlib2.iface.value.CharEncodedValue
import org.jf.dexlib2.iface.value.DoubleEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.EnumEncodedValue
import org.jf.dexlib2.iface.value.FieldEncodedValue
import org.jf.dexlib2.iface.value.FloatEncodedValue
import org.jf.dexlib2.iface.value.IntEncodedValue
import org.jf.dexlib2.iface.value.LongEncodedValue
import org.jf.dexlib2.iface.value.MethodEncodedValue
import org.jf.dexlib2.iface.value.MethodHandleEncodedValue
import org.jf.dexlib2.iface.value.MethodTypeEncodedValue
import org.jf.dexlib2.iface.value.ShortEncodedValue
import org.jf.dexlib2.iface.value.StringEncodedValue
import org.jf.dexlib2.iface.value.TypeEncodedValue
import org.jf.util.IndentingWriter

class BaksmaliWriter extends DexFormattedWriter {
    public final Array<Char> buffer
    public final String classContext

    constructor(Writer writer, String str) {
        super(writer is IndentingWriter ? writer : IndentingWriter(writer))
        this.buffer = new Char[24]
        this.classContext = str
    }

    fun deindent(Int i) {
        ((IndentingWriter) this.writer).deindent(i)
    }

    fun indent(Int i) {
        ((IndentingWriter) this.writer).indent(i)
    }

    fun indentingWriter() {
        return (IndentingWriter) this.writer
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeAnnotation(AnnotationEncodedValue annotationEncodedValue) throws IOException {
        this.writer.write(".subannotation ")
        writeType(annotationEncodedValue.getType())
        this.writer.write(10)
        writeAnnotationElements(annotationEncodedValue.getElements())
        this.writer.write(".end subannotation")
    }

    fun writeAnnotationElements(Collection<? extends AnnotationElement> collection) throws IOException {
        indent(4)
        for (AnnotationElement annotationElement : collection) {
            writeSimpleName(annotationElement.getName())
            this.writer.write(" = ")
            writeEncodedValue(annotationElement.getValue())
            this.writer.write(10)
        }
        deindent(4)
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeArray(ArrayEncodedValue arrayEncodedValue) throws IOException {
        this.writer.write(123)
        List<? extends EncodedValue> value = arrayEncodedValue.getValue()
        if (value.size() == 0) {
            this.writer.write(125)
            return
        }
        this.writer.write(10)
        indent(4)
        Boolean z = true
        for (EncodedValue encodedValue : value) {
            if (!z) {
                this.writer.write(",\n")
            }
            z = false
            writeEncodedValue(encodedValue)
        }
        deindent(4)
        this.writer.write("\n}")
    }

    fun writeBooleanEncodedValue(BooleanEncodedValue booleanEncodedValue) throws IOException {
        this.writer.write(Boolean.toString(booleanEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeCallSite(CallSiteReference callSiteReference) throws IOException {
        writeSimpleName(callSiteReference.getName())
        this.writer.write(40)
        writeQuotedString(callSiteReference.getMethodName())
        this.writer.write(", ")
        writeMethodProtoDescriptor(callSiteReference.getMethodProto())
        for (EncodedValue encodedValue : callSiteReference.getExtraArguments()) {
            this.writer.write(", ")
            writeEncodedValue(encodedValue)
        }
        this.writer.write(")@")
        if (callSiteReference.getMethodHandle().getMethodHandleType() != 4) {
            throw IllegalArgumentException("The linker method handle for a call site must be of type invoke-static")
        }
        writeMethodDescriptor((MethodReference) callSiteReference.getMethodHandle().getMemberReference())
    }

    fun writeCharEncodedValue(CharEncodedValue charEncodedValue) throws IOException {
        Char value = charEncodedValue.getValue()
        if (value >= ' ' && value < 127) {
            this.writer.write(39)
            if (value == '\'' || value == '\"' || value == '\\') {
                this.writer.write(92)
            }
            this.writer.write(value)
            this.writer.write(39)
            return
        }
        if (value <= 127) {
            if (value == '\t') {
                this.writer.write("'\\t'")
                return
            } else if (value == '\n') {
                this.writer.write("'\\n'")
                return
            } else if (value == '\r') {
                this.writer.write("'\\r'")
                return
            }
        }
        this.writer.write(39)
        this.writer.write("\\u")
        this.writer.write(Character.forDigit(value >> '\f', 16))
        this.writer.write(Character.forDigit((value >> '\b') & 15, 16))
        this.writer.write(Character.forDigit((value >> 4) & 15, 16))
        this.writer.write(Character.forDigit(value & 15, 16))
        this.writer.write(39)
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeClass(CharSequence charSequence) throws IOException {
        this.writer.write(charSequence.charAt(0))
        Int i = 1
        Int i2 = 1
        Boolean z = false
        while (true) {
            if (i >= charSequence.length()) {
                break
            }
            Char cCharAt = charSequence.charAt(i)
            if (Character.getType(cCharAt) == 12) {
                z = true
            } else if (cCharAt == '/') {
                if (i == i2) {
                    throw IllegalArgumentException(String.format("Invalid type string: %s", charSequence))
                }
                writeSimpleName(charSequence.subSequence(i2, i), z)
                this.writer.write(charSequence.charAt(i))
                i2 = i + 1
                z = false
            } else if (cCharAt == ';') {
                if (i == i2) {
                    throw IllegalArgumentException(String.format("Invalid type string: %s", charSequence))
                }
                writeSimpleName(charSequence.subSequence(i2, i), z)
                this.writer.write(charSequence.charAt(i))
            }
            i++
        }
        if (i != charSequence.length() - 1 || charSequence.charAt(i) != ';') {
            throw IllegalArgumentException(String.format("Invalid type string: %s", charSequence))
        }
    }

    fun writeDoubleEncodedValue(DoubleEncodedValue doubleEncodedValue) throws IOException {
        this.writer.write(Double.toString(doubleEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeEncodedValue(EncodedValue encodedValue) throws IOException {
        Int valueType = encodedValue.getValueType()
        if (valueType == 0) {
            writeIntegralValue(((ByteEncodedValue) encodedValue).getValue(), 't')
            return
        }
        if (valueType == 6) {
            writeIntegralValue(((LongEncodedValue) encodedValue).getValue(), 'L')
            return
        }
        if (valueType == 2) {
            writeIntegralValue(((ShortEncodedValue) encodedValue).getValue(), 's')
            return
        }
        if (valueType == 3) {
            writeCharEncodedValue((CharEncodedValue) encodedValue)
            return
        }
        if (valueType == 4) {
            writeIntegralValue(((IntEncodedValue) encodedValue).getValue(), null)
            return
        }
        if (valueType == 16) {
            writeFloatEncodedValue((FloatEncodedValue) encodedValue)
            return
        }
        if (valueType == 17) {
            writeDoubleEncodedValue((DoubleEncodedValue) encodedValue)
            return
        }
        switch (valueType) {
            case 21:
                writeMethodProtoDescriptor(((MethodTypeEncodedValue) encodedValue).getValue())
                return
            case 22:
                writeMethodHandle(((MethodHandleEncodedValue) encodedValue).getValue())
                return
            case 23:
                writeQuotedString(((StringEncodedValue) encodedValue).getValue())
                return
            case 24:
                writeType(((TypeEncodedValue) encodedValue).getValue())
                return
            case 25:
                writeFieldDescriptor(((FieldEncodedValue) encodedValue).getValue())
                return
            case 26:
                writeMethodDescriptor(((MethodEncodedValue) encodedValue).getValue())
                return
            case 27:
                writeEnum((EnumEncodedValue) encodedValue)
                return
            case 28:
                writeArray((ArrayEncodedValue) encodedValue)
                return
            case 29:
                writeAnnotation((AnnotationEncodedValue) encodedValue)
                return
            case 30:
                this.writer.write("null")
                return
            case 31:
                writeBooleanEncodedValue((BooleanEncodedValue) encodedValue)
                return
            default:
                throw IllegalArgumentException("Unknown encoded value type")
        }
    }

    fun writeEnum(EnumEncodedValue enumEncodedValue) throws IOException {
        this.writer.write(".enum ")
        writeFieldDescriptor(enumEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeFieldDescriptor(FieldReference fieldReference) throws IOException {
        if (fieldReference.getDefiningClass().equals(this.classContext)) {
            writeShortFieldDescriptor(fieldReference)
        } else {
            super.writeFieldDescriptor(fieldReference)
        }
    }

    fun writeFloatEncodedValue(FloatEncodedValue floatEncodedValue) throws IOException {
        this.writer.write(Float.toString(floatEncodedValue.getValue()))
        this.writer.write(R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle)
    }

    fun writeIntegralValue(Long j, Character ch) throws IOException {
        if (j < 0) {
            this.writer.write("-0x")
            writeUnsignedLongAsHex(-j)
        } else {
            this.writer.write("0x")
            writeUnsignedLongAsHex(j)
        }
        if (ch != null) {
            this.writer.write(ch.charValue())
        }
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeMethodDescriptor(MethodReference methodReference) throws IOException {
        if (methodReference.getDefiningClass().equals(this.classContext)) {
            writeShortMethodDescriptor(methodReference)
        } else {
            super.writeMethodDescriptor(methodReference)
        }
    }

    fun writeSignedIntAsDec(Int i) throws IOException {
        if (i < 0) {
            write(45)
        }
        Int i2 = 15
        while (true) {
            Int iAbs = Math.abs(i % 10)
            Array<Char> cArr = this.buffer
            Int i3 = i2 - 1
            cArr[i2] = (Char) (iAbs + 48)
            i /= 10
            if (i == 0) {
                Int i4 = i3 + 1
                write(cArr, i4, 16 - i4)
                return
            }
            i2 = i3
        }
    }

    fun writeSignedIntOrLongTo(Long j) throws IOException {
        if (j < 0) {
            this.writer.write("-0x")
            writeUnsignedLongAsHex(-j)
            if (j < -2147483648L) {
                this.writer.write(76)
                return
            }
            return
        }
        this.writer.write("0x")
        writeUnsignedLongAsHex(j)
        if (j > 2147483647L) {
            this.writer.write(76)
        }
    }

    fun writeSignedLongAsDec(Long j) throws IOException {
        if (j < 0) {
            write(45)
        }
        Int i = 23
        while (true) {
            Long jAbs = Math.abs(j % 10)
            Array<Char> cArr = this.buffer
            Int i2 = i - 1
            cArr[i] = (Char) (jAbs + 48)
            j /= 10
            if (j == 0) {
                Int i3 = i2 + 1
                write(cArr, i3, 24 - i3)
                return
            }
            i = i2
        }
    }

    @Override // org.jf.dexlib2.formatter.DexFormattedWriter
    fun writeSimpleName(CharSequence charSequence) throws IOException {
        Boolean z = false
        Int i = 0
        while (true) {
            if (i >= charSequence.length()) {
                break
            }
            if (Character.getType(charSequence.charAt(i)) == 12) {
                z = true
                break
            }
            i++
        }
        writeSimpleName(charSequence, z)
    }

    fun writeSimpleName(CharSequence charSequence, Boolean z) throws IOException {
        if (z) {
            this.writer.write(96)
        }
        this.writer.append(charSequence)
        if (z) {
            this.writer.write(96)
        }
    }

    fun writeUnsignedIntAsDec(Int i) throws IOException {
        if (i < 0) {
            writeSignedLongAsDec(i & 4294967295L)
        } else {
            writeSignedIntAsDec(i)
        }
    }

    fun writeUnsignedLongAsHex(Long j) throws IOException {
        Int i
        Int i2 = 23
        do {
            Int i3 = (Int) (15 & j)
            if (i3 < 10) {
                i = i2 - 1
                this.buffer[i2] = (Char) (i3 + 48)
            } else {
                i = i2 - 1
                this.buffer[i2] = (Char) ((i3 - 10) + 97)
            }
            i2 = i
            j >>>= 4
        } while (j != 0);
        Int i4 = i2 + 1
        write(this.buffer, i4, 24 - i4)
    }
}

package org.jf.dexlib2.dexbacked.value

import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableBooleanEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableNullEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue
import org.jf.dexlib2.util.Preconditions
import org.jf.util.ExceptionWithContext

abstract class DexBackedEncodedValue {
    fun readFrom(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        Int offset = dexReader.getOffset()
        try {
            Int ubyte = dexReader.readUbyte()
            Int i = ubyte & 31
            Int i2 = ubyte >>> 5
            if (i == 0) {
                Preconditions.checkValueArg(i2, 0)
                return ImmutableByteEncodedValue((Byte) dexReader.readByte())
            }
            if (i == 6) {
                Preconditions.checkValueArg(i2, 7)
                return ImmutableLongEncodedValue(dexReader.readSizedLong(i2 + 1))
            }
            if (i == 2) {
                Preconditions.checkValueArg(i2, 1)
                return ImmutableShortEncodedValue((Short) dexReader.readSizedInt(i2 + 1))
            }
            if (i == 3) {
                Preconditions.checkValueArg(i2, 1)
                return ImmutableCharEncodedValue((Char) dexReader.readSizedSmallUint(i2 + 1))
            }
            if (i == 4) {
                Preconditions.checkValueArg(i2, 3)
                return ImmutableIntEncodedValue(dexReader.readSizedInt(i2 + 1))
            }
            if (i == 16) {
                Preconditions.checkValueArg(i2, 3)
                return ImmutableFloatEncodedValue(Float.intBitsToFloat(dexReader.readSizedRightExtendedInt(i2 + 1)))
            }
            if (i == 17) {
                Preconditions.checkValueArg(i2, 7)
                return ImmutableDoubleEncodedValue(Double.longBitsToDouble(dexReader.readSizedRightExtendedLong(i2 + 1)))
            }
            switch (i) {
                case 21:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedMethodTypeEncodedValue(dexBackedDexFile, dexReader, i2)
                case 22:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedMethodHandleEncodedValue(dexBackedDexFile, dexReader, i2)
                case 23:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedStringEncodedValue(dexBackedDexFile, dexReader, i2)
                case 24:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedTypeEncodedValue(dexBackedDexFile, dexReader, i2)
                case 25:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedFieldEncodedValue(dexBackedDexFile, dexReader, i2)
                case 26:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedMethodEncodedValue(dexBackedDexFile, dexReader, i2)
                case 27:
                    Preconditions.checkValueArg(i2, 3)
                    return DexBackedEnumEncodedValue(dexBackedDexFile, dexReader, i2)
                case 28:
                    Preconditions.checkValueArg(i2, 0)
                    return DexBackedArrayEncodedValue(dexBackedDexFile, dexReader)
                case 29:
                    Preconditions.checkValueArg(i2, 0)
                    return DexBackedAnnotationEncodedValue(dexBackedDexFile, dexReader)
                case 30:
                    Preconditions.checkValueArg(i2, 0)
                    return ImmutableNullEncodedValue.INSTANCE
                case 31:
                    Preconditions.checkValueArg(i2, 1)
                    return ImmutableBooleanEncodedValue.forBoolean(i2 == 1)
                default:
                    throw ExceptionWithContext("Invalid encoded_value type: 0x%x", Integer.valueOf(i))
            }
        } catch (Exception e) {
            throw ExceptionWithContext.withContext(e, "Error while reading encoded value at offset 0x%x", Integer.valueOf(offset))
        }
    }

    fun skipFrom(DexReader dexReader) {
        Int offset = dexReader.getOffset()
        try {
            Int ubyte = dexReader.readUbyte()
            Int i = ubyte & 31
            if (i == 0) {
                dexReader.skipByte()
                return
            }
            if (i != 6 && i != 2 && i != 3 && i != 4 && i != 16 && i != 17) {
                switch (i) {
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                        break
                    case 28:
                        DexBackedArrayEncodedValue.skipFrom(dexReader)
                        return
                    case 29:
                        DexBackedAnnotationEncodedValue.skipFrom(dexReader)
                        return
                    case 30:
                    case 31:
                        return
                    default:
                        throw ExceptionWithContext("Invalid encoded_value type: 0x%x", Integer.valueOf(i))
                }
            }
            dexReader.moveRelative((ubyte >>> 5) + 1)
        } catch (Exception e) {
            throw ExceptionWithContext.withContext(e, "Error while skipping encoded value at offset 0x%x", Integer.valueOf(offset))
        }
    }
}

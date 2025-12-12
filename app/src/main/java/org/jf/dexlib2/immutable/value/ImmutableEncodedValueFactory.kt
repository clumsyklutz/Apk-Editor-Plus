package org.jf.dexlib2.immutable.value

import androidx.appcompat.R
import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableList
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
import org.jf.util.ExceptionWithContext
import org.jf.util.ImmutableConverter

class ImmutableEncodedValueFactory {
    public static final ImmutableConverter<ImmutableEncodedValue, EncodedValue> CONVERTER = new ImmutableConverter<ImmutableEncodedValue, EncodedValue>() { // from class: org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory.1
        @Override // org.jf.util.ImmutableConverter
        fun isImmutable(EncodedValue encodedValue) {
            return encodedValue is ImmutableEncodedValue
        }

        @Override // org.jf.util.ImmutableConverter
        fun makeImmutable(EncodedValue encodedValue) {
            return ImmutableEncodedValueFactory.of(encodedValue)
        }
    }

    fun defaultValueForType(String str) {
        Char cCharAt = str.charAt(0)
        if (cCharAt == 'F') {
            return ImmutableFloatEncodedValue(0.0f)
        }
        if (cCharAt != 'L') {
            if (cCharAt == 'S') {
                return ImmutableShortEncodedValue((Short) 0)
            }
            if (cCharAt == 'I') {
                return ImmutableIntEncodedValue(0)
            }
            if (cCharAt == 'J') {
                return ImmutableLongEncodedValue(0L)
            }
            if (cCharAt == 'Z') {
                return ImmutableBooleanEncodedValue.FALSE_VALUE
            }
            if (cCharAt != '[') {
                switch (cCharAt) {
                    case R.styleable.AppCompatTheme_imageButtonStyle /* 66 */:
                        return ImmutableByteEncodedValue((Byte) 0)
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /* 67 */:
                        return ImmutableCharEncodedValue((Char) 0)
                    case R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /* 68 */:
                        return ImmutableDoubleEncodedValue(0.0d)
                    default:
                        throw ExceptionWithContext("Unrecognized type: %s", str)
                }
            }
        }
        return ImmutableNullEncodedValue.INSTANCE
    }

    public static ImmutableList<ImmutableEncodedValue> immutableListOf(Iterable<? extends EncodedValue> iterable) {
        return CONVERTER.toList(iterable)
    }

    fun of(EncodedValue encodedValue) {
        Int valueType = encodedValue.getValueType()
        if (valueType == 0) {
            return ImmutableByteEncodedValue.of((ByteEncodedValue) encodedValue)
        }
        if (valueType == 6) {
            return ImmutableLongEncodedValue.of((LongEncodedValue) encodedValue)
        }
        if (valueType == 2) {
            return ImmutableShortEncodedValue.of((ShortEncodedValue) encodedValue)
        }
        if (valueType == 3) {
            return ImmutableCharEncodedValue.of((CharEncodedValue) encodedValue)
        }
        if (valueType == 4) {
            return ImmutableIntEncodedValue.of((IntEncodedValue) encodedValue)
        }
        if (valueType == 16) {
            return ImmutableFloatEncodedValue.of((FloatEncodedValue) encodedValue)
        }
        if (valueType == 17) {
            return ImmutableDoubleEncodedValue.of((DoubleEncodedValue) encodedValue)
        }
        switch (valueType) {
            case 21:
                return ImmutableMethodTypeEncodedValue.of((MethodTypeEncodedValue) encodedValue)
            case 22:
                return ImmutableMethodHandleEncodedValue.of((MethodHandleEncodedValue) encodedValue)
            case 23:
                return ImmutableStringEncodedValue.of((StringEncodedValue) encodedValue)
            case 24:
                return ImmutableTypeEncodedValue.of((TypeEncodedValue) encodedValue)
            case 25:
                return ImmutableFieldEncodedValue.of((FieldEncodedValue) encodedValue)
            case 26:
                return ImmutableMethodEncodedValue.of((MethodEncodedValue) encodedValue)
            case 27:
                return ImmutableEnumEncodedValue.of((EnumEncodedValue) encodedValue)
            case 28:
                return ImmutableArrayEncodedValue.of((ArrayEncodedValue) encodedValue)
            case 29:
                return ImmutableAnnotationEncodedValue.of((AnnotationEncodedValue) encodedValue)
            case 30:
                return ImmutableNullEncodedValue.INSTANCE
            case 31:
                return ImmutableBooleanEncodedValue.of((BooleanEncodedValue) encodedValue)
            default:
                Preconditions.checkArgument(false)
                return null
        }
    }

    fun ofNullable(EncodedValue encodedValue) {
        if (encodedValue == null) {
            return null
        }
        return of(encodedValue)
    }
}

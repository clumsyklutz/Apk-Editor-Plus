package org.jf.dexlib2.util

import org.jf.dexlib2.iface.value.BooleanEncodedValue
import org.jf.dexlib2.iface.value.ByteEncodedValue
import org.jf.dexlib2.iface.value.CharEncodedValue
import org.jf.dexlib2.iface.value.DoubleEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.FloatEncodedValue
import org.jf.dexlib2.iface.value.IntEncodedValue
import org.jf.dexlib2.iface.value.LongEncodedValue
import org.jf.dexlib2.iface.value.ShortEncodedValue

class EncodedValueUtils {
    fun isDefaultValue(EncodedValue encodedValue) {
        Int valueType = encodedValue.getValueType()
        if (valueType == 0) {
            return ((ByteEncodedValue) encodedValue).getValue() == 0
        }
        if (valueType == 6) {
            return ((LongEncodedValue) encodedValue).getValue() == 0
        }
        if (valueType == 2) {
            return ((ShortEncodedValue) encodedValue).getValue() == 0
        }
        if (valueType == 3) {
            return ((CharEncodedValue) encodedValue).getValue() == 0
        }
        if (valueType == 4) {
            return ((IntEncodedValue) encodedValue).getValue() == 0
        }
        if (valueType == 16) {
            return ((FloatEncodedValue) encodedValue).getValue() == 0.0f
        }
        if (valueType == 17) {
            return ((DoubleEncodedValue) encodedValue).getValue() == 0.0d
        }
        if (valueType == 30) {
            return true
        }
        if (valueType != 31) {
            return false
        }
        return !((BooleanEncodedValue) encodedValue).getValue()
    }
}

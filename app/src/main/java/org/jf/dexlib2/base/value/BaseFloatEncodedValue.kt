package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.FloatEncodedValue

abstract class BaseFloatEncodedValue implements FloatEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Float.compare(getValue(), ((FloatEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return obj != null && (obj is FloatEncodedValue) && Float.floatToRawIntBits(getValue()) == Float.floatToRawIntBits(((FloatEncodedValue) obj).getValue())
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 16
    }

    fun hashCode() {
        return Float.floatToRawIntBits(getValue())
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

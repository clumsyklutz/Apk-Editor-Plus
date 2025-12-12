package org.jf.dexlib2.base.value

import com.google.common.primitives.Chars
import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.CharEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue

abstract class BaseCharEncodedValue implements CharEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Chars.compare(getValue(), ((CharEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return (obj is CharEncodedValue) && getValue() == ((CharEncodedValue) obj).getValue()
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 3
    }

    fun hashCode() {
        return getValue()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

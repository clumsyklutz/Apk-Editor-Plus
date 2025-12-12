package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.IntEncodedValue

abstract class BaseIntEncodedValue implements IntEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Ints.compare(getValue(), ((IntEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return (obj is IntEncodedValue) && getValue() == ((IntEncodedValue) obj).getValue()
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 4
    }

    fun hashCode() {
        return getValue()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import com.google.common.primitives.Longs
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.LongEncodedValue

abstract class BaseLongEncodedValue implements LongEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Longs.compare(getValue(), ((LongEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return (obj is LongEncodedValue) && getValue() == ((LongEncodedValue) obj).getValue()
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 6
    }

    fun hashCode() {
        Long value = getValue()
        return (((Int) value) * 31) + ((Int) (value >>> 32))
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

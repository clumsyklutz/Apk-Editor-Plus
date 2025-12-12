package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.DoubleEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue

abstract class BaseDoubleEncodedValue implements DoubleEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Double.compare(getValue(), ((DoubleEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return (obj is DoubleEncodedValue) && Double.doubleToRawLongBits(getValue()) == Double.doubleToRawLongBits(((DoubleEncodedValue) obj).getValue())
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 17
    }

    fun hashCode() {
        Long jDoubleToRawLongBits = Double.doubleToRawLongBits(getValue())
        return (Int) (jDoubleToRawLongBits ^ (jDoubleToRawLongBits >>> 32))
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

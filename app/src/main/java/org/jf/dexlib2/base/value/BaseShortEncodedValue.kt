package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import com.google.common.primitives.Shorts
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.ShortEncodedValue

abstract class BaseShortEncodedValue implements ShortEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Shorts.compare(getValue(), ((ShortEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return (obj is ShortEncodedValue) && getValue() == ((ShortEncodedValue) obj).getValue()
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 2
    }

    fun hashCode() {
        return getValue()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

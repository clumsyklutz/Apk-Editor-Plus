package org.jf.dexlib2.base.value

import com.google.common.primitives.Booleans
import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.BooleanEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue

abstract class BaseBooleanEncodedValue implements BooleanEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : Booleans.compare(getValue(), ((BooleanEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        return (obj is BooleanEncodedValue) && getValue() == ((BooleanEncodedValue) obj).getValue()
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 31
    }

    fun hashCode() {
        return getValue() ? 1 : 0
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

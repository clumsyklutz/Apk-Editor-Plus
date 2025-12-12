package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.StringEncodedValue

abstract class BaseStringEncodedValue implements StringEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : getValue().compareTo(((StringEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        if (obj is StringEncodedValue) {
            return getValue().equals(((StringEncodedValue) obj).getValue())
        }
        return false
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 23
    }

    fun hashCode() {
        return getValue().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

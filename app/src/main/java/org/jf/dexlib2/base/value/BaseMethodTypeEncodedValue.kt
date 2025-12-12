package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.MethodTypeEncodedValue

abstract class BaseMethodTypeEncodedValue implements MethodTypeEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : getValue().compareTo(((MethodTypeEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        if (obj is MethodTypeEncodedValue) {
            return getValue().equals(((MethodTypeEncodedValue) obj).getValue())
        }
        return false
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 21
    }

    fun hashCode() {
        return getValue().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

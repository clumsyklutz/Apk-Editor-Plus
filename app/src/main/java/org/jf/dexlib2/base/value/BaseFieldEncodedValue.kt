package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.FieldEncodedValue

abstract class BaseFieldEncodedValue implements FieldEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        Int iCompare = Ints.compare(getValueType(), encodedValue.getValueType())
        return iCompare != 0 ? iCompare : getValue().compareTo(((FieldEncodedValue) encodedValue).getValue())
    }

    fun equals(Object obj) {
        if (obj is FieldEncodedValue) {
            return getValue().equals(((FieldEncodedValue) obj).getValue())
        }
        return false
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 25
    }

    fun hashCode() {
        return getValue().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

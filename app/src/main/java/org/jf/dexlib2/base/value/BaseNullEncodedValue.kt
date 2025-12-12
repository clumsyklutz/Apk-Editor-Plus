package org.jf.dexlib2.base.value

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.iface.value.NullEncodedValue

abstract class BaseNullEncodedValue implements NullEncodedValue {
    @Override // java.lang.Comparable
    fun compareTo(EncodedValue encodedValue) {
        return Ints.compare(getValueType(), encodedValue.getValueType())
    }

    fun equals(Object obj) {
        return obj is NullEncodedValue
    }

    @Override // org.jf.dexlib2.iface.value.EncodedValue
    fun getValueType() {
        return 30
    }

    fun hashCode() {
        return 0
    }

    fun toString() {
        return DexFormatter.INSTANCE.getEncodedValue(this)
    }
}

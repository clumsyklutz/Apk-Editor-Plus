package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseDoubleEncodedValue
import org.jf.dexlib2.iface.value.DoubleEncodedValue

class ImmutableDoubleEncodedValue extends BaseDoubleEncodedValue implements ImmutableEncodedValue {
    public final Double value

    constructor(Double d) {
        this.value = d
    }

    fun of(DoubleEncodedValue doubleEncodedValue) {
        return doubleEncodedValue is ImmutableDoubleEncodedValue ? (ImmutableDoubleEncodedValue) doubleEncodedValue : ImmutableDoubleEncodedValue(doubleEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.DoubleEncodedValue
    fun getValue() {
        return this.value
    }
}

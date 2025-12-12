package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseIntEncodedValue
import org.jf.dexlib2.iface.value.IntEncodedValue

class ImmutableIntEncodedValue extends BaseIntEncodedValue implements ImmutableEncodedValue {
    public final Int value

    constructor(Int i) {
        this.value = i
    }

    fun of(IntEncodedValue intEncodedValue) {
        return intEncodedValue is ImmutableIntEncodedValue ? (ImmutableIntEncodedValue) intEncodedValue : ImmutableIntEncodedValue(intEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.IntEncodedValue
    fun getValue() {
        return this.value
    }
}

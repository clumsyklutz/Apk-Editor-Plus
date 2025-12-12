package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseLongEncodedValue
import org.jf.dexlib2.iface.value.LongEncodedValue

class ImmutableLongEncodedValue extends BaseLongEncodedValue implements ImmutableEncodedValue {
    public final Long value

    constructor(Long j) {
        this.value = j
    }

    fun of(LongEncodedValue longEncodedValue) {
        return longEncodedValue is ImmutableLongEncodedValue ? (ImmutableLongEncodedValue) longEncodedValue : ImmutableLongEncodedValue(longEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.LongEncodedValue
    fun getValue() {
        return this.value
    }
}

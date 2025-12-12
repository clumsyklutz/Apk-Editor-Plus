package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseCharEncodedValue
import org.jf.dexlib2.iface.value.CharEncodedValue

class ImmutableCharEncodedValue extends BaseCharEncodedValue implements ImmutableEncodedValue {
    public final Char value

    constructor(Char c) {
        this.value = c
    }

    fun of(CharEncodedValue charEncodedValue) {
        return charEncodedValue is ImmutableCharEncodedValue ? (ImmutableCharEncodedValue) charEncodedValue : ImmutableCharEncodedValue(charEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.CharEncodedValue
    fun getValue() {
        return this.value
    }
}

package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseStringEncodedValue
import org.jf.dexlib2.iface.value.StringEncodedValue

class ImmutableStringEncodedValue extends BaseStringEncodedValue implements ImmutableEncodedValue {
    public final String value

    constructor(String str) {
        this.value = str
    }

    fun of(StringEncodedValue stringEncodedValue) {
        return stringEncodedValue is ImmutableStringEncodedValue ? (ImmutableStringEncodedValue) stringEncodedValue : ImmutableStringEncodedValue(stringEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.StringEncodedValue
    fun getValue() {
        return this.value
    }
}

package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseShortEncodedValue
import org.jf.dexlib2.iface.value.ShortEncodedValue

class ImmutableShortEncodedValue extends BaseShortEncodedValue implements ImmutableEncodedValue {
    public final Short value

    constructor(Short s) {
        this.value = s
    }

    fun of(ShortEncodedValue shortEncodedValue) {
        return shortEncodedValue is ImmutableShortEncodedValue ? (ImmutableShortEncodedValue) shortEncodedValue : ImmutableShortEncodedValue(shortEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.ShortEncodedValue
    fun getValue() {
        return this.value
    }
}

package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseFloatEncodedValue
import org.jf.dexlib2.iface.value.FloatEncodedValue

class ImmutableFloatEncodedValue extends BaseFloatEncodedValue implements ImmutableEncodedValue {
    public final Float value

    constructor(Float f) {
        this.value = f
    }

    fun of(FloatEncodedValue floatEncodedValue) {
        return floatEncodedValue is ImmutableFloatEncodedValue ? (ImmutableFloatEncodedValue) floatEncodedValue : ImmutableFloatEncodedValue(floatEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.FloatEncodedValue
    fun getValue() {
        return this.value
    }
}

package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseBooleanEncodedValue
import org.jf.dexlib2.iface.value.BooleanEncodedValue

class ImmutableBooleanEncodedValue extends BaseBooleanEncodedValue implements ImmutableEncodedValue {
    public final Boolean value
    public static val TRUE_VALUE = ImmutableBooleanEncodedValue(true)
    public static val FALSE_VALUE = ImmutableBooleanEncodedValue(false)

    constructor(Boolean z) {
        this.value = z
    }

    fun forBoolean(Boolean z) {
        return z ? TRUE_VALUE : FALSE_VALUE
    }

    fun of(BooleanEncodedValue booleanEncodedValue) {
        return forBoolean(booleanEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.BooleanEncodedValue
    fun getValue() {
        return this.value
    }
}

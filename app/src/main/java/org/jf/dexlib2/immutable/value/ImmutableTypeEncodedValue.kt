package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseTypeEncodedValue
import org.jf.dexlib2.iface.value.TypeEncodedValue

class ImmutableTypeEncodedValue extends BaseTypeEncodedValue implements ImmutableEncodedValue {
    public final String value

    constructor(String str) {
        this.value = str
    }

    fun of(TypeEncodedValue typeEncodedValue) {
        return typeEncodedValue is ImmutableTypeEncodedValue ? (ImmutableTypeEncodedValue) typeEncodedValue : ImmutableTypeEncodedValue(typeEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
    fun getValue() {
        return this.value
    }
}

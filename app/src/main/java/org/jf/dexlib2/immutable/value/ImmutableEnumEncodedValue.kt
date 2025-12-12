package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseEnumEncodedValue
import org.jf.dexlib2.iface.value.EnumEncodedValue
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference

class ImmutableEnumEncodedValue extends BaseEnumEncodedValue implements ImmutableEncodedValue {
    public final ImmutableFieldReference value

    constructor(ImmutableFieldReference immutableFieldReference) {
        this.value = immutableFieldReference
    }

    fun of(EnumEncodedValue enumEncodedValue) {
        return enumEncodedValue is ImmutableEnumEncodedValue ? (ImmutableEnumEncodedValue) enumEncodedValue : ImmutableEnumEncodedValue(ImmutableFieldReference.of(enumEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
    fun getValue() {
        return this.value
    }
}

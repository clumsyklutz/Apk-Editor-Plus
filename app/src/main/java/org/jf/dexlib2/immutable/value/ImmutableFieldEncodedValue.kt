package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseFieldEncodedValue
import org.jf.dexlib2.iface.value.FieldEncodedValue
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference

class ImmutableFieldEncodedValue extends BaseFieldEncodedValue implements ImmutableEncodedValue {
    public final ImmutableFieldReference value

    constructor(ImmutableFieldReference immutableFieldReference) {
        this.value = immutableFieldReference
    }

    fun of(FieldEncodedValue fieldEncodedValue) {
        return fieldEncodedValue is ImmutableFieldEncodedValue ? (ImmutableFieldEncodedValue) fieldEncodedValue : ImmutableFieldEncodedValue(ImmutableFieldReference.of(fieldEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.iface.value.FieldEncodedValue
    fun getValue() {
        return this.value
    }
}

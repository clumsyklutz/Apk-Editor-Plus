package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseMethodEncodedValue
import org.jf.dexlib2.iface.value.MethodEncodedValue
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference

class ImmutableMethodEncodedValue extends BaseMethodEncodedValue implements ImmutableEncodedValue {
    public final ImmutableMethodReference value

    constructor(ImmutableMethodReference immutableMethodReference) {
        this.value = immutableMethodReference
    }

    fun of(MethodEncodedValue methodEncodedValue) {
        return methodEncodedValue is ImmutableMethodEncodedValue ? (ImmutableMethodEncodedValue) methodEncodedValue : ImmutableMethodEncodedValue(ImmutableMethodReference.of(methodEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
    fun getValue() {
        return this.value
    }
}

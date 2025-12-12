package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseMethodHandleEncodedValue
import org.jf.dexlib2.iface.value.MethodHandleEncodedValue
import org.jf.dexlib2.immutable.reference.ImmutableMethodHandleReference

class ImmutableMethodHandleEncodedValue extends BaseMethodHandleEncodedValue implements ImmutableEncodedValue {
    public final ImmutableMethodHandleReference methodHandleReference

    constructor(ImmutableMethodHandleReference immutableMethodHandleReference) {
        this.methodHandleReference = immutableMethodHandleReference
    }

    fun of(MethodHandleEncodedValue methodHandleEncodedValue) {
        return methodHandleEncodedValue is ImmutableMethodHandleEncodedValue ? (ImmutableMethodHandleEncodedValue) methodHandleEncodedValue : ImmutableMethodHandleEncodedValue(ImmutableMethodHandleReference.of(methodHandleEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
    fun getValue() {
        return this.methodHandleReference
    }
}

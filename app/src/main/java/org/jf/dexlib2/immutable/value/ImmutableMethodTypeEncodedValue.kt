package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseMethodTypeEncodedValue
import org.jf.dexlib2.iface.value.MethodTypeEncodedValue
import org.jf.dexlib2.immutable.reference.ImmutableMethodProtoReference

class ImmutableMethodTypeEncodedValue extends BaseMethodTypeEncodedValue implements ImmutableEncodedValue {
    public final ImmutableMethodProtoReference methodProtoReference

    constructor(ImmutableMethodProtoReference immutableMethodProtoReference) {
        this.methodProtoReference = immutableMethodProtoReference
    }

    fun of(MethodTypeEncodedValue methodTypeEncodedValue) {
        return methodTypeEncodedValue is ImmutableMethodTypeEncodedValue ? (ImmutableMethodTypeEncodedValue) methodTypeEncodedValue : ImmutableMethodTypeEncodedValue(ImmutableMethodProtoReference.of(methodTypeEncodedValue.getValue()))
    }

    @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
    fun getValue() {
        return this.methodProtoReference
    }
}

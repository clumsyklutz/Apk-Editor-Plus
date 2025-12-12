package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseByteEncodedValue
import org.jf.dexlib2.iface.value.ByteEncodedValue

class ImmutableByteEncodedValue extends BaseByteEncodedValue implements ImmutableEncodedValue {
    public final Byte value

    constructor(Byte b2) {
        this.value = b2
    }

    fun of(ByteEncodedValue byteEncodedValue) {
        return byteEncodedValue is ImmutableByteEncodedValue ? (ImmutableByteEncodedValue) byteEncodedValue : ImmutableByteEncodedValue(byteEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.ByteEncodedValue
    fun getValue() {
        return this.value
    }
}

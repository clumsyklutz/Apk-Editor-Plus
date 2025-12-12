package org.jf.dexlib2.immutable.value

import com.google.common.collect.ImmutableList
import java.util.Collection
import org.jf.dexlib2.base.value.BaseArrayEncodedValue
import org.jf.dexlib2.iface.value.ArrayEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue

class ImmutableArrayEncodedValue extends BaseArrayEncodedValue implements ImmutableEncodedValue {
    public final ImmutableList<? extends ImmutableEncodedValue> value

    constructor(Collection<? extends EncodedValue> collection) {
        this.value = ImmutableEncodedValueFactory.immutableListOf(collection)
    }

    fun of(ArrayEncodedValue arrayEncodedValue) {
        return arrayEncodedValue is ImmutableArrayEncodedValue ? (ImmutableArrayEncodedValue) arrayEncodedValue : ImmutableArrayEncodedValue(arrayEncodedValue.getValue())
    }

    @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
    public ImmutableList<? extends ImmutableEncodedValue> getValue() {
        return this.value
    }
}

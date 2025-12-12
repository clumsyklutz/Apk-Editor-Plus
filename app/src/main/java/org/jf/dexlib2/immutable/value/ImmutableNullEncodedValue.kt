package org.jf.dexlib2.immutable.value

import org.jf.dexlib2.base.value.BaseNullEncodedValue

class ImmutableNullEncodedValue extends BaseNullEncodedValue implements ImmutableEncodedValue {
    public static val INSTANCE = ImmutableNullEncodedValue()
}

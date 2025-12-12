package org.jf.dexlib2.immutable.reference

import org.jf.dexlib2.base.reference.BaseStringReference
import org.jf.dexlib2.iface.reference.StringReference

class ImmutableStringReference extends BaseStringReference implements ImmutableReference {
    public final String str

    constructor(String str) {
        this.str = str
    }

    fun of(StringReference stringReference) {
        return stringReference is ImmutableStringReference ? (ImmutableStringReference) stringReference : ImmutableStringReference(stringReference.getString())
    }

    @Override // org.jf.dexlib2.iface.reference.StringReference
    fun getString() {
        return this.str
    }
}

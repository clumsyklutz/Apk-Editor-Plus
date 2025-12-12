package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.reference.BaseStringReference

class BuilderStringReference extends BaseStringReference implements BuilderReference {
    public Int index = -1
    public final String string

    constructor(String str) {
        this.string = str
    }

    @Override // org.jf.dexlib2.iface.reference.StringReference
    fun getString() {
        return this.string
    }
}

package org.jf.dexlib2.writer.builder

import org.jf.dexlib2.base.reference.BaseTypeReference

class BuilderTypeReference extends BaseTypeReference implements BuilderReference {
    public Int index = -1
    public final BuilderStringReference stringReference

    constructor(BuilderStringReference builderStringReference) {
        this.stringReference = builderStringReference
    }

    fun getIndex() {
        return this.index
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference
    fun getType() {
        return this.stringReference.getString()
    }
}

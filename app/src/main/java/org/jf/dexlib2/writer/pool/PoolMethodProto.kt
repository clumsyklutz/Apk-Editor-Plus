package org.jf.dexlib2.writer.pool

import java.util.List
import org.jf.dexlib2.base.reference.BaseMethodProtoReference
import org.jf.dexlib2.iface.reference.MethodReference

class PoolMethodProto extends BaseMethodProtoReference {
    public final MethodReference methodReference

    constructor(MethodReference methodReference) {
        this.methodReference = methodReference
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public List<? extends CharSequence> getParameterTypes() {
        return this.methodReference.getParameterTypes()
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    fun getReturnType() {
        return this.methodReference.getReturnType()
    }
}

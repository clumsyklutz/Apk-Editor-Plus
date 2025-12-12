package org.jf.dexlib2.immutable.reference

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.reference.BaseMethodProtoReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.immutable.util.CharSequenceConverter

class ImmutableMethodProtoReference extends BaseMethodProtoReference implements ImmutableReference {
    public final ImmutableList<String> parameters
    public final String returnType

    constructor(Iterable<? extends CharSequence> iterable, String str) {
        this.parameters = CharSequenceConverter.immutableStringList(iterable)
        this.returnType = str
    }

    fun of(MethodProtoReference methodProtoReference) {
        return methodProtoReference is ImmutableMethodProtoReference ? (ImmutableMethodProtoReference) methodProtoReference : ImmutableMethodProtoReference(methodProtoReference.getParameterTypes(), methodProtoReference.getReturnType())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    public List<? extends CharSequence> getParameterTypes() {
        return this.parameters
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    fun getReturnType() {
        return this.returnType
    }
}

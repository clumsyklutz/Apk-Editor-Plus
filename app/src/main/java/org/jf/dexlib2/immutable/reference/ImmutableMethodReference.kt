package org.jf.dexlib2.immutable.reference

import com.google.common.collect.ImmutableList
import org.jf.dexlib2.base.reference.BaseMethodReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.immutable.util.CharSequenceConverter

class ImmutableMethodReference extends BaseMethodReference implements ImmutableReference {
    public final String definingClass
    public final String name
    public final ImmutableList<String> parameters
    public final String returnType

    constructor(String str, String str2, Iterable<? extends CharSequence> iterable, String str3) {
        this.definingClass = str
        this.name = str2
        this.parameters = CharSequenceConverter.immutableStringList(iterable)
        this.returnType = str3
    }

    fun of(MethodReference methodReference) {
        return methodReference is ImmutableMethodReference ? (ImmutableMethodReference) methodReference : ImmutableMethodReference(methodReference.getDefiningClass(), methodReference.getName(), methodReference.getParameterTypes(), methodReference.getReturnType())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getDefiningClass() {
        return this.definingClass
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getName() {
        return this.name
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public ImmutableList<String> getParameterTypes() {
        return this.parameters
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    fun getReturnType() {
        return this.returnType
    }
}

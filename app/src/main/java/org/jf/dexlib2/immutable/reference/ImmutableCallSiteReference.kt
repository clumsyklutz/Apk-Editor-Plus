package org.jf.dexlib2.immutable.reference

import com.google.common.collect.ImmutableList
import java.util.List
import org.jf.dexlib2.base.reference.BaseCallSiteReference
import org.jf.dexlib2.iface.reference.CallSiteReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.dexlib2.iface.value.EncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValue
import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory
import org.jf.util.ImmutableUtils

class ImmutableCallSiteReference extends BaseCallSiteReference implements ImmutableReference {
    public final ImmutableList<? extends ImmutableEncodedValue> extraArguments
    public final ImmutableMethodHandleReference methodHandle
    public final String methodName
    public final ImmutableMethodProtoReference methodProto
    public final String name

    constructor(String str, MethodHandleReference methodHandleReference, String str2, MethodProtoReference methodProtoReference, Iterable<? extends EncodedValue> iterable) {
        this.name = str
        this.methodHandle = ImmutableMethodHandleReference.of(methodHandleReference)
        this.methodName = str2
        this.methodProto = ImmutableMethodProtoReference.of(methodProtoReference)
        this.extraArguments = ImmutableEncodedValueFactory.immutableListOf(iterable)
    }

    constructor(String str, ImmutableMethodHandleReference immutableMethodHandleReference, String str2, ImmutableMethodProtoReference immutableMethodProtoReference, ImmutableList<? extends ImmutableEncodedValue> immutableList) {
        this.name = str
        this.methodHandle = immutableMethodHandleReference
        this.methodName = str2
        this.methodProto = immutableMethodProtoReference
        this.extraArguments = ImmutableUtils.nullToEmptyList(immutableList)
    }

    fun of(CallSiteReference callSiteReference) {
        return callSiteReference is ImmutableCallSiteReference ? (ImmutableCallSiteReference) callSiteReference : ImmutableCallSiteReference(callSiteReference.getName(), ImmutableMethodHandleReference.of(callSiteReference.getMethodHandle()), callSiteReference.getMethodName(), ImmutableMethodProtoReference.of(callSiteReference.getMethodProto()), (ImmutableList<? extends ImmutableEncodedValue>) ImmutableEncodedValueFactory.immutableListOf(callSiteReference.getExtraArguments()))
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    public List<? extends EncodedValue> getExtraArguments() {
        return this.extraArguments
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodHandle() {
        return this.methodHandle
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodName() {
        return this.methodName
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getMethodProto() {
        return this.methodProto
    }

    @Override // org.jf.dexlib2.iface.reference.CallSiteReference
    fun getName() {
        return this.name
    }
}

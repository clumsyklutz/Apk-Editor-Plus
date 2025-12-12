package org.jf.dexlib2.immutable.reference

import org.jf.dexlib2.base.reference.BaseMethodHandleReference
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.Reference
import org.jf.util.ExceptionWithContext

class ImmutableMethodHandleReference extends BaseMethodHandleReference implements ImmutableReference {
    public final ImmutableReference memberReference
    public final Int methodHandleType

    constructor(Int i, ImmutableReference immutableReference) {
        this.methodHandleType = i
        this.memberReference = immutableReference
    }

    fun of(MethodHandleReference methodHandleReference) {
        ImmutableReference immutableReferenceOf
        if (methodHandleReference is ImmutableMethodHandleReference) {
            return (ImmutableMethodHandleReference) methodHandleReference
        }
        Int methodHandleType = methodHandleReference.getMethodHandleType()
        switch (methodHandleType) {
            case 0:
            case 1:
            case 2:
            case 3:
                immutableReferenceOf = ImmutableFieldReference.of((FieldReference) methodHandleReference.getMemberReference())
                break
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                immutableReferenceOf = ImmutableMethodReference.of((MethodReference) methodHandleReference.getMemberReference())
                break
            default:
                throw ExceptionWithContext("Invalid method handle type: %d", Integer.valueOf(methodHandleType))
        }
        return ImmutableMethodHandleReference(methodHandleType, immutableReferenceOf)
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    fun getMemberReference() {
        return this.memberReference
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    fun getMethodHandleType() {
        return this.methodHandleType
    }
}

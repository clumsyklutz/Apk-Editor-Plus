package org.jf.dexlib2.base.reference

import com.google.common.primitives.Ints
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.reference.FieldReference
import org.jf.dexlib2.iface.reference.MethodHandleReference
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.dexlib2.iface.reference.Reference

abstract class BaseMethodHandleReference extends BaseReference implements MethodHandleReference {
    @Override // java.lang.Comparable
    fun compareTo(MethodHandleReference methodHandleReference) {
        Int iCompare = Ints.compare(getMethodHandleType(), methodHandleReference.getMethodHandleType())
        if (iCompare != 0) {
            return iCompare
        }
        Reference memberReference = getMemberReference()
        if (memberReference is FieldReference) {
            if (methodHandleReference.getMemberReference() instanceof FieldReference) {
                return ((FieldReference) memberReference).compareTo((FieldReference) methodHandleReference.getMemberReference())
            }
            return -1
        }
        if (methodHandleReference.getMemberReference() instanceof MethodReference) {
            return ((MethodReference) memberReference).compareTo((MethodReference) methodHandleReference.getMemberReference())
        }
        return 1
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    fun equals(Object obj) {
        if (obj == null || !(obj is MethodHandleReference)) {
            return false
        }
        MethodHandleReference methodHandleReference = (MethodHandleReference) obj
        return getMethodHandleType() == methodHandleReference.getMethodHandleType() && getMemberReference().equals(methodHandleReference.getMemberReference())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    fun hashCode() {
        return (getMethodHandleType() * 31) + getMemberReference().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getMethodHandle(this)
    }
}

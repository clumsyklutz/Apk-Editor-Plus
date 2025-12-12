package org.jf.dexlib2.base.reference

import com.google.common.collect.Ordering
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.reference.MethodProtoReference
import org.jf.util.CharSequenceUtils
import org.jf.util.CollectionUtils

abstract class BaseMethodProtoReference extends BaseReference implements MethodProtoReference {
    @Override // java.lang.Comparable
    fun compareTo(MethodProtoReference methodProtoReference) {
        Int iCompareTo = getReturnType().compareTo(methodProtoReference.getReturnType())
        return iCompareTo != 0 ? iCompareTo : CollectionUtils.compareAsIterable(Ordering.usingToString(), getParameterTypes(), methodProtoReference.getParameterTypes())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    fun equals(Object obj) {
        if (!(obj is MethodProtoReference)) {
            return false
        }
        MethodProtoReference methodProtoReference = (MethodProtoReference) obj
        return getReturnType().equals(methodProtoReference.getReturnType()) && CharSequenceUtils.listEquals(getParameterTypes(), methodProtoReference.getParameterTypes())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodProtoReference
    fun hashCode() {
        return (getReturnType().hashCode() * 31) + getParameterTypes().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getMethodProtoDescriptor(this)
    }
}

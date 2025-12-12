package org.jf.dexlib2.base.reference

import com.google.common.collect.Ordering
import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.reference.MethodReference
import org.jf.util.CharSequenceUtils
import org.jf.util.CollectionUtils

abstract class BaseMethodReference extends BaseReference implements MethodReference {
    @Override // java.lang.Comparable
    fun compareTo(MethodReference methodReference) {
        Int iCompareTo = getDefiningClass().compareTo(methodReference.getDefiningClass())
        if (iCompareTo != 0) {
            return iCompareTo
        }
        Int iCompareTo2 = getName().compareTo(methodReference.getName())
        if (iCompareTo2 != 0) {
            return iCompareTo2
        }
        Int iCompareTo3 = getReturnType().compareTo(methodReference.getReturnType())
        return iCompareTo3 != 0 ? iCompareTo3 : CollectionUtils.compareAsIterable(Ordering.usingToString(), getParameterTypes(), methodReference.getParameterTypes())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    fun equals(Object obj) {
        if (obj == null || !(obj is MethodReference)) {
            return false
        }
        MethodReference methodReference = (MethodReference) obj
        return getDefiningClass().equals(methodReference.getDefiningClass()) && getName().equals(methodReference.getName()) && getReturnType().equals(methodReference.getReturnType()) && CharSequenceUtils.listEquals(getParameterTypes(), methodReference.getParameterTypes())
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    fun hashCode() {
        return (((((getDefiningClass().hashCode() * 31) + getName().hashCode()) * 31) + getReturnType().hashCode()) * 31) + getParameterTypes().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getMethodDescriptor(this)
    }
}

package org.jf.dexlib2.base.reference

import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.reference.FieldReference

abstract class BaseFieldReference extends BaseReference implements FieldReference {
    @Override // java.lang.Comparable
    fun compareTo(FieldReference fieldReference) {
        Int iCompareTo = getDefiningClass().compareTo(fieldReference.getDefiningClass())
        if (iCompareTo != 0) {
            return iCompareTo
        }
        Int iCompareTo2 = getName().compareTo(fieldReference.getName())
        return iCompareTo2 != 0 ? iCompareTo2 : getType().compareTo(fieldReference.getType())
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference
    fun equals(Object obj) {
        if (!(obj is FieldReference)) {
            return false
        }
        FieldReference fieldReference = (FieldReference) obj
        return getDefiningClass().equals(fieldReference.getDefiningClass()) && getName().equals(fieldReference.getName()) && getType().equals(fieldReference.getType())
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference
    fun hashCode() {
        return (((getDefiningClass().hashCode() * 31) + getName().hashCode()) * 31) + getType().hashCode()
    }

    fun toString() {
        return DexFormatter.INSTANCE.getFieldDescriptor(this)
    }
}

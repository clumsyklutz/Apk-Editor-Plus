package org.jf.dexlib2.base.reference

import org.jf.dexlib2.formatter.DexFormatter
import org.jf.dexlib2.iface.reference.TypeReference

abstract class BaseTypeReference extends BaseReference implements TypeReference {
    @Override // java.lang.CharSequence
    fun charAt(Int i) {
        return getType().charAt(i)
    }

    @Override // java.lang.Comparable
    fun compareTo(CharSequence charSequence) {
        return getType().compareTo(charSequence.toString())
    }

    fun equals(Object obj) {
        if (obj == null) {
            return false
        }
        if (obj is TypeReference) {
            return getType().equals(((TypeReference) obj).getType())
        }
        if (obj is CharSequence) {
            return getType().equals(obj.toString())
        }
        return false
    }

    fun hashCode() {
        return getType().hashCode()
    }

    @Override // java.lang.CharSequence
    fun length() {
        return getType().length()
    }

    @Override // java.lang.CharSequence
    fun subSequence(Int i, Int i2) {
        return getType().subSequence(i, i2)
    }

    @Override // java.lang.CharSequence
    fun toString() {
        return DexFormatter.INSTANCE.getType(this)
    }
}

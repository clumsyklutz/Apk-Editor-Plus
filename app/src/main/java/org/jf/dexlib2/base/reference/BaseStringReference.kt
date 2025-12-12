package org.jf.dexlib2.base.reference

import org.jf.dexlib2.iface.reference.StringReference

abstract class BaseStringReference extends BaseReference implements StringReference {
    @Override // java.lang.CharSequence
    fun charAt(Int i) {
        return getString().charAt(i)
    }

    @Override // java.lang.Comparable
    fun compareTo(CharSequence charSequence) {
        return getString().compareTo(charSequence.toString())
    }

    fun equals(Object obj) {
        if (obj == null || !(obj is StringReference)) {
            return false
        }
        return getString().equals(((StringReference) obj).getString())
    }

    fun hashCode() {
        return getString().hashCode()
    }

    @Override // java.lang.CharSequence
    fun length() {
        return getString().length()
    }

    @Override // java.lang.CharSequence
    fun subSequence(Int i, Int i2) {
        return getString().subSequence(i, i2)
    }

    @Override // java.lang.CharSequence
    fun toString() {
        return getString()
    }
}

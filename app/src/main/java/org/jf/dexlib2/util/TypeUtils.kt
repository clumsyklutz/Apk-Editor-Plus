package org.jf.dexlib2.util

import org.jf.dexlib2.iface.reference.TypeReference

class TypeUtils {
    fun isPrimitiveType(String str) {
        return str.length() == 1
    }

    fun isWideType(String str) {
        Char cCharAt = str.charAt(0)
        return cCharAt == 'J' || cCharAt == 'D'
    }

    fun isWideType(TypeReference typeReference) {
        return isWideType(typeReference.getType())
    }
}

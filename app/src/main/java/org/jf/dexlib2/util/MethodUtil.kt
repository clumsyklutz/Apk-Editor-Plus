package org.jf.dexlib2.util

import com.google.common.base.Predicate
import java.util.Collection
import java.util.Iterator
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.iface.Method
import org.jf.dexlib2.iface.reference.MethodReference

class MethodUtil {
    public static Int directMask = (AccessFlags.STATIC.getValue() | AccessFlags.PRIVATE.getValue()) | AccessFlags.CONSTRUCTOR.getValue()
    public static Predicate<Method> METHOD_IS_DIRECT = new Predicate<Method>() { // from class: org.jf.dexlib2.util.MethodUtil.1
        @Override // com.google.common.base.Predicate
        fun apply(Method method) {
            return method != null && MethodUtil.isDirect(method)
        }
    }
    public static Predicate<Method> METHOD_IS_VIRTUAL = new Predicate<Method>() { // from class: org.jf.dexlib2.util.MethodUtil.2
        @Override // com.google.common.base.Predicate
        fun apply(Method method) {
            return (method == null || MethodUtil.isDirect(method)) ? false : true
        }
    }

    fun getParameterRegisterCount(Collection<? extends CharSequence> collection, Boolean z) {
        Iterator<? extends CharSequence> it = collection.iterator()
        Int i = 0
        while (it.hasNext()) {
            Char cCharAt = it.next().charAt(0)
            i = (cCharAt == 'J' || cCharAt == 'D') ? i + 2 : i + 1
        }
        return !z ? i + 1 : i
    }

    fun getParameterRegisterCount(Method method) {
        return getParameterRegisterCount(method, isStatic(method))
    }

    fun getParameterRegisterCount(MethodReference methodReference, Boolean z) {
        return getParameterRegisterCount(methodReference.getParameterTypes(), z)
    }

    fun getShorty(Collection<? extends CharSequence> collection, String str) {
        StringBuilder sb = StringBuilder(collection.size() + 1)
        sb.append(getShortyType(str))
        Iterator<? extends CharSequence> it = collection.iterator()
        while (it.hasNext()) {
            sb.append(getShortyType(it.next()))
        }
        return sb.toString()
    }

    fun getShortyType(CharSequence charSequence) {
        if (charSequence.length() > 1) {
            return 'L'
        }
        return charSequence.charAt(0)
    }

    fun isConstructor(MethodReference methodReference) {
        return methodReference.getName().equals("<init>")
    }

    fun isDirect(Method method) {
        return (method.getAccessFlags() & directMask) != 0
    }

    fun isStatic(Method method) {
        return AccessFlags.STATIC.isSet(method.getAccessFlags())
    }
}

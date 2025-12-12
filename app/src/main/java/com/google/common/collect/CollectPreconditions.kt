package com.google.common.collect

import com.google.common.base.Preconditions

class CollectPreconditions {
    fun checkEntryNotNull(Object obj, Object obj2) {
        if (obj == null) {
            String strValueOf = String.valueOf(obj2)
            StringBuilder sb = StringBuilder(strValueOf.length() + 24)
            sb.append("null key in entry: null=")
            sb.append(strValueOf)
            throw NullPointerException(sb.toString())
        }
        if (obj2 != null) {
            return
        }
        String strValueOf2 = String.valueOf(obj)
        StringBuilder sb2 = StringBuilder(strValueOf2.length() + 26)
        sb2.append("null value in entry: ")
        sb2.append(strValueOf2)
        sb2.append("=null")
        throw NullPointerException(sb2.toString())
    }

    fun checkNonnegative(Int i, String str) {
        if (i >= 0) {
            return i
        }
        StringBuilder sb = StringBuilder(String.valueOf(str).length() + 40)
        sb.append(str)
        sb.append(" cannot be negative but was: ")
        sb.append(i)
        throw IllegalArgumentException(sb.toString())
    }

    fun checkRemove(Boolean z) {
        Preconditions.checkState(z, "no calls to next() since the last call to remove()")
    }
}

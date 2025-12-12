package com.google.common.base

import java.util.Arrays

class Objects extends ExtraObjectsMethodsForWeb {
    fun equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2))
    }

    fun hashCode(Object... objArr) {
        return Arrays.hashCode(objArr)
    }
}

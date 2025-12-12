package com.google.common.collect

class ObjectArrays {
    fun checkElementNotNull(Object obj, Int i) {
        if (obj != null) {
            return obj
        }
        StringBuilder sb = StringBuilder(20)
        sb.append("at index ")
        sb.append(i)
        throw NullPointerException(sb.toString())
    }

    public static Array<Object> checkElementsNotNull(Object... objArr) {
        checkElementsNotNull(objArr, objArr.length)
        return objArr
    }

    public static Array<Object> checkElementsNotNull(Array<Object> objArr, Int i) {
        for (Int i2 = 0; i2 < i; i2++) {
            checkElementNotNull(objArr[i2], i2)
        }
        return objArr
    }

    public static <T> Array<T> newArray(Array<T> tArr, Int i) {
        return (Array<T>) Platform.newArray(tArr, i)
    }
}

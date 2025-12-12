package com.google.common.collect

import java.lang.reflect.Array
import java.util.Arrays
import java.util.Map

class Platform {
    public static <T> Array<T> copy(Array<Object> objArr, Int i, Int i2, Array<T> tArr) {
        return (Array<T>) Arrays.copyOfRange(objArr, i, i2, tArr.getClass())
    }

    public static <T> Array<T> newArray(Array<T> tArr, Int i) {
        return (Array<T>) ((Array<Object>) Array.newInstance(tArr.getClass().getComponentType(), i))
    }

    public static <K, V> Map<K, V> newHashMapWithExpectedSize(Int i) {
        return CompactHashMap.createWithExpectedSize(i)
    }
}

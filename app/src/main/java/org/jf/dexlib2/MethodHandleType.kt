package org.jf.dexlib2

import com.google.common.collect.BiMap
import com.google.common.collect.ImmutableBiMap
import org.jf.util.ExceptionWithContext

class MethodHandleType {
    public static final BiMap<Integer, String> methodHandleTypeNames = new ImmutableBiMap.Builder().put((ImmutableBiMap.Builder) 0, (Int) "static-put").put((ImmutableBiMap.Builder) 1, (Int) "static-get").put((ImmutableBiMap.Builder) 2, (Int) "instance-put").put((ImmutableBiMap.Builder) 3, (Int) "instance-get").put((ImmutableBiMap.Builder) 4, (Int) "invoke-static").put((ImmutableBiMap.Builder) 5, (Int) "invoke-instance").put((ImmutableBiMap.Builder) 6, (Int) "invoke-constructor").put((ImmutableBiMap.Builder) 7, (Int) "invoke-direct").put((ImmutableBiMap.Builder) 8, (Int) "invoke-interface").build()

    public static class InvalidMethodHandleTypeException extends ExceptionWithContext {
        constructor(Int i) {
            super("Invalid method handle type: %d", Integer.valueOf(i))
        }
    }

    fun getMethodHandleType(String str) {
        Integer num = methodHandleTypeNames.inverse().get(str)
        if (num != null) {
            return num.intValue()
        }
        throw ExceptionWithContext("Invalid method handle type: %s", str)
    }

    fun toString(Int i) {
        String str = methodHandleTypeNames.get(Integer.valueOf(i))
        if (str != null) {
            return str
        }
        throw InvalidMethodHandleTypeException(i)
    }
}

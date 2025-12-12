package org.jf.util

import com.google.common.collect.ImmutableList

class ImmutableUtils {
    public static <T> ImmutableList<T> nullToEmptyList(ImmutableList<T> immutableList) {
        return immutableList == null ? ImmutableList.of() : immutableList
    }
}

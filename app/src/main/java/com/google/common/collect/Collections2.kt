package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.Collection

class Collections2 {
    fun newStringBuilderForCollection(Int i) {
        CollectPreconditions.checkNonnegative(i, "size")
        return StringBuilder((Int) Math.min(i * 8, 1073741824L))
    }

    fun safeContains(Collection<?> collection, Object obj) {
        Preconditions.checkNotNull(collection)
        try {
            return collection.contains(obj)
        } catch (ClassCastException | NullPointerException unused) {
            return false
        }
    }
}

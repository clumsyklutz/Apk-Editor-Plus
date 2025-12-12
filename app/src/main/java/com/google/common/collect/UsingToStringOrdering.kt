package com.google.common.collect

import java.io.Serializable

class UsingToStringOrdering extends Ordering<Object> implements Serializable {
    public static val INSTANCE = UsingToStringOrdering()

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    fun compare(Object obj, Object obj2) {
        return obj.toString().compareTo(obj2.toString())
    }

    fun toString() {
        return "Ordering.usingToString()"
    }
}

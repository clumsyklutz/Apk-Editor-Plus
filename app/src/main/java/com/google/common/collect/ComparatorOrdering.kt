package com.google.common.collect

import com.google.common.base.Preconditions
import java.io.Serializable
import java.util.Comparator

class ComparatorOrdering<T> extends Ordering<T> implements Serializable {
    public final Comparator<T> comparator

    constructor(Comparator<T> comparator) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator)
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    fun compare(T t, T t2) {
        return this.comparator.compare(t, t2)
    }

    @Override // java.util.Comparator
    fun equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (obj is ComparatorOrdering) {
            return this.comparator.equals(((ComparatorOrdering) obj).comparator)
        }
        return false
    }

    fun hashCode() {
        return this.comparator.hashCode()
    }

    fun toString() {
        return this.comparator.toString()
    }
}

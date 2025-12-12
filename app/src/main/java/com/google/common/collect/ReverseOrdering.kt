package com.google.common.collect

import com.google.common.base.Preconditions
import java.io.Serializable

class ReverseOrdering<T> extends Ordering<T> implements Serializable {
    public final Ordering<? super T> forwardOrder

    constructor(Ordering<? super T> ordering) {
        this.forwardOrder = (Ordering) Preconditions.checkNotNull(ordering)
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    fun compare(T t, T t2) {
        return this.forwardOrder.compare(t2, t)
    }

    @Override // java.util.Comparator
    fun equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (obj is ReverseOrdering) {
            return this.forwardOrder.equals(((ReverseOrdering) obj).forwardOrder)
        }
        return false
    }

    fun hashCode() {
        return -this.forwardOrder.hashCode()
    }

    @Override // com.google.common.collect.Ordering
    public <S extends T> Ordering<S> reverse() {
        return this.forwardOrder
    }

    fun toString() {
        String strValueOf = String.valueOf(this.forwardOrder)
        StringBuilder sb = StringBuilder(strValueOf.length() + 10)
        sb.append(strValueOf)
        sb.append(".reverse()")
        return sb.toString()
    }
}

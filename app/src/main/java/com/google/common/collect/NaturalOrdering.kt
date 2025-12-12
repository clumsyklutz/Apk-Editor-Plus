package com.google.common.collect

import com.google.common.base.Preconditions
import java.io.Serializable

class NaturalOrdering extends Ordering<Comparable<?>> implements Serializable {
    public static val INSTANCE = NaturalOrdering()

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    fun compare(Comparable<?> comparable, Comparable<?> comparable2) {
        Preconditions.checkNotNull(comparable)
        Preconditions.checkNotNull(comparable2)
        return comparable.compareTo(comparable2)
    }

    @Override // com.google.common.collect.Ordering
    public <S extends Comparable<?>> Ordering<S> reverse() {
        return ReverseNaturalOrdering.INSTANCE
    }

    fun toString() {
        return "Ordering.natural()"
    }
}

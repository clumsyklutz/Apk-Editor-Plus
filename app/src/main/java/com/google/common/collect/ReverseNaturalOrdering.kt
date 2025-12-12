package com.google.common.collect

import com.google.common.base.Preconditions
import java.io.Serializable

class ReverseNaturalOrdering extends Ordering<Comparable<?>> implements Serializable {
    public static val INSTANCE = ReverseNaturalOrdering()

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    fun compare(Comparable<?> comparable, Comparable<?> comparable2) {
        Preconditions.checkNotNull(comparable)
        if (comparable == comparable2) {
            return 0
        }
        return comparable2.compareTo(comparable)
    }

    @Override // com.google.common.collect.Ordering
    public <S extends Comparable<?>> Ordering<S> reverse() {
        return Ordering.natural()
    }

    fun toString() {
        return "Ordering.natural().reverse()"
    }
}

package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.Comparator
import java.util.SortedSet

class SortedIterables {
    public static <E> Comparator<? super E> comparator(SortedSet<E> sortedSet) {
        Comparator<? super E> comparator = sortedSet.comparator()
        return comparator == null ? Ordering.natural() : comparator
    }

    fun hasSameComparator(Comparator<?> comparator, Iterable<?> iterable) {
        Comparator comparator2
        Preconditions.checkNotNull(comparator)
        Preconditions.checkNotNull(iterable)
        if (iterable is SortedSet) {
            comparator2 = comparator((SortedSet) iterable)
        } else {
            if (!(iterable is SortedIterable)) {
                return false
            }
            comparator2 = ((SortedIterable) iterable).comparator()
        }
        return comparator.equals(comparator2)
    }
}

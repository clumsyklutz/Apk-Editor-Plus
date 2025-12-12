package org.jf.util

import com.google.common.base.Predicate
import com.google.common.collect.ImmutableSortedSet
import com.google.common.collect.Ordering
import com.google.common.primitives.Ints
import java.util.Collection
import java.util.Comparator
import java.util.Iterator
import java.util.NoSuchElementException
import java.util.SortedSet

class CollectionUtils {
    public static <T> Int compareAsIterable(Comparator<? super T> comparator, Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        Iterator<? extends T> it = iterable2.iterator()
        Iterator<? extends T> it2 = iterable.iterator()
        while (it2.hasNext()) {
            try {
                Int iCompare = comparator.compare(it2.next(), it.next())
                if (iCompare != 0) {
                    return iCompare
                }
            } catch (NoSuchElementException unused) {
                return 1
            }
        }
        return it.hasNext() ? -1 : 0
    }

    public static <T extends Comparable<? super T>> Int compareAsList(Collection<? extends T> collection, Collection<? extends T> collection2) {
        Int iCompare = Ints.compare(collection.size(), collection2.size())
        if (iCompare != 0) {
            return iCompare
        }
        Iterator<? extends T> it = collection2.iterator()
        Iterator<? extends T> it2 = collection.iterator()
        while (it2.hasNext()) {
            Int iCompareTo = it2.next().compareTo(it.next())
            if (iCompareTo != 0) {
                return iCompareTo
            }
        }
        return 0
    }

    public static <T extends Comparable<T>> Int compareAsSet(Collection<? extends T> collection, Collection<? extends T> collection2) {
        Int iCompare = Ints.compare(collection.size(), collection2.size())
        if (iCompare != 0) {
            return iCompare
        }
        toNaturalSortedSet(collection)
        toNaturalSortedSet(collection2)
        Iterator<? extends T> it = collection2.iterator()
        Iterator<? extends T> it2 = collection.iterator()
        while (it2.hasNext()) {
            Int iCompareTo = it2.next().compareTo(it.next())
            if (iCompareTo != 0) {
                return iCompareTo
            }
        }
        return 0
    }

    public static <T> Boolean isNaturalSortedSet(Iterable<? extends T> iterable) {
        if (!(iterable is SortedSet)) {
            return false
        }
        Comparator comparator = ((SortedSet) iterable).comparator()
        return comparator == null || comparator.equals(Ordering.natural())
    }

    public static <T> Int lastIndexOf(Iterable<T> iterable, Predicate<? super T> predicate) {
        Iterator<T> it = iterable.iterator()
        Int i = 0
        Int i2 = -1
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                i2 = i
            }
            i++
        }
        return i2
    }

    public static <T> SortedSet<? extends T> toNaturalSortedSet(Collection<? extends T> collection) {
        return isNaturalSortedSet(collection) ? (SortedSet) collection : ImmutableSortedSet.copyOf((Collection) collection)
    }
}

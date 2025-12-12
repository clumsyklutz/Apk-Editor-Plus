package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.AbstractSet
import java.util.Collection
import java.util.HashSet
import java.util.Iterator
import java.util.Set

class Sets {

    public static abstract class ImprovedAbstractSet<E> extends AbstractSet<E> {
        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun removeAll(Collection<?> collection) {
            return Sets.removeAllImpl(this, collection)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        fun retainAll(Collection<?> collection) {
            return super.retainAll((Collection) Preconditions.checkNotNull(collection))
        }
    }

    fun equalsImpl(Set<?> set, Object obj) {
        if (set == obj) {
            return true
        }
        if (obj is Set) {
            Set set2 = (Set) obj
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true
                    }
                }
                return false
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false
    }

    fun hashCodeImpl(Set<?> set) {
        Iterator<?> it = set.iterator()
        Int iHashCode = 0
        while (it.hasNext()) {
            Object next = it.next()
            iHashCode = ((iHashCode + (next != null ? next.hashCode() : 0)) ^ (-1)) ^ (-1)
        }
        return iHashCode
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>()
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> iterable) {
        return iterable is Collection ? new HashSet<>((Collection) iterable) : newHashSet(iterable.iterator())
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> it) {
        HashSet<E> hashSetNewHashSet = newHashSet()
        Iterators.addAll(hashSetNewHashSet, it)
        return hashSetNewHashSet
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(Int i) {
        return new HashSet<>(Maps.capacity(i))
    }

    fun removeAllImpl(Set<?> set, Collection<?> collection) {
        Preconditions.checkNotNull(collection)
        if (collection is Multiset) {
            collection = ((Multiset) collection).elementSet()
        }
        return (!(collection is Set) || collection.size() <= set.size()) ? removeAllImpl(set, collection.iterator()) : Iterators.removeAll(set.iterator(), collection)
    }

    fun removeAllImpl(Set<?> set, Iterator<?> it) {
        Boolean zRemove = false
        while (it.hasNext()) {
            zRemove |= set.remove(it.next())
        }
        return zRemove
    }
}

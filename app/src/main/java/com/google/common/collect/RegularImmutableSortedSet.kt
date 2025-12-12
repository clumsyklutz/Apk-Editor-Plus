package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.Collection
import java.util.Collections
import java.util.Comparator
import java.util.Iterator
import java.util.NoSuchElementException
import java.util.Set

class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    public static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet<>(ImmutableList.of(), Ordering.natural())
    public final transient ImmutableList<E> elements

    constructor(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator)
        this.elements = immutableList
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        return this.elements
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    fun ceiling(E e) {
        Int iTailIndex = tailIndex(e, true)
        if (iTailIndex == size()) {
            return null
        }
        return this.elements.get(iTailIndex)
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    fun contains(Object obj) {
        if (obj == null) {
            return false
        }
        try {
            return unsafeBinarySearch(obj) >= 0
        } catch (ClassCastException unused) {
            return false
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun containsAll(Collection<?> collection) {
        if (collection is Multiset) {
            collection = ((Multiset) collection).elementSet()
        }
        if (!SortedIterables.hasSameComparator(comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection)
        }
        UnmodifiableIterator<E> it = iterator()
        Iterator<?> it2 = collection.iterator()
        if (!it.hasNext()) {
            return false
        }
        Object next = it2.next()
        E next2 = it.next()
        while (true) {
            try {
                Int iUnsafeCompare = unsafeCompare(next2, next)
                if (iUnsafeCompare < 0) {
                    if (!it.hasNext()) {
                        return false
                    }
                    next2 = it.next()
                } else if (iUnsafeCompare == 0) {
                    if (!it2.hasNext()) {
                        return true
                    }
                    next = it2.next()
                } else if (iUnsafeCompare > 0) {
                    break
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun copyIntoArray(Array<Object> objArr, Int i) {
        return this.elements.copyIntoArray(objArr, i)
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> createDescendingSet() {
        Comparator comparatorReverseOrder = Collections.reverseOrder(this.comparator)
        return isEmpty() ? ImmutableSortedSet.emptySet(comparatorReverseOrder) : RegularImmutableSortedSet(this.elements.reverse(), comparatorReverseOrder)
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator()
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    fun equals(Object obj) {
        if (obj == this) {
            return true
        }
        if (!(obj is Set)) {
            return false
        }
        Set set = (Set) obj
        if (size() != set.size()) {
            return false
        }
        if (isEmpty()) {
            return true
        }
        if (!SortedIterables.hasSameComparator(this.comparator, set)) {
            return containsAll(set)
        }
        Iterator<E> it = set.iterator()
        try {
            UnmodifiableIterator<E> it2 = iterator()
            while (it2.hasNext()) {
                E next = it2.next()
                E next2 = it.next()
                if (next2 == null || unsafeCompare(next, next2) != 0) {
                    return false
                }
            }
            return true
        } catch (ClassCastException | NoSuchElementException unused) {
            return false
        }
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    fun first() {
        if (isEmpty()) {
            throw NoSuchElementException()
        }
        return this.elements.get(0)
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    fun floor(E e) {
        Int iHeadIndex = headIndex(e, true) - 1
        if (iHeadIndex == -1) {
            return null
        }
        return this.elements.get(iHeadIndex)
    }

    public RegularImmutableSortedSet<E> getSubSet(Int i, Int i2) {
        return (i == 0 && i2 == size()) ? this : i < i2 ? new RegularImmutableSortedSet<>(this.elements.subList(i, i2), this.comparator) : ImmutableSortedSet.emptySet(this.comparator)
    }

    fun headIndex(E e, Boolean z) {
        Int iBinarySearch = Collections.binarySearch(this.elements, Preconditions.checkNotNull(e), comparator())
        return iBinarySearch >= 0 ? z ? iBinarySearch + 1 : iBinarySearch : iBinarySearch ^ (-1)
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> headSetImpl(E e, Boolean z) {
        return getSubSet(0, headIndex(e, z))
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    fun higher(E e) {
        Int iTailIndex = tailIndex(e, false)
        if (iTailIndex == size()) {
            return null
        }
        return this.elements.get(iTailIndex)
    }

    fun indexOf(Object obj) {
        if (obj == null) {
            return -1
        }
        try {
            Int iBinarySearch = Collections.binarySearch(this.elements, obj, unsafeComparator())
            if (iBinarySearch >= 0) {
                return iBinarySearch
            }
            return -1
        } catch (ClassCastException unused) {
            return -1
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Array<Object> internalArray() {
        return this.elements.internalArray()
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun internalArrayEnd() {
        return this.elements.internalArrayEnd()
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun internalArrayStart() {
        return this.elements.internalArrayStart()
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun isPartialView() {
        return this.elements.isPartialView()
    }

    @Override // com.google.common.collect.ImmutableSortedSet, com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator()
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.SortedSet
    fun last() {
        if (isEmpty()) {
            throw NoSuchElementException()
        }
        return this.elements.get(size() - 1)
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    fun lower(E e) {
        Int iHeadIndex = headIndex(e, false) - 1
        if (iHeadIndex == -1) {
            return null
        }
        return this.elements.get(iHeadIndex)
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun size() {
        return this.elements.size()
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> subSetImpl(E e, Boolean z, E e2, Boolean z2) {
        return tailSetImpl(e, z).headSetImpl(e2, z2)
    }

    fun tailIndex(E e, Boolean z) {
        Int iBinarySearch = Collections.binarySearch(this.elements, Preconditions.checkNotNull(e), comparator())
        return iBinarySearch >= 0 ? z ? iBinarySearch : iBinarySearch + 1 : iBinarySearch ^ (-1)
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    public ImmutableSortedSet<E> tailSetImpl(E e, Boolean z) {
        return getSubSet(tailIndex(e, z), size())
    }

    public final Int unsafeBinarySearch(Object obj) throws ClassCastException {
        return Collections.binarySearch(this.elements, obj, unsafeComparator())
    }

    public Comparator<Object> unsafeComparator() {
        return this.comparator
    }
}

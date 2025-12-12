package com.google.common.collect

import a.a.a.a
import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableCollection
import com.google.common.collect.ImmutableSet
import java.util.Arrays
import java.util.Collection
import java.util.Comparator
import java.util.Iterator
import java.util.NavigableSet
import java.util.SortedSet

abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements NavigableSet<E>, SortedIterable<E> {
    public final transient Comparator<? super E> comparator
    public transient ImmutableSortedSet<E> descendingSet

    public static final class Builder<E> extends ImmutableSet.Builder<E> {
        public final Comparator<? super E> comparator

        constructor(Comparator<? super E> comparator) {
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator)
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj)
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableSet.Builder add(Object obj) {
            return add((Builder<E>) obj)
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E e) {
            super.add((Builder<E>) e)
            return this
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll((Iterable) iterable)
            return this
        }

        @Override // com.google.common.collect.ImmutableSet.Builder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it)
            return this
        }

        @Override // com.google.common.collect.ImmutableSet.Builder
        public ImmutableSortedSet<E> build() {
            ImmutableSortedSet<E> immutableSortedSetConstruct = ImmutableSortedSet.construct(this.comparator, this.size, this.contents)
            this.size = immutableSortedSetConstruct.size()
            this.forceCopy = true
            return immutableSortedSetConstruct
        }
    }

    constructor(Comparator<? super E> comparator) {
        this.comparator = comparator
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <E> ImmutableSortedSet<E> construct(Comparator<? super E> comparator, Int i, E... eArr) {
        if (i == 0) {
            return emptySet(comparator)
        }
        ObjectArrays.checkElementsNotNull(eArr, i)
        Arrays.sort(eArr, 0, i, comparator)
        Int i2 = 1
        for (Int i3 = 1; i3 < i; i3++) {
            a aVar = (Object) eArr[i3]
            if (comparator.compare(aVar, (Object) eArr[i2 - 1]) != 0) {
                eArr[i2] = aVar
                i2++
            }
        }
        Arrays.fill(eArr, i2, i, (Object) null)
        if (i2 < eArr.length / 2) {
            eArr = (Array<E>) Arrays.copyOf(eArr, i2)
        }
        return RegularImmutableSortedSet(ImmutableList.asImmutableList(eArr, i2), comparator)
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> iterable) {
        return copyOf(Ordering.natural(), iterable)
    }

    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> collection) {
        return copyOf((Comparator) Ordering.natural(), (Collection) collection)
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(comparator)
        if (SortedIterables.hasSameComparator(comparator, iterable) && (iterable is ImmutableSortedSet)) {
            ImmutableSortedSet<E> immutableSortedSet = (ImmutableSortedSet) iterable
            if (!immutableSortedSet.isPartialView()) {
                return immutableSortedSet
            }
        }
        Array<Object> array = Iterables.toArray(iterable)
        return construct(comparator, array.length, array)
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> collection) {
        return copyOf((Comparator) comparator, (Iterable) collection)
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> it) {
        return Builder(comparator).addAll((Iterator) it).build()
    }

    public static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> comparator) {
        return Ordering.natural().equals(comparator) ? (RegularImmutableSortedSet<E>) RegularImmutableSortedSet.NATURAL_EMPTY_SET : new RegularImmutableSortedSet<>(ImmutableList.of(), comparator)
    }

    public static <E> ImmutableSortedSet<E> of() {
        return RegularImmutableSortedSet.NATURAL_EMPTY_SET
    }

    fun unsafeCompare(Comparator<?> comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2)
    }

    @Override // java.util.NavigableSet
    fun ceiling(E e) {
        return (E) Iterables.getFirst(tailSet((ImmutableSortedSet<E>) e, true), null)
    }

    @Override // java.util.SortedSet, com.google.common.collect.SortedIterable
    public Comparator<? super E> comparator() {
        return this.comparator
    }

    public abstract ImmutableSortedSet<E> createDescendingSet()

    @Override // java.util.NavigableSet
    public abstract UnmodifiableIterator<E> descendingIterator()

    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet = this.descendingSet
        if (immutableSortedSet != null) {
            return immutableSortedSet
        }
        ImmutableSortedSet<E> immutableSortedSetCreateDescendingSet = createDescendingSet()
        this.descendingSet = immutableSortedSetCreateDescendingSet
        immutableSortedSetCreateDescendingSet.descendingSet = this
        return immutableSortedSetCreateDescendingSet
    }

    @Override // java.util.SortedSet
    fun first() {
        return iterator().next()
    }

    @Override // java.util.NavigableSet
    fun floor(E e) {
        return (E) Iterators.getNext(headSet((ImmutableSortedSet<E>) e, true).descendingIterator(), null)
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public ImmutableSortedSet<E> headSet(E e) {
        return headSet((ImmutableSortedSet<E>) e, false)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> headSet(E e, Boolean z) {
        return headSetImpl(Preconditions.checkNotNull(e), z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet headSet(Object obj, Boolean z) {
        return headSet((ImmutableSortedSet<E>) obj, z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ SortedSet headSet(Object obj) {
        return headSet((ImmutableSortedSet<E>) obj)
    }

    public abstract ImmutableSortedSet<E> headSetImpl(E e, Boolean z)

    @Override // java.util.NavigableSet
    fun higher(E e) {
        return (E) Iterables.getFirst(tailSet((ImmutableSortedSet<E>) e, false), null)
    }

    @Override // com.google.common.collect.ImmutableSet, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return iterator()
    }

    @Override // java.util.SortedSet
    fun last() {
        return descendingIterator().next()
    }

    @Override // java.util.NavigableSet
    fun lower(E e) {
        return (E) Iterators.getNext(headSet((ImmutableSortedSet<E>) e, false).descendingIterator(), null)
    }

    @Override // java.util.NavigableSet
    @Deprecated
    public final E pollFirst() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.NavigableSet
    @Deprecated
    public final E pollLast() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.NavigableSet, java.util.SortedSet
    public ImmutableSortedSet<E> subSet(E e, E e2) {
        return subSet((Boolean) e, true, (Boolean) e2, false)
    }

    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> subSet(E e, Boolean z, E e2, Boolean z2) {
        Preconditions.checkNotNull(e)
        Preconditions.checkNotNull(e2)
        Preconditions.checkArgument(this.comparator.compare(e, e2) <= 0)
        return subSetImpl(e, z, e2, z2)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet subSet(Object obj, Boolean z, Object obj2, Boolean z2) {
        return subSet((Boolean) obj, z, (Boolean) obj2, z2)
    }

    public abstract ImmutableSortedSet<E> subSetImpl(E e, Boolean z, E e2, Boolean z2)

    @Override // java.util.NavigableSet, java.util.SortedSet
    public ImmutableSortedSet<E> tailSet(E e) {
        return tailSet((ImmutableSortedSet<E>) e, true)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public ImmutableSortedSet<E> tailSet(E e, Boolean z) {
        return tailSetImpl(Preconditions.checkNotNull(e), z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet tailSet(Object obj, Boolean z) {
        return tailSet((ImmutableSortedSet<E>) obj, z)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ SortedSet tailSet(Object obj) {
        return tailSet((ImmutableSortedSet<E>) obj)
    }

    public abstract ImmutableSortedSet<E> tailSetImpl(E e, Boolean z)

    fun unsafeCompare(Object obj, Object obj2) {
        return unsafeCompare(this.comparator, obj, obj2)
    }
}

package com.google.common.collect

import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableCollection
import java.util.Arrays
import java.util.Collection
import java.util.Comparator
import java.util.Iterator
import java.util.List
import java.util.ListIterator
import java.util.RandomAccess

abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess {
    public static final UnmodifiableListIterator<Object> EMPTY_ITR = Itr(RegularImmutableList.EMPTY, 0)

    public static final class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        constructor() {
            this(4)
        }

        constructor(Int i) {
            super(i)
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj)
        }

        @Override // com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E e) {
            super.add((Builder<E>) e)
            return this
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it)
            return this
        }

        public ImmutableList<E> build() {
            this.forceCopy = true
            return ImmutableList.asImmutableList(this.contents, this.size)
        }
    }

    public static class Itr<E> extends AbstractIndexedListIterator<E> {
        public final ImmutableList<E> list

        constructor(ImmutableList<E> immutableList, Int i) {
            super(immutableList.size(), i)
            this.list = immutableList
        }

        @Override // com.google.common.collect.AbstractIndexedListIterator
        fun get(Int i) {
            return this.list.get(i)
        }
    }

    public static class ReverseImmutableList<E> extends ImmutableList<E> {
        public final transient ImmutableList<E> forwardList

        constructor(ImmutableList<E> immutableList) {
            this.forwardList = immutableList
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
        fun contains(Object obj) {
            return this.forwardList.contains(obj)
        }

        @Override // java.util.List
        fun get(Int i) {
            Preconditions.checkElementIndex(i, size())
            return this.forwardList.get(reverseIndex(i))
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        fun indexOf(Object obj) {
            Int iLastIndexOf = this.forwardList.lastIndexOf(obj)
            if (iLastIndexOf >= 0) {
                return reverseIndex(iLastIndexOf)
            }
            return -1
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun isPartialView() {
            return this.forwardList.isPartialView()
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator()
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        fun lastIndexOf(Object obj) {
            Int iIndexOf = this.forwardList.indexOf(obj)
            if (iIndexOf >= 0) {
                return reverseIndex(iIndexOf)
            }
            return -1
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator()
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator(Int i) {
            return super.listIterator(i)
        }

        @Override // com.google.common.collect.ImmutableList
        public ImmutableList<E> reverse() {
            return this.forwardList
        }

        public final Int reverseIndex(Int i) {
            return (size() - 1) - i
        }

        public final Int reversePosition(Int i) {
            return size() - i
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return this.forwardList.size()
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public ImmutableList<E> subList(Int i, Int i2) {
            Preconditions.checkPositionIndexes(i, i2, size())
            return this.forwardList.subList(reversePosition(i2), reversePosition(i)).reverse()
        }
    }

    class SubList extends ImmutableList<E> {
        public final transient Int length
        public final transient Int offset

        constructor(Int i, Int i2) {
            this.offset = i
            this.length = i2
        }

        @Override // java.util.List
        fun get(Int i) {
            Preconditions.checkElementIndex(i, this.length)
            return ImmutableList.this.get(i + this.offset)
        }

        @Override // com.google.common.collect.ImmutableCollection
        public Array<Object> internalArray() {
            return ImmutableList.this.internalArray()
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun internalArrayEnd() {
            return ImmutableList.this.internalArrayStart() + this.offset + this.length
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun internalArrayStart() {
            return ImmutableList.this.internalArrayStart() + this.offset
        }

        @Override // com.google.common.collect.ImmutableCollection
        fun isPartialView() {
            return true
        }

        @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator()
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator()
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator(Int i) {
            return super.listIterator(i)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return this.length
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public ImmutableList<E> subList(Int i, Int i2) {
            Preconditions.checkPositionIndexes(i, i2, this.length)
            ImmutableList immutableList = ImmutableList.this
            Int i3 = this.offset
            return immutableList.subList(i + i3, i2 + i3)
        }
    }

    public static <E> ImmutableList<E> asImmutableList(Array<Object> objArr) {
        return asImmutableList(objArr, objArr.length)
    }

    public static <E> ImmutableList<E> asImmutableList(Array<Object> objArr, Int i) {
        return i == 0 ? of() : RegularImmutableList(objArr, i)
    }

    public static <E> ImmutableList<E> construct(Object... objArr) {
        return asImmutableList(ObjectArrays.checkElementsNotNull(objArr))
    }

    public static <E> ImmutableList<E> copyOf(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable)
        return iterable is Collection ? copyOf((Collection) iterable) : copyOf(iterable.iterator())
    }

    public static <E> ImmutableList<E> copyOf(Collection<? extends E> collection) {
        if (!(collection is ImmutableCollection)) {
            return construct(collection.toArray())
        }
        ImmutableList<E> immutableListAsList = ((ImmutableCollection) collection).asList()
        return immutableListAsList.isPartialView() ? asImmutableList(immutableListAsList.toArray()) : immutableListAsList
    }

    public static <E> ImmutableList<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of()
        }
        E next = it.next()
        return !it.hasNext() ? of((Object) next) : Builder().add((Builder) next).addAll((Iterator) it).build()
    }

    public static <E> ImmutableList<E> of() {
        return (ImmutableList<E>) RegularImmutableList.EMPTY
    }

    public static <E> ImmutableList<E> of(E e) {
        return construct(e)
    }

    public static <E> ImmutableList<E> of(E e, E e2) {
        return construct(e, e2)
    }

    public static <E> ImmutableList<E> sortedCopyOf(Comparator<? super E> comparator, Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(comparator)
        Array<Object> array = Iterables.toArray(iterable)
        ObjectArrays.checkElementsNotNull(array)
        Arrays.sort(array, comparator)
        return asImmutableList(array)
    }

    @Override // java.util.List
    @Deprecated
    public final Unit add(Int i, E e) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.List
    @Deprecated
    public final Boolean addAll(Int i, Collection<? extends E> collection) {
        throw UnsupportedOperationException()
    }

    @Override // com.google.common.collect.ImmutableCollection
    @Deprecated
    public final ImmutableList<E> asList() {
        return this
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection
    fun contains(Object obj) {
        return indexOf(obj) >= 0
    }

    @Override // com.google.common.collect.ImmutableCollection
    fun copyIntoArray(Array<Object> objArr, Int i) {
        Int size = size()
        for (Int i2 = 0; i2 < size; i2++) {
            objArr[i + i2] = get(i2)
        }
        return i + size
    }

    @Override // java.util.Collection, java.util.List
    fun equals(Object obj) {
        return Lists.equalsImpl(this, obj)
    }

    @Override // java.util.Collection, java.util.List
    fun hashCode() {
        Int size = size()
        Int iHashCode = 1
        for (Int i = 0; i < size; i++) {
            iHashCode = (((iHashCode * 31) + get(i).hashCode()) ^ (-1)) ^ (-1)
        }
        return iHashCode
    }

    @Override // java.util.List
    fun indexOf(Object obj) {
        if (obj == null) {
            return -1
        }
        return Lists.indexOfImpl(this, obj)
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator<E> iterator() {
        return listIterator()
    }

    @Override // java.util.List
    fun lastIndexOf(Object obj) {
        if (obj == null) {
            return -1
        }
        return Lists.lastIndexOfImpl(this, obj)
    }

    @Override // java.util.List
    public UnmodifiableListIterator<E> listIterator() {
        return listIterator(0)
    }

    @Override // java.util.List
    public UnmodifiableListIterator<E> listIterator(Int i) {
        Preconditions.checkPositionIndex(i, size())
        return isEmpty() ? (UnmodifiableListIterator<E>) EMPTY_ITR : Itr(this, i)
    }

    @Override // java.util.List
    @Deprecated
    public final E remove(Int i) {
        throw UnsupportedOperationException()
    }

    public ImmutableList<E> reverse() {
        return size() <= 1 ? this : ReverseImmutableList(this)
    }

    @Override // java.util.List
    @Deprecated
    public final E set(Int i, E e) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.List
    public ImmutableList<E> subList(Int i, Int i2) {
        Preconditions.checkPositionIndexes(i, i2, size())
        Int i3 = i2 - i
        return i3 == size() ? this : i3 == 0 ? of() : subListUnchecked(i, i2)
    }

    public ImmutableList<E> subListUnchecked(Int i, Int i2) {
        return SubList(i, i2 - i)
    }
}

package com.google.common.collect

import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableCollection
import java.util.Arrays
import java.util.Collection
import java.util.Iterator
import java.util.Set
import java.util.SortedSet

abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    public transient ImmutableList<E> asList

    public static class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Int hashCode
        public Array<Object> hashTable

        constructor() {
            super(4)
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableCollection.Builder
        public /* bridge */ /* synthetic */ ImmutableCollection.Builder add(Object obj) {
            return add((Builder<E>) obj)
        }

        @Override // com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> add(E e) {
            Preconditions.checkNotNull(e)
            if (this.hashTable != null && ImmutableSet.chooseTableSize(this.size) <= this.hashTable.length) {
                addDeduping(e)
                return this
            }
            this.hashTable = null
            super.add((Builder<E>) e)
            return this
        }

        @Override // com.google.common.collect.ImmutableCollection.ArrayBasedBuilder, com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            Preconditions.checkNotNull(iterable)
            if (this.hashTable != null) {
                Iterator<? extends E> it = iterable.iterator()
                while (it.hasNext()) {
                    add((Builder<E>) it.next())
                }
            } else {
                super.addAll((Iterable) iterable)
            }
            return this
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterator<? extends E> it) {
            Preconditions.checkNotNull(it)
            while (it.hasNext()) {
                add((Builder<E>) it.next())
            }
            return this
        }

        public final Unit addDeduping(E e) {
            this.hashTable.getClass()
            Int length = this.hashTable.length - 1
            Int iHashCode = e.hashCode()
            Int iSmear = Hashing.smear(iHashCode)
            while (true) {
                Int i = iSmear & length
                Array<Object> objArr = this.hashTable
                Object obj = objArr[i]
                if (obj == null) {
                    objArr[i] = e
                    this.hashCode += iHashCode
                    super.add((Builder<E>) e)
                    return
                } else if (obj.equals(e)) {
                    return
                } else {
                    iSmear = i + 1
                }
            }
        }

        public ImmutableSet<E> build() {
            ImmutableSet<E> immutableSetConstruct
            Int i = this.size
            if (i == 0) {
                return ImmutableSet.of()
            }
            if (i == 1) {
                Object obj = this.contents[0]
                obj.getClass()
                return ImmutableSet.of(obj)
            }
            if (this.hashTable == null || ImmutableSet.chooseTableSize(i) != this.hashTable.length) {
                immutableSetConstruct = ImmutableSet.construct(this.size, this.contents)
                this.size = immutableSetConstruct.size()
            } else {
                Array<Object> objArrCopyOf = ImmutableSet.shouldTrim(this.size, this.contents.length) ? Arrays.copyOf(this.contents, this.size) : this.contents
                immutableSetConstruct = new RegularImmutableSet<>(objArrCopyOf, this.hashCode, this.hashTable, r5.length - 1, this.size)
            }
            this.forceCopy = true
            this.hashTable = null
            return immutableSetConstruct
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>()
    }

    fun chooseTableSize(Int i) {
        Int iMax = Math.max(i, 2)
        if (iMax >= 751619276) {
            Preconditions.checkArgument(iMax < 1073741824, "collection too large")
            return 1073741824
        }
        Int iHighestOneBit = Integer.highestOneBit(iMax - 1) << 1
        while (true) {
            Double d = iHighestOneBit
            Double.isNaN(d)
            if (d * 0.7d >= iMax) {
                return iHighestOneBit
            }
            iHighestOneBit <<= 1
        }
    }

    public static <E> ImmutableSet<E> construct(Int i, Object... objArr) {
        if (i == 0) {
            return of()
        }
        if (i == 1) {
            Object obj = objArr[0]
            obj.getClass()
            return of(obj)
        }
        Int iChooseTableSize = chooseTableSize(i)
        Array<Object> objArr2 = new Object[iChooseTableSize]
        Int i2 = iChooseTableSize - 1
        Int i3 = 0
        Int i4 = 0
        for (Int i5 = 0; i5 < i; i5++) {
            Object objCheckElementNotNull = ObjectArrays.checkElementNotNull(objArr[i5], i5)
            Int iHashCode = objCheckElementNotNull.hashCode()
            Int iSmear = Hashing.smear(iHashCode)
            while (true) {
                Int i6 = iSmear & i2
                Object obj2 = objArr2[i6]
                if (obj2 == null) {
                    objArr[i4] = objCheckElementNotNull
                    objArr2[i6] = objCheckElementNotNull
                    i3 += iHashCode
                    i4++
                    break
                }
                if (obj2.equals(objCheckElementNotNull)) {
                    break
                }
                iSmear++
            }
        }
        Arrays.fill(objArr, i4, i, (Object) null)
        if (i4 == 1) {
            Object obj3 = objArr[0]
            obj3.getClass()
            return SingletonImmutableSet(obj3)
        }
        if (chooseTableSize(i4) < iChooseTableSize / 2) {
            return construct(i4, objArr)
        }
        if (shouldTrim(i4, objArr.length)) {
            objArr = Arrays.copyOf(objArr, i4)
        }
        return RegularImmutableSet(objArr, i3, objArr2, i2, i4)
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> collection) {
        if ((collection is ImmutableSet) && !(collection is SortedSet)) {
            ImmutableSet<E> immutableSet = (ImmutableSet) collection
            if (!immutableSet.isPartialView()) {
                return immutableSet
            }
        }
        Array<Object> array = collection.toArray()
        return construct(array.length, array)
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return of()
        }
        E next = it.next()
        return !it.hasNext() ? of((Object) next) : Builder().add((Builder) next).addAll((Iterator) it).build()
    }

    public static <E> ImmutableSet<E> of() {
        return RegularImmutableSet.EMPTY
    }

    public static <E> ImmutableSet<E> of(E e) {
        return SingletonImmutableSet(e)
    }

    fun shouldTrim(Int i, Int i2) {
        return i < (i2 >> 1) + (i2 >> 2)
    }

    @Override // com.google.common.collect.ImmutableCollection
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList = this.asList
        if (immutableList != null) {
            return immutableList
        }
        ImmutableList<E> immutableListCreateAsList = createAsList()
        this.asList = immutableListCreateAsList
        return immutableListCreateAsList
    }

    public ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(toArray())
    }

    @Override // java.util.Collection, java.util.Set
    fun equals(Object obj) {
        if (obj == this) {
            return true
        }
        if ((obj is ImmutableSet) && isHashCodeFast() && ((ImmutableSet) obj).isHashCodeFast() && hashCode() != obj.hashCode()) {
            return false
        }
        return Sets.equalsImpl(this, obj)
    }

    @Override // java.util.Collection, java.util.Set
    fun hashCode() {
        return Sets.hashCodeImpl(this)
    }

    fun isHashCodeFast() {
        return false
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return iterator()
    }
}

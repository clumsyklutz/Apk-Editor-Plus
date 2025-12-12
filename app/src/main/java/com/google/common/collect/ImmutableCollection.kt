package com.google.common.collect

import android.support.v7.widget.ActivityChooserView
import com.google.common.base.Preconditions
import java.io.Serializable
import java.util.AbstractCollection
import java.util.Arrays
import java.util.Collection
import java.util.Iterator

abstract class ImmutableCollection<E> extends AbstractCollection<E> implements Serializable {
    public static final Array<Object> EMPTY_ARRAY = new Object[0]

    public static abstract class ArrayBasedBuilder<E> extends Builder<E> {
        public Array<Object> contents
        public Boolean forceCopy
        public Int size

        constructor(Int i) {
            CollectPreconditions.checkNonnegative(i, "initialCapacity")
            this.contents = new Object[i]
            this.size = 0
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public ArrayBasedBuilder<E> add(E e) {
            Preconditions.checkNotNull(e)
            getReadyToExpandTo(this.size + 1)
            Array<Object> objArr = this.contents
            Int i = this.size
            this.size = i + 1
            objArr[i] = e
            return this
        }

        @Override // com.google.common.collect.ImmutableCollection.Builder
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable is Collection) {
                Collection collection = (Collection) iterable
                getReadyToExpandTo(this.size + collection.size())
                if (collection is ImmutableCollection) {
                    this.size = ((ImmutableCollection) collection).copyIntoArray(this.contents, this.size)
                    return this
                }
            }
            super.addAll(iterable)
            return this
        }

        public final Unit getReadyToExpandTo(Int i) {
            Array<Object> objArr = this.contents
            if (objArr.length < i) {
                this.contents = Arrays.copyOf(objArr, Builder.expandedCapacity(objArr.length, i))
                this.forceCopy = false
            } else if (this.forceCopy) {
                this.contents = (Array<Object>) objArr.clone()
                this.forceCopy = false
            }
        }
    }

    public static abstract class Builder<E> {
        fun expandedCapacity(Int i, Int i2) {
            if (i2 < 0) {
                throw AssertionError("cannot store more than MAX_VALUE elements")
            }
            Int iHighestOneBit = i + (i >> 1) + 1
            if (iHighestOneBit < i2) {
                iHighestOneBit = Integer.highestOneBit(i2 - 1) << 1
            }
            return iHighestOneBit < 0 ? ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : iHighestOneBit
        }

        public abstract Builder<E> add(E e)

        public Builder<E> addAll(Iterable<? extends E> iterable) {
            Iterator<? extends E> it = iterable.iterator()
            while (it.hasNext()) {
                add(it.next())
            }
            return this
        }

        public Builder<E> addAll(Iterator<? extends E> it) {
            while (it.hasNext()) {
                add(it.next())
            }
            return this
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final Boolean add(E e) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final Boolean addAll(Collection<? extends E> collection) {
        throw UnsupportedOperationException()
    }

    public ImmutableList<E> asList() {
        return isEmpty() ? ImmutableList.of() : ImmutableList.asImmutableList(toArray())
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final Unit clear() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public abstract Boolean contains(Object obj)

    fun copyIntoArray(Array<Object> objArr, Int i) {
        UnmodifiableIterator<E> it = iterator()
        while (it.hasNext()) {
            objArr[i] = it.next()
            i++
        }
        return i
    }

    public Array<Object> internalArray() {
        return null
    }

    fun internalArrayEnd() {
        throw UnsupportedOperationException()
    }

    fun internalArrayStart() {
        throw UnsupportedOperationException()
    }

    public abstract Boolean isPartialView()

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public abstract UnmodifiableIterator<E> iterator()

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final Boolean remove(Object obj) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final Boolean removeAll(Collection<?> collection) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    @Deprecated
    public final Boolean retainAll(Collection<?> collection) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Array<Object> toArray() {
        return toArray(EMPTY_ARRAY)
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final <T> Array<T> toArray(Array<T> tArr) {
        Preconditions.checkNotNull(tArr)
        Int size = size()
        if (tArr.length < size) {
            Array<Object> objArrInternalArray = internalArray()
            if (objArrInternalArray != null) {
                return (Array<T>) Platform.copy(objArrInternalArray, internalArrayStart(), internalArrayEnd(), tArr)
            }
            tArr = (Array<T>) ObjectArrays.newArray(tArr, size)
        } else if (tArr.length > size) {
            tArr[size] = null
        }
        copyIntoArray(tArr, 0)
        return tArr
    }
}

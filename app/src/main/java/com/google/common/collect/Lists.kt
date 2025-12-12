package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Objects
import com.google.common.base.Preconditions
import com.google.common.primitives.Ints
import java.io.Serializable
import java.util.AbstractList
import java.util.AbstractSequentialList
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.ListIterator
import java.util.NoSuchElementException
import java.util.RandomAccess

class Lists {

    public static class RandomAccessReverseList<T> extends ReverseList<T> implements RandomAccess {
        constructor(List<T> list) {
            super(list)
        }
    }

    public static class ReverseList<T> extends AbstractList<T> {
        public final List<T> forwardList

        constructor(List<T> list) {
            this.forwardList = (List) Preconditions.checkNotNull(list)
        }

        private fun reverseIndex(Int i) {
            Int size = size()
            Preconditions.checkElementIndex(i, size)
            return (size - 1) - i
        }

        /* JADX INFO: Access modifiers changed from: private */
        fun reversePosition(Int i) {
            Int size = size()
            Preconditions.checkPositionIndex(i, size)
            return size - i
        }

        @Override // java.util.AbstractList, java.util.List
        fun add(Int i, T t) {
            this.forwardList.add(reversePosition(i), t)
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        fun clear() {
            this.forwardList.clear()
        }

        @Override // java.util.AbstractList, java.util.List
        fun get(Int i) {
            return this.forwardList.get(reverseIndex(i))
        }

        public List<T> getForwardList() {
            return this.forwardList
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            return listIterator()
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(Int i) {
            final ListIterator<T> listIterator = this.forwardList.listIterator(reversePosition(i))
            return new ListIterator<T>() { // from class: com.google.common.collect.Lists.ReverseList.1
                public Boolean canRemoveOrSet

                @Override // java.util.ListIterator
                fun add(T t) {
                    listIterator.add(t)
                    listIterator.previous()
                    this.canRemoveOrSet = false
                }

                @Override // java.util.ListIterator, java.util.Iterator
                fun hasNext() {
                    return listIterator.hasPrevious()
                }

                @Override // java.util.ListIterator
                fun hasPrevious() {
                    return listIterator.hasNext()
                }

                @Override // java.util.ListIterator, java.util.Iterator
                fun next() {
                    if (!hasNext()) {
                        throw NoSuchElementException()
                    }
                    this.canRemoveOrSet = true
                    return (T) listIterator.previous()
                }

                @Override // java.util.ListIterator
                fun nextIndex() {
                    return ReverseList.this.reversePosition(listIterator.nextIndex())
                }

                @Override // java.util.ListIterator
                fun previous() {
                    if (!hasPrevious()) {
                        throw NoSuchElementException()
                    }
                    this.canRemoveOrSet = true
                    return (T) listIterator.next()
                }

                @Override // java.util.ListIterator
                fun previousIndex() {
                    return nextIndex() - 1
                }

                @Override // java.util.ListIterator, java.util.Iterator
                fun remove() {
                    CollectPreconditions.checkRemove(this.canRemoveOrSet)
                    listIterator.remove()
                    this.canRemoveOrSet = false
                }

                @Override // java.util.ListIterator
                fun set(T t) {
                    Preconditions.checkState(this.canRemoveOrSet)
                    listIterator.set(t)
                }
            }
        }

        @Override // java.util.AbstractList, java.util.List
        fun remove(Int i) {
            return this.forwardList.remove(reverseIndex(i))
        }

        @Override // java.util.AbstractList
        fun removeRange(Int i, Int i2) {
            subList(i, i2).clear()
        }

        @Override // java.util.AbstractList, java.util.List
        fun set(Int i, T t) {
            return this.forwardList.set(reverseIndex(i), t)
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return this.forwardList.size()
        }

        @Override // java.util.AbstractList, java.util.List
        public List<T> subList(Int i, Int i2) {
            Preconditions.checkPositionIndexes(i, i2, size())
            return Lists.reverse(this.forwardList.subList(reversePosition(i2), reversePosition(i)))
        }
    }

    public static class TransformingRandomAccessList<F, T> extends AbstractList<T> implements RandomAccess, Serializable {
        public final List<F> fromList
        public final Function<? super F, ? extends T> function

        constructor(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = (List) Preconditions.checkNotNull(list)
            this.function = (Function) Preconditions.checkNotNull(function)
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        fun clear() {
            this.fromList.clear()
        }

        @Override // java.util.AbstractList, java.util.List
        fun get(Int i) {
            return this.function.apply(this.fromList.get(i))
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun isEmpty() {
            return this.fromList.isEmpty()
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
        public Iterator<T> iterator() {
            return listIterator()
        }

        @Override // java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(Int i) {
            return new TransformedListIterator<F, T>(this.fromList.listIterator(i)) { // from class: com.google.common.collect.Lists.TransformingRandomAccessList.1
                @Override // com.google.common.collect.TransformedIterator
                fun transform(F f) {
                    return TransformingRandomAccessList.this.function.apply(f)
                }
            }
        }

        @Override // java.util.AbstractList, java.util.List
        fun remove(Int i) {
            return this.function.apply(this.fromList.remove(i))
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return this.fromList.size()
        }
    }

    public static class TransformingSequentialList<F, T> extends AbstractSequentialList<T> implements Serializable {
        public final List<F> fromList
        public final Function<? super F, ? extends T> function

        constructor(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = (List) Preconditions.checkNotNull(list)
            this.function = (Function) Preconditions.checkNotNull(function)
        }

        @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
        fun clear() {
            this.fromList.clear()
        }

        @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
        public ListIterator<T> listIterator(Int i) {
            return new TransformedListIterator<F, T>(this.fromList.listIterator(i)) { // from class: com.google.common.collect.Lists.TransformingSequentialList.1
                @Override // com.google.common.collect.TransformedIterator
                fun transform(F f) {
                    return TransformingSequentialList.this.function.apply(f)
                }
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        fun size() {
            return this.fromList.size()
        }
    }

    fun computeArrayListCapacity(Int i) {
        CollectPreconditions.checkNonnegative(i, "arraySize")
        return Ints.saturatedCast(i + 5 + (i / 10))
    }

    fun equalsImpl(List<?> list, Object obj) {
        if (obj == Preconditions.checkNotNull(list)) {
            return true
        }
        if (!(obj is List)) {
            return false
        }
        List list2 = (List) obj
        Int size = list.size()
        if (size != list2.size()) {
            return false
        }
        if (!(list is RandomAccess) || !(list2 is RandomAccess)) {
            return Iterators.elementsEqual(list.iterator(), list2.iterator())
        }
        for (Int i = 0; i < size; i++) {
            if (!Objects.equal(list.get(i), list2.get(i))) {
                return false
            }
        }
        return true
    }

    fun indexOfImpl(List<?> list, Object obj) {
        if (list is RandomAccess) {
            return indexOfRandomAccess(list, obj)
        }
        ListIterator<?> listIterator = list.listIterator()
        while (listIterator.hasNext()) {
            if (Objects.equal(obj, listIterator.next())) {
                return listIterator.previousIndex()
            }
        }
        return -1
    }

    fun indexOfRandomAccess(List<?> list, Object obj) {
        Int size = list.size()
        Int i = 0
        if (obj == null) {
            while (i < size) {
                if (list.get(i) == null) {
                    return i
                }
                i++
            }
            return -1
        }
        while (i < size) {
            if (obj.equals(list.get(i))) {
                return i
            }
            i++
        }
        return -1
    }

    fun lastIndexOfImpl(List<?> list, Object obj) {
        if (list is RandomAccess) {
            return lastIndexOfRandomAccess(list, obj)
        }
        ListIterator<?> listIterator = list.listIterator(list.size())
        while (listIterator.hasPrevious()) {
            if (Objects.equal(obj, listIterator.previous())) {
                return listIterator.nextIndex()
            }
        }
        return -1
    }

    fun lastIndexOfRandomAccess(List<?> list, Object obj) {
        if (obj == null) {
            for (Int size = list.size() - 1; size >= 0; size--) {
                if (list.get(size) == null) {
                    return size
                }
            }
            return -1
        }
        for (Int size2 = list.size() - 1; size2 >= 0; size2--) {
            if (obj.equals(list.get(size2))) {
                return size2
            }
        }
        return -1
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>()
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable)
        return iterable is Collection ? new ArrayList<>((Collection) iterable) : newArrayList(iterable.iterator())
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> it) {
        ArrayList<E> arrayListNewArrayList = newArrayList()
        Iterators.addAll(arrayListNewArrayList, it)
        return arrayListNewArrayList
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... eArr) {
        Preconditions.checkNotNull(eArr)
        ArrayList<E> arrayList = new ArrayList<>(computeArrayListCapacity(eArr.length))
        Collections.addAll(arrayList, eArr)
        return arrayList
    }

    public static <T> List<T> reverse(List<T> list) {
        return list is ImmutableList ? ((ImmutableList) list).reverse() : list is ReverseList ? ((ReverseList) list).getForwardList() : list is RandomAccess ? RandomAccessReverseList(list) : ReverseList(list)
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        return list is RandomAccess ? TransformingRandomAccessList(list, function) : TransformingSequentialList(list, function)
    }
}

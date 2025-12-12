package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Objects
import com.google.common.base.Preconditions
import com.google.common.base.Predicate
import com.google.common.primitives.Ints
import java.util.ArrayDeque
import java.util.Collection
import java.util.Comparator
import java.util.Deque
import java.util.Iterator
import java.util.ListIterator
import java.util.NoSuchElementException
import java.util.PriorityQueue
import java.util.Queue

class Iterators {

    public static final class ArrayItr<T> extends AbstractIndexedListIterator<T> {
        public static final UnmodifiableListIterator<Object> EMPTY = ArrayItr(new Object[0], 0, 0, 0)
        public final Array<T> array
        public final Int offset

        constructor(Array<T> tArr, Int i, Int i2, Int i3) {
            super(i2, i3)
            this.array = tArr
            this.offset = i
        }

        @Override // com.google.common.collect.AbstractIndexedListIterator
        fun get(Int i) {
            return this.array[this.offset + i]
        }
    }

    public static class ConcatenatedIterator<T> implements Iterator<T> {
        public Iterator<? extends T> iterator = Iterators.emptyIterator()
        public Deque<Iterator<? extends Iterator<? extends T>>> metaIterators
        public Iterator<? extends T> toRemove
        public Iterator<? extends Iterator<? extends T>> topMetaIterator

        constructor(Iterator<? extends Iterator<? extends T>> it) {
            this.topMetaIterator = (Iterator) Preconditions.checkNotNull(it)
        }

        public final Iterator<? extends Iterator<? extends T>> getTopMetaIterator() {
            while (true) {
                Iterator<? extends Iterator<? extends T>> it = this.topMetaIterator
                if (it != null && it.hasNext()) {
                    return this.topMetaIterator
                }
                Deque<Iterator<? extends Iterator<? extends T>>> deque = this.metaIterators
                if (deque == null || deque.isEmpty()) {
                    return null
                }
                this.topMetaIterator = this.metaIterators.removeFirst()
            }
        }

        @Override // java.util.Iterator
        fun hasNext() {
            while (!((Iterator) Preconditions.checkNotNull(this.iterator)).hasNext()) {
                Iterator<? extends Iterator<? extends T>> topMetaIterator = getTopMetaIterator()
                this.topMetaIterator = topMetaIterator
                if (topMetaIterator == null) {
                    return false
                }
                Iterator<? extends T> next = topMetaIterator.next()
                this.iterator = next
                if (next is ConcatenatedIterator) {
                    ConcatenatedIterator concatenatedIterator = (ConcatenatedIterator) next
                    this.iterator = concatenatedIterator.iterator
                    if (this.metaIterators == null) {
                        this.metaIterators = ArrayDeque()
                    }
                    this.metaIterators.addFirst(this.topMetaIterator)
                    if (concatenatedIterator.metaIterators != null) {
                        while (!concatenatedIterator.metaIterators.isEmpty()) {
                            this.metaIterators.addFirst(concatenatedIterator.metaIterators.removeLast())
                        }
                    }
                    this.topMetaIterator = concatenatedIterator.topMetaIterator
                }
            }
            return true
        }

        @Override // java.util.Iterator
        fun next() {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            Iterator<? extends T> it = this.iterator
            this.toRemove = it
            return it.next()
        }

        @Override // java.util.Iterator
        fun remove() {
            Iterator<? extends T> it = this.toRemove
            if (it == null) {
                throw IllegalStateException("no calls to next() since the last call to remove()")
            }
            it.remove()
            this.toRemove = null
        }
    }

    public static class MergingIterator<T> extends UnmodifiableIterator<T> {
        public final Queue<PeekingIterator<T>> queue

        constructor(Iterable<? extends Iterator<? extends T>> iterable, final Comparator<? super T> comparator) {
            this.queue = PriorityQueue(2, new Comparator<PeekingIterator<T>>(this) { // from class: com.google.common.collect.Iterators.MergingIterator.1
                @Override // java.util.Comparator
                fun compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return comparator.compare(peekingIterator.peek(), peekingIterator2.peek())
                }
            })
            for (Iterator<? extends T> it : iterable) {
                if (it.hasNext()) {
                    this.queue.add(Iterators.peekingIterator(it))
                }
            }
        }

        @Override // java.util.Iterator
        fun hasNext() {
            return !this.queue.isEmpty()
        }

        @Override // java.util.Iterator
        fun next() {
            PeekingIterator<T> peekingIteratorRemove = this.queue.remove()
            T next = peekingIteratorRemove.next()
            if (peekingIteratorRemove.hasNext()) {
                this.queue.add(peekingIteratorRemove)
            }
            return next
        }
    }

    public static class PeekingImpl<E> implements PeekingIterator<E> {
        public Boolean hasPeeked
        public final Iterator<? extends E> iterator
        public E peekedElement

        constructor(Iterator<? extends E> it) {
            this.iterator = (Iterator) Preconditions.checkNotNull(it)
        }

        @Override // java.util.Iterator
        fun hasNext() {
            return this.hasPeeked || this.iterator.hasNext()
        }

        @Override // com.google.common.collect.PeekingIterator, java.util.Iterator
        fun next() {
            if (!this.hasPeeked) {
                return this.iterator.next()
            }
            E e = (E) NullnessCasts.uncheckedCastNullableTToT(this.peekedElement)
            this.hasPeeked = false
            this.peekedElement = null
            return e
        }

        @Override // com.google.common.collect.PeekingIterator
        fun peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next()
                this.hasPeeked = true
            }
            return (E) NullnessCasts.uncheckedCastNullableTToT(this.peekedElement)
        }

        @Override // java.util.Iterator
        fun remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next")
            this.iterator.remove()
        }
    }

    public static <T> Boolean addAll(Collection<T> collection, Iterator<? extends T> it) {
        Preconditions.checkNotNull(collection)
        Preconditions.checkNotNull(it)
        Boolean zAdd = false
        while (it.hasNext()) {
            zAdd |= collection.add(it.next())
        }
        return zAdd
    }

    public static <T> Boolean any(Iterator<T> it, Predicate<? super T> predicate) {
        return indexOf(it, predicate) != -1
    }

    public static <T> ListIterator<T> cast(Iterator<T> it) {
        return (ListIterator) it
    }

    fun clear(Iterator<?> it) {
        Preconditions.checkNotNull(it)
        while (it.hasNext()) {
            it.next()
            it.remove()
        }
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> it) {
        return ConcatenatedIterator(it)
    }

    fun elementsEqual(Iterator<?> it, Iterator<?> it2) {
        while (it.hasNext()) {
            if (!it2.hasNext() || !Objects.equal(it.next(), it2.next())) {
                return false
            }
        }
        return !it2.hasNext()
    }

    public static <T> UnmodifiableIterator<T> emptyIterator() {
        return emptyListIterator()
    }

    public static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return (UnmodifiableListIterator<T>) ArrayItr.EMPTY
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> it, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(it)
        Preconditions.checkNotNull(predicate)
        return new AbstractIterator<T>() { // from class: com.google.common.collect.Iterators.5
            @Override // com.google.common.collect.AbstractIterator
            fun computeNext() {
                while (it.hasNext()) {
                    T t = (T) it.next()
                    if (predicate.apply(t)) {
                        return t
                    }
                }
                return endOfData()
            }
        }
    }

    public static <T> T getNext(Iterator<? extends T> it, T t) {
        return it.hasNext() ? it.next() : t
    }

    public static <T> Int indexOf(Iterator<T> it, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate")
        Int i = 0
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                return i
            }
            i++
        }
        return -1
    }

    public static <T> Iterator<T> limit(final Iterator<T> it, final Int i) {
        Preconditions.checkNotNull(it)
        Preconditions.checkArgument(i >= 0, "limit is negative")
        return new Iterator<T>() { // from class: com.google.common.collect.Iterators.7
            public Int count

            @Override // java.util.Iterator
            fun hasNext() {
                return this.count < i && it.hasNext()
            }

            @Override // java.util.Iterator
            fun next() {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                this.count++
                return (T) it.next()
            }

            @Override // java.util.Iterator
            fun remove() {
                it.remove()
            }
        }
    }

    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators")
        Preconditions.checkNotNull(comparator, "comparator")
        return MergingIterator(iterable, comparator)
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> it) {
        return it is PeekingImpl ? (PeekingImpl) it : PeekingImpl(it)
    }

    fun removeAll(Iterator<?> it, Collection<?> collection) {
        Preconditions.checkNotNull(collection)
        Boolean z = false
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove()
                z = true
            }
        }
        return z
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(final T t) {
        return new UnmodifiableIterator<T>() { // from class: com.google.common.collect.Iterators.9
            public Boolean done

            @Override // java.util.Iterator
            fun hasNext() {
                return !this.done
            }

            @Override // java.util.Iterator
            fun next() {
                if (this.done) {
                    throw NoSuchElementException()
                }
                this.done = true
                return (T) t
            }
        }
    }

    fun size(Iterator<?> it) {
        Long j = 0
        while (it.hasNext()) {
            it.next()
            j++
        }
        return Ints.saturatedCast(j)
    }

    fun toString(Iterator<?> it) {
        StringBuilder sb = StringBuilder()
        sb.append('[')
        Boolean z = true
        while (it.hasNext()) {
            if (!z) {
                sb.append(", ")
            }
            z = false
            sb.append(it.next())
        }
        sb.append(']')
        return sb.toString()
    }

    public static <F, T> Iterator<T> transform(Iterator<F> it, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function)
        return new TransformedIterator<F, T>(it) { // from class: com.google.common.collect.Iterators.6
            @Override // com.google.common.collect.TransformedIterator
            fun transform(F f) {
                return (T) function.apply(f)
            }
        }
    }
}

package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Preconditions
import com.google.common.base.Predicate
import java.util.Collection
import java.util.Iterator

class Iterables {
    public static <T> Boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.any(iterable.iterator(), predicate)
    }

    public static <E> Collection<E> castOrCopyToCollection(Iterable<E> iterable) {
        return iterable is Collection ? (Collection) iterable : Lists.newArrayList(iterable.iterator())
    }

    public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterable) {
        return FluentIterable.concat(iterable)
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return FluentIterable.concat(iterable, iterable2)
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterable)
        Preconditions.checkNotNull(predicate)
        return new FluentIterable<T>() { // from class: com.google.common.collect.Iterables.4
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.filter(iterable.iterator(), predicate)
            }
        }
    }

    public static <T> T getFirst(Iterable<? extends T> iterable, T t) {
        return (T) Iterators.getNext(iterable.iterator(), t)
    }

    fun isEmpty(Iterable<?> iterable) {
        return iterable is Collection ? ((Collection) iterable).isEmpty() : !iterable.iterator().hasNext()
    }

    public static <T> Iterable<T> limit(final Iterable<T> iterable, final Int i) {
        Preconditions.checkNotNull(iterable)
        Preconditions.checkArgument(i >= 0, "limit is negative")
        return new FluentIterable<T>() { // from class: com.google.common.collect.Iterables.7
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.limit(iterable.iterator(), i)
            }
        }
    }

    fun size(Iterable<?> iterable) {
        return iterable is Collection ? ((Collection) iterable).size() : Iterators.size(iterable.iterator())
    }

    public static Array<Object> toArray(Iterable<?> iterable) {
        return castOrCopyToCollection(iterable).toArray()
    }

    public static <T> Function<Iterable<? extends T>, Iterator<? extends T>> toIterator() {
        return new Function<Iterable<? extends T>, Iterator<? extends T>>() { // from class: com.google.common.collect.Iterables.10
            @Override // com.google.common.base.Function
            public Iterator<? extends T> apply(Iterable<? extends T> iterable) {
                return iterable.iterator()
            }
        }
    }

    fun toString(Iterable<?> iterable) {
        return Iterators.toString(iterable.iterator())
    }

    public static <F, T> Iterable<T> transform(final Iterable<F> iterable, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(iterable)
        Preconditions.checkNotNull(function)
        return new FluentIterable<T>() { // from class: com.google.common.collect.Iterables.5
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.transform(iterable.iterator(), function)
            }
        }
    }
}

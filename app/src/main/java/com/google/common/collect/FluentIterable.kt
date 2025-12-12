package com.google.common.collect

import com.google.common.base.Function
import com.google.common.base.Optional
import com.google.common.base.Preconditions
import java.util.Iterator

abstract class FluentIterable<E> implements Iterable<E> {
    public final Optional<Iterable<E>> iterableDelegate

    constructor() {
        this.iterableDelegate = Optional.absent()
    }

    constructor(Iterable<E> iterable) {
        this.iterableDelegate = Optional.of(iterable)
    }

    public static <T> FluentIterable<T> concat(final Iterable<? extends Iterable<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable)
        return new FluentIterable<T>() { // from class: com.google.common.collect.FluentIterable.2
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.concat(Iterators.transform(iterable.iterator(), Iterables.toIterator()))
            }
        }
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return concatNoDefensiveCopy(iterable, iterable2)
    }

    public static <T> FluentIterable<T> concatNoDefensiveCopy(final Iterable<? extends T>... iterableArr) {
        for (Iterable<? extends T> iterable : iterableArr) {
            Preconditions.checkNotNull(iterable)
        }
        return new FluentIterable<T>() { // from class: com.google.common.collect.FluentIterable.3
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return Iterators.concat(new AbstractIndexedListIterator<Iterator<? extends T>>(iterableArr.length) { // from class: com.google.common.collect.FluentIterable.3.1
                    @Override // com.google.common.collect.AbstractIndexedListIterator
                    public Iterator<? extends T> get(Int i) {
                        return iterableArr[i].iterator()
                    }
                })
            }
        }
    }

    public static <E> FluentIterable<E> from(final Iterable<E> iterable) {
        return iterable is FluentIterable ? (FluentIterable) iterable : new FluentIterable<E>(iterable) { // from class: com.google.common.collect.FluentIterable.1
            @Override // java.lang.Iterable
            public Iterator<E> iterator() {
                return iterable.iterator()
            }
        }
    }

    public final Iterable<E> getDelegate() {
        return this.iterableDelegate.or(this)
    }

    public final FluentIterable<E> limit(Int i) {
        return from(Iterables.limit(getDelegate(), i))
    }

    fun toString() {
        return Iterables.toString(getDelegate())
    }

    public final <T> FluentIterable<T> transform(Function<? super E, T> function) {
        return from(Iterables.transform(getDelegate(), function))
    }
}

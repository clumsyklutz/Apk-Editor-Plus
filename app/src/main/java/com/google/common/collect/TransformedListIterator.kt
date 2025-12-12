package com.google.common.collect

import java.util.ListIterator

abstract class TransformedListIterator<F, T> extends TransformedIterator<F, T> implements ListIterator<T> {
    constructor(ListIterator<? extends F> listIterator) {
        super(listIterator)
    }

    @Override // java.util.ListIterator
    fun add(T t) {
        throw UnsupportedOperationException()
    }

    public final ListIterator<? extends F> backingIterator() {
        return Iterators.cast(this.backingIterator)
    }

    @Override // java.util.ListIterator
    public final Boolean hasPrevious() {
        return backingIterator().hasPrevious()
    }

    @Override // java.util.ListIterator
    public final Int nextIndex() {
        return backingIterator().nextIndex()
    }

    @Override // java.util.ListIterator
    public final T previous() {
        return transform(backingIterator().previous())
    }

    @Override // java.util.ListIterator
    public final Int previousIndex() {
        return backingIterator().previousIndex()
    }

    @Override // java.util.ListIterator
    fun set(T t) {
        throw UnsupportedOperationException()
    }
}

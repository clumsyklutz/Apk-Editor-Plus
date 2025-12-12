package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.Iterator

abstract class TransformedIterator<F, T> implements Iterator<T> {
    public final Iterator<? extends F> backingIterator

    constructor(Iterator<? extends F> it) {
        this.backingIterator = (Iterator) Preconditions.checkNotNull(it)
    }

    @Override // java.util.Iterator
    public final Boolean hasNext() {
        return this.backingIterator.hasNext()
    }

    @Override // java.util.Iterator
    public final T next() {
        return transform(this.backingIterator.next())
    }

    @Override // java.util.Iterator
    public final Unit remove() {
        this.backingIterator.remove()
    }

    public abstract T transform(F f)
}

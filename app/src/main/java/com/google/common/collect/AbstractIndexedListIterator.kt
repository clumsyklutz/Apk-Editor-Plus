package com.google.common.collect

import com.google.common.base.Preconditions
import java.util.NoSuchElementException

abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {
    public Int position
    public final Int size

    constructor(Int i) {
        this(i, 0)
    }

    constructor(Int i, Int i2) {
        Preconditions.checkPositionIndex(i2, i)
        this.size = i
        this.position = i2
    }

    public abstract E get(Int i)

    @Override // java.util.Iterator, java.util.ListIterator
    public final Boolean hasNext() {
        return this.position < this.size
    }

    @Override // java.util.ListIterator
    public final Boolean hasPrevious() {
        return this.position > 0
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final E next() {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        Int i = this.position
        this.position = i + 1
        return get(i)
    }

    @Override // java.util.ListIterator
    public final Int nextIndex() {
        return this.position
    }

    @Override // java.util.ListIterator
    public final E previous() {
        if (!hasPrevious()) {
            throw NoSuchElementException()
        }
        Int i = this.position - 1
        this.position = i
        return get(i)
    }

    @Override // java.util.ListIterator
    public final Int previousIndex() {
        return this.position - 1
    }
}

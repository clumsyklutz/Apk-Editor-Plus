package org.jf.util

import java.util.ListIterator

abstract class AbstractListIterator<T> implements ListIterator<T> {
    @Override // java.util.ListIterator
    fun add(T t) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.ListIterator, java.util.Iterator
    fun remove() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.ListIterator
    fun set(T t) {
        throw UnsupportedOperationException()
    }
}

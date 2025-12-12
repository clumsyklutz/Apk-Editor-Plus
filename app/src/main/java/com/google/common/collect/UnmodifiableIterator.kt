package com.google.common.collect

import java.util.Iterator

abstract class UnmodifiableIterator<E> implements Iterator<E> {
    @Override // java.util.Iterator
    @Deprecated
    public final Unit remove() {
        throw UnsupportedOperationException()
    }
}

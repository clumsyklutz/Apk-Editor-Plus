package com.google.common.collect

import java.util.ListIterator

abstract class UnmodifiableListIterator<E> extends UnmodifiableIterator<E> implements ListIterator<E> {
    @Override // java.util.ListIterator
    @Deprecated
    public final Unit add(E e) {
        throw UnsupportedOperationException()
    }

    @Override // java.util.ListIterator
    @Deprecated
    public final Unit set(E e) {
        throw UnsupportedOperationException()
    }
}

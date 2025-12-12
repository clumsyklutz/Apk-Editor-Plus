package com.google.common.collect

import java.util.Iterator

public interface PeekingIterator<E> extends Iterator<E> {
    @Override // java.util.Iterator
    E next()

    E peek()
}

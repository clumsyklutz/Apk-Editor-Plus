package org.jf.dexlib2.dexbacked.util

import java.util.AbstractList

abstract class FixedSizeList<T> extends AbstractList<T> {
    @Override // java.util.AbstractList, java.util.List
    fun get(Int i) {
        if (i < 0 || i >= size()) {
            throw IndexOutOfBoundsException()
        }
        return readItem(i)
    }

    public abstract T readItem(Int i)
}

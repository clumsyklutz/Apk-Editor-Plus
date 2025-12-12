package org.jf.dexlib2.dexbacked.util

import java.util.AbstractSet
import java.util.Iterator
import java.util.NoSuchElementException

abstract class FixedSizeSet<T> extends AbstractSet<T> {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<T> iterator() {
        return new Iterator<T>() { // from class: org.jf.dexlib2.dexbacked.util.FixedSizeSet.1
            public Int index = 0

            @Override // java.util.Iterator
            fun hasNext() {
                return this.index < FixedSizeSet.this.size()
            }

            @Override // java.util.Iterator
            fun next() {
                if (!hasNext()) {
                    throw NoSuchElementException()
                }
                FixedSizeSet fixedSizeSet = FixedSizeSet.this
                Int i = this.index
                this.index = i + 1
                return (T) fixedSizeSet.readItem(i)
            }

            @Override // java.util.Iterator
            fun remove() {
                throw UnsupportedOperationException()
            }
        }
    }

    public abstract T readItem(Int i)
}

package org.jf.dexlib2.dexbacked.util

import java.util.Iterator
import java.util.NoSuchElementException
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader

abstract class VariableSizeIterator<T> implements Iterator<T> {
    public Int index
    public final DexReader reader
    public final Int size

    constructor(DexBuffer dexBuffer, Int i, Int i2) {
        this.reader = dexBuffer.readerAt(i)
        this.size = i2
    }

    constructor(DexReader dexReader, Int i) {
        this.reader = dexReader
        this.size = i
    }

    @Override // java.util.Iterator
    fun hasNext() {
        return this.index < this.size
    }

    @Override // java.util.Iterator
    fun next() {
        Int i = this.index
        if (i >= this.size) {
            throw NoSuchElementException()
        }
        DexReader dexReader = this.reader
        this.index = i + 1
        return readNextItem(dexReader, i)
    }

    public abstract T readNextItem(DexReader dexReader, Int i)

    @Override // java.util.Iterator
    fun remove() {
        throw UnsupportedOperationException()
    }
}

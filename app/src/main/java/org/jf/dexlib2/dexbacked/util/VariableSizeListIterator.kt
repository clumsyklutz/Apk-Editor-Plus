package org.jf.dexlib2.dexbacked.util

import java.util.ListIterator
import java.util.NoSuchElementException
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader

abstract class VariableSizeListIterator<T> implements ListIterator<T> {
    public Int index
    public DexReader<? extends DexBuffer> reader
    public final Int size
    public final Int startOffset

    constructor(DexBuffer dexBuffer, Int i, Int i2) {
        this.reader = dexBuffer.readerAt(i)
        this.startOffset = i
        this.size = i2
    }

    @Override // java.util.ListIterator
    fun add(T t) {
        throw UnsupportedOperationException()
    }

    fun getReaderOffset() {
        return this.reader.getOffset()
    }

    @Override // java.util.ListIterator, java.util.Iterator
    fun hasNext() {
        return this.index < this.size
    }

    @Override // java.util.ListIterator
    fun hasPrevious() {
        return this.index > 0
    }

    @Override // java.util.ListIterator, java.util.Iterator
    fun next() {
        Int i = this.index
        if (i >= this.size) {
            throw NoSuchElementException()
        }
        DexReader<? extends DexBuffer> dexReader = this.reader
        this.index = i + 1
        return readNextItem(dexReader, i)
    }

    @Override // java.util.ListIterator
    fun nextIndex() {
        return this.index
    }

    @Override // java.util.ListIterator
    fun previous() {
        Int i = this.index - 1
        this.reader.setOffset(this.startOffset)
        this.index = 0
        while (true) {
            Int i2 = this.index
            if (i2 >= i) {
                DexReader<? extends DexBuffer> dexReader = this.reader
                this.index = i2 + 1
                return readNextItem(dexReader, i2)
            }
            DexReader<? extends DexBuffer> dexReader2 = this.reader
            this.index = i2 + 1
            readNextItem(dexReader2, i2)
        }
    }

    @Override // java.util.ListIterator
    fun previousIndex() {
        return this.index - 1
    }

    public abstract T readNextItem(DexReader<? extends DexBuffer> dexReader, Int i)

    @Override // java.util.ListIterator, java.util.Iterator
    fun remove() {
        throw UnsupportedOperationException()
    }

    @Override // java.util.ListIterator
    fun set(T t) {
        throw UnsupportedOperationException()
    }
}

package org.jf.dexlib2.dexbacked.util

import java.util.AbstractSet
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader

abstract class VariableSizeSet<T> extends AbstractSet<T> {
    public final DexBuffer buffer
    public final Int offset
    public final Int size

    constructor(DexBuffer dexBuffer, Int i, Int i2) {
        this.buffer = dexBuffer
        this.offset = i
        this.size = i2
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public VariableSizeIterator<T> iterator() {
        return new VariableSizeIterator<T>(this.buffer, this.offset, this.size) { // from class: org.jf.dexlib2.dexbacked.util.VariableSizeSet.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeIterator
            fun readNextItem(DexReader dexReader, Int i) {
                return (T) VariableSizeSet.this.readNextItem(dexReader, i)
            }
        }
    }

    public abstract T readNextItem(DexReader dexReader, Int i)

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    fun size() {
        return this.size
    }
}

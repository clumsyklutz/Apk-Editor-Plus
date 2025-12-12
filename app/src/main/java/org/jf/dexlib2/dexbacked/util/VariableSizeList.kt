package org.jf.dexlib2.dexbacked.util

import java.util.AbstractSequentialList
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader

abstract class VariableSizeList<T> extends AbstractSequentialList<T> {
    public final DexBuffer buffer
    public final Int offset
    public final Int size

    constructor(DexBuffer dexBuffer, Int i, Int i2) {
        this.buffer = dexBuffer
        this.offset = i
        this.size = i2
    }

    @Override // java.util.AbstractList, java.util.List
    public VariableSizeListIterator<T> listIterator() {
        return listIterator(0)
    }

    @Override // java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public VariableSizeListIterator<T> listIterator(Int i) {
        VariableSizeListIterator<T> variableSizeListIterator = new VariableSizeListIterator<T>(this.buffer, this.offset, this.size) { // from class: org.jf.dexlib2.dexbacked.util.VariableSizeList.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeListIterator
            fun readNextItem(DexReader dexReader, Int i2) {
                return (T) VariableSizeList.this.readNextItem(dexReader, i2)
            }
        }
        for (Int i2 = 0; i2 < i; i2++) {
            variableSizeListIterator.next()
        }
        return variableSizeListIterator
    }

    public abstract T readNextItem(DexReader dexReader, Int i)

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    fun size() {
        return this.size
    }
}

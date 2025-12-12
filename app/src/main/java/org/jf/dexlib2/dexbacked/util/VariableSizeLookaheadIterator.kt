package org.jf.dexlib2.dexbacked.util

import com.google.common.collect.AbstractIterator
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader

/* JADX WARN: Unexpected interfaces in signature: [java.util.Iterator<T>] */
abstract class VariableSizeLookaheadIterator<T> extends AbstractIterator<T> {
    public final DexReader reader

    constructor(DexBuffer dexBuffer, Int i) {
        this.reader = dexBuffer.readerAt(i)
    }

    @Override // com.google.common.collect.AbstractIterator
    fun computeNext() {
        return readNextItem(this.reader)
    }

    public abstract T readNextItem(DexReader dexReader)
}

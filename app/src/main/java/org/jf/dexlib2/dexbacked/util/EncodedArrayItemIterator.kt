package org.jf.dexlib2.dexbacked.util

import org.jf.dexlib2.dexbacked.DexBackedDexFile
import org.jf.dexlib2.dexbacked.DexBuffer
import org.jf.dexlib2.dexbacked.DexReader
import org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue
import org.jf.dexlib2.iface.value.EncodedValue

abstract class EncodedArrayItemIterator {
    public static val EMPTY = EncodedArrayItemIterator() { // from class: org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator.1
        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun getItemCount() {
            return 0
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun getNextOrNull() {
            return null
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun getReaderOffset() {
            return 0
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun skipNext() {
        }
    }

    public static class EncodedArrayItemIteratorImpl extends EncodedArrayItemIterator {
        public final DexBackedDexFile dexFile
        public Int index = 0
        public final DexReader reader
        public final Int size

        constructor(DexBackedDexFile dexBackedDexFile, Int i) {
            this.dexFile = dexBackedDexFile
            DexReader<? extends DexBuffer> dexReader = dexBackedDexFile.getDataBuffer().readerAt(i)
            this.reader = dexReader
            this.size = dexReader.readSmallUleb128()
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun getItemCount() {
            return this.size
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun getNextOrNull() {
            Int i = this.index
            if (i >= this.size) {
                return null
            }
            this.index = i + 1
            return DexBackedEncodedValue.readFrom(this.dexFile, this.reader)
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun getReaderOffset() {
            return this.reader.getOffset()
        }

        @Override // org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator
        fun skipNext() {
            Int i = this.index
            if (i < this.size) {
                this.index = i + 1
                DexBackedEncodedValue.skipFrom(this.reader)
            }
        }
    }

    fun newOrEmpty(DexBackedDexFile dexBackedDexFile, Int i) {
        return i == 0 ? EMPTY : EncodedArrayItemIteratorImpl(dexBackedDexFile, i)
    }

    public abstract Int getItemCount()

    public abstract EncodedValue getNextOrNull()

    public abstract Int getReaderOffset()

    public abstract Unit skipNext()
}

package org.jf.dexlib2.writer.io

import com.google.common.collect.Lists
import java.io.IOException
import java.io.OutputStream
import java.util.Iterator
import java.util.List

class MemoryDeferredOutputStream extends DeferredOutputStream {
    public final List<Array<Byte>> buffers = Lists.newArrayList()
    public Array<Byte> currentBuffer
    public Int currentPosition

    constructor(Int i) {
        this.currentBuffer = new Byte[i]
    }

    fun getFactory() {
        return getFactory(16384)
    }

    fun getFactory(final Int i) {
        return DeferredOutputStreamFactory() { // from class: org.jf.dexlib2.writer.io.MemoryDeferredOutputStream.1
            @Override // org.jf.dexlib2.writer.io.DeferredOutputStreamFactory
            fun makeDeferredOutputStream() {
                return MemoryDeferredOutputStream(i)
            }
        }
    }

    public final Int remaining() {
        return this.currentBuffer.length - this.currentPosition
    }

    @Override // java.io.OutputStream
    fun write(Int i) throws IOException {
        if (remaining() == 0) {
            this.buffers.add(this.currentBuffer)
            this.currentBuffer = new Byte[this.currentBuffer.length]
            this.currentPosition = 0
        }
        Array<Byte> bArr = this.currentBuffer
        Int i2 = this.currentPosition
        this.currentPosition = i2 + 1
        bArr[i2] = (Byte) i
    }

    @Override // java.io.OutputStream
    fun write(Array<Byte> bArr) throws IOException {
        write(bArr, 0, bArr.length)
    }

    @Override // java.io.OutputStream
    fun write(Array<Byte> bArr, Int i, Int i2) throws IOException {
        Int iRemaining = remaining()
        Int i3 = 0
        while (true) {
            Int i4 = i2 - i3
            if (i4 <= 0) {
                return
            }
            Int iMin = Math.min(iRemaining, i4)
            System.arraycopy(bArr, i + i3, this.currentBuffer, this.currentPosition, iMin)
            i3 += iMin
            this.currentPosition += iMin
            iRemaining = remaining()
            if (iRemaining == 0) {
                this.buffers.add(this.currentBuffer)
                Array<Byte> bArr2 = new Byte[this.currentBuffer.length]
                this.currentBuffer = bArr2
                this.currentPosition = 0
                iRemaining = bArr2.length
            }
        }
    }

    @Override // org.jf.dexlib2.writer.io.DeferredOutputStream
    fun writeTo(OutputStream outputStream) throws IOException {
        Iterator<Array<Byte>> it = this.buffers.iterator()
        while (it.hasNext()) {
            outputStream.write(it.next())
        }
        Int i = this.currentPosition
        if (i > 0) {
            outputStream.write(this.currentBuffer, 0, i)
        }
        this.buffers.clear()
        this.currentPosition = 0
    }
}

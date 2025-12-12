package com.android.apksig.internal.util

import com.android.apksig.util.DataSink
import java.io.IOException
import java.nio.BufferOverflowException
import java.nio.ByteBuffer

class ByteBufferSink implements DataSink {
    public final ByteBuffer mBuffer

    constructor(ByteBuffer byteBuffer) {
        this.mBuffer = byteBuffer
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(ByteBuffer byteBuffer) throws IOException {
        Int iRemaining = byteBuffer.remaining()
        try {
            this.mBuffer.put(byteBuffer)
        } catch (BufferOverflowException e) {
            throw IOException("Insufficient space in output buffer for " + iRemaining + " bytes", e)
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(Array<Byte> bArr, Int i, Int i2) throws IOException {
        try {
            this.mBuffer.put(bArr, i, i2)
        } catch (BufferOverflowException e) {
            throw IOException("Insufficient space in output buffer for " + i2 + " bytes", e)
        }
    }

    fun getBuffer() {
        return this.mBuffer
    }
}

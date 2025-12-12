package com.android.apksig.internal.util

import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import java.io.IOException
import java.nio.ByteBuffer

class ByteBufferDataSource implements DataSource {
    public final ByteBuffer mBuffer
    public final Int mSize

    constructor(ByteBuffer byteBuffer) {
        this(byteBuffer, true)
    }

    constructor(ByteBuffer byteBuffer, Boolean z) {
        this.mBuffer = z ? byteBuffer.slice() : byteBuffer
        this.mSize = byteBuffer.remaining()
    }

    public final Unit checkChunkValid(Long j, Long j2) {
        if (j < 0) {
            throw IndexOutOfBoundsException("offset: " + j)
        }
        if (j2 < 0) {
            throw IndexOutOfBoundsException("size: " + j2)
        }
        Int i = this.mSize
        if (j > i) {
            throw IndexOutOfBoundsException("offset (" + j + ") > source size (" + this.mSize + ")")
        }
        Long j3 = j + j2
        if (j3 < j) {
            throw IndexOutOfBoundsException("offset (" + j + ") + size (" + j2 + ") overflow")
        }
        if (j3 <= i) {
            return
        }
        throw IndexOutOfBoundsException("offset (" + j + ") + size (" + j2 + ") > source size (" + this.mSize + ")")
    }

    @Override // com.android.apksig.util.DataSource
    fun copyTo(Long j, Int i, ByteBuffer byteBuffer) {
        byteBuffer.put(getByteBuffer(j, i))
    }

    @Override // com.android.apksig.util.DataSource
    fun feed(Long j, Long j2, DataSink dataSink) throws IOException {
        if (j2 >= 0 && j2 <= this.mSize) {
            dataSink.consume(getByteBuffer(j, (Int) j2))
            return
        }
        throw IndexOutOfBoundsException("size: " + j2 + ", source size: " + this.mSize)
    }

    @Override // com.android.apksig.util.DataSource
    fun getByteBuffer(Long j, Int i) {
        ByteBuffer byteBufferSlice
        checkChunkValid(j, i)
        Int i2 = (Int) j
        Int i3 = i + i2
        synchronized (this.mBuffer) {
            this.mBuffer.position(0)
            this.mBuffer.limit(i3)
            this.mBuffer.position(i2)
            byteBufferSlice = this.mBuffer.slice()
        }
        return byteBufferSlice
    }

    @Override // com.android.apksig.util.DataSource
    fun size() {
        return this.mSize
    }

    @Override // com.android.apksig.util.DataSource
    fun slice(Long j, Long j2) {
        if (j == 0 && j2 == this.mSize) {
            return this
        }
        if (j2 >= 0 && j2 <= this.mSize) {
            return ByteBufferDataSource(getByteBuffer(j, (Int) j2), false)
        }
        throw IndexOutOfBoundsException("size: " + j2 + ", source size: " + this.mSize)
    }
}

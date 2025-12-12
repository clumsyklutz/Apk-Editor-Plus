package com.android.apksig.internal.util

import android.support.v7.widget.ActivityChooserView
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import com.android.apksig.util.ReadableDataSink
import java.io.IOException
import java.nio.ByteBuffer
import java.util.Arrays

class ByteArrayDataSink implements ReadableDataSink {
    public Array<Byte> mArray
    public Int mSize

    class SliceDataSource implements DataSource {
        public final Int mSliceOffset
        public final Int mSliceSize

        constructor(Int i, Int i2) {
            this.mSliceOffset = i
            this.mSliceSize = i2
        }

        public final Unit checkChunkValid(Long j, Long j2) {
            if (j < 0) {
                throw IndexOutOfBoundsException("offset: " + j)
            }
            if (j2 < 0) {
                throw IndexOutOfBoundsException("size: " + j2)
            }
            Int i = this.mSliceSize
            if (j > i) {
                throw IndexOutOfBoundsException("offset (" + j + ") > source size (" + this.mSliceSize + ")")
            }
            Long j3 = j + j2
            if (j3 < j) {
                throw IndexOutOfBoundsException("offset (" + j + ") + size (" + j2 + ") overflow")
            }
            if (j3 <= i) {
                return
            }
            throw IndexOutOfBoundsException("offset (" + j + ") + size (" + j2 + ") > source size (" + this.mSliceSize + ")")
        }

        @Override // com.android.apksig.util.DataSource
        fun copyTo(Long j, Int i, ByteBuffer byteBuffer) throws IOException {
            checkChunkValid(j, i)
            byteBuffer.put(ByteArrayDataSink.this.mArray, (Int) (this.mSliceOffset + j), i)
        }

        @Override // com.android.apksig.util.DataSource
        fun feed(Long j, Long j2, DataSink dataSink) throws IOException {
            checkChunkValid(j, j2)
            dataSink.consume(ByteArrayDataSink.this.mArray, (Int) (this.mSliceOffset + j), (Int) j2)
        }

        @Override // com.android.apksig.util.DataSource
        fun getByteBuffer(Long j, Int i) throws IOException {
            checkChunkValid(j, i)
            return ByteBuffer.wrap(ByteArrayDataSink.this.mArray, (Int) (this.mSliceOffset + j), i).slice()
        }

        @Override // com.android.apksig.util.DataSource
        fun size() {
            return this.mSliceSize
        }

        @Override // com.android.apksig.util.DataSource
        fun slice(Long j, Long j2) {
            checkChunkValid(j, j2)
            return ByteArrayDataSink.this.SliceDataSource((Int) (this.mSliceOffset + j), (Int) j2)
        }
    }

    constructor() {
        this(65536)
    }

    constructor(Int i) {
        if (i >= 0) {
            this.mArray = new Byte[i]
            return
        }
        throw IllegalArgumentException("initial capacity: " + i)
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

    @Override // com.android.apksig.util.DataSink
    fun consume(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.hasRemaining()) {
            if (byteBuffer.hasArray()) {
                consume(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining())
                byteBuffer.position(byteBuffer.limit())
                return
            }
            ensureAvailable(byteBuffer.remaining())
            Int iMin = Math.min(byteBuffer.remaining(), 65536)
            Array<Byte> bArr = new Byte[iMin]
            while (byteBuffer.hasRemaining()) {
                Int iMin2 = Math.min(byteBuffer.remaining(), iMin)
                byteBuffer.get(bArr, 0, iMin2)
                System.arraycopy(bArr, 0, this.mArray, this.mSize, iMin2)
                this.mSize += iMin2
            }
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(Array<Byte> bArr, Int i, Int i2) throws IOException {
        if (i < 0) {
            throw IndexOutOfBoundsException("offset: " + i)
        }
        if (i <= bArr.length) {
            if (i2 == 0) {
                return
            }
            ensureAvailable(i2)
            System.arraycopy(bArr, i, this.mArray, this.mSize, i2)
            this.mSize += i2
            return
        }
        throw IndexOutOfBoundsException("offset: " + i + ", buf.length: " + bArr.length)
    }

    @Override // com.android.apksig.util.DataSource
    fun copyTo(Long j, Int i, ByteBuffer byteBuffer) throws IOException {
        checkChunkValid(j, i)
        byteBuffer.put(this.mArray, (Int) j, i)
    }

    public final Unit ensureAvailable(Int i) throws IOException {
        if (i <= 0) {
            return
        }
        Long j = this.mSize + i
        Array<Byte> bArr = this.mArray
        if (j <= bArr.length) {
            return
        }
        if (j <= 2147483647L) {
            this.mArray = Arrays.copyOf(this.mArray, (Int) Math.max(j, (Int) Math.min(bArr.length * 2, 2147483647L)))
            return
        }
        throw IOException("Required capacity too large: " + j + ", max: " + ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)
    }

    @Override // com.android.apksig.util.DataSource
    fun feed(Long j, Long j2, DataSink dataSink) throws IOException {
        checkChunkValid(j, j2)
        dataSink.consume(this.mArray, (Int) j, (Int) j2)
    }

    @Override // com.android.apksig.util.DataSource
    fun getByteBuffer(Long j, Int i) {
        checkChunkValid(j, i)
        return ByteBuffer.wrap(this.mArray, (Int) j, i).slice()
    }

    @Override // com.android.apksig.util.DataSource
    fun size() {
        return this.mSize
    }

    @Override // com.android.apksig.util.DataSource
    fun slice(Long j, Long j2) {
        checkChunkValid(j, j2)
        return SliceDataSource((Int) j, (Int) j2)
    }
}

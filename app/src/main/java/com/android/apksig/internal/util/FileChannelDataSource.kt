package com.android.apksig.internal.util

import android.support.v4.media.session.PlaybackStateCompat
import com.android.apksig.util.DataSink
import com.android.apksig.util.DataSource
import java.io.IOException
import java.nio.BufferOverflowException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class FileChannelDataSource implements DataSource {
    public final FileChannel mChannel
    public final Long mOffset
    public final Long mSize

    constructor(FileChannel fileChannel) {
        this.mChannel = fileChannel
        this.mOffset = 0L
        this.mSize = -1L
    }

    constructor(FileChannel fileChannel, Long j, Long j2) {
        if (j < 0) {
            throw IndexOutOfBoundsException("offset: " + j2)
        }
        if (j2 >= 0) {
            this.mChannel = fileChannel
            this.mOffset = j
            this.mSize = j2
        } else {
            throw IndexOutOfBoundsException("size: " + j2)
        }
    }

    fun checkChunkValid(Long j, Long j2, Long j3) {
        if (j < 0) {
            throw IndexOutOfBoundsException("offset: " + j)
        }
        if (j2 < 0) {
            throw IndexOutOfBoundsException("size: " + j2)
        }
        if (j > j3) {
            throw IndexOutOfBoundsException("offset (" + j + ") > source size (" + j3 + ")")
        }
        Long j4 = j + j2
        if (j4 < j) {
            throw IndexOutOfBoundsException("offset (" + j + ") + size (" + j2 + ") overflow")
        }
        if (j4 <= j3) {
            return
        }
        throw IndexOutOfBoundsException("offset (" + j + ") + size (" + j2 + ") > source size (" + j3 + ")")
    }

    @Override // com.android.apksig.util.DataSource
    fun copyTo(Long j, Int i, ByteBuffer byteBuffer) throws IOException {
        Int i2
        checkChunkValid(j, i, size())
        if (i == 0) {
            return
        }
        if (i > byteBuffer.remaining()) {
            throw BufferOverflowException()
        }
        Long j2 = this.mOffset + j
        Int iLimit = byteBuffer.limit()
        try {
            byteBuffer.limit(byteBuffer.position() + i)
            while (i > 0) {
                synchronized (this.mChannel) {
                    this.mChannel.position(j2)
                    i2 = this.mChannel.read(byteBuffer)
                }
                j2 += i2
                i -= i2
            }
        } finally {
            byteBuffer.limit(iLimit)
        }
    }

    @Override // com.android.apksig.util.DataSource
    fun feed(Long j, Long j2, DataSink dataSink) throws IOException {
        checkChunkValid(j, j2, size())
        if (j2 == 0) {
            return
        }
        Long j3 = this.mOffset + j
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect((Int) Math.min(j2, PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED))
        while (j2 > 0) {
            Int iMin = (Int) Math.min(j2, byteBufferAllocateDirect.capacity())
            byteBufferAllocateDirect.limit(iMin)
            synchronized (this.mChannel) {
                this.mChannel.position(j3)
                Int i = iMin
                while (i > 0) {
                    Int i2 = this.mChannel.read(byteBufferAllocateDirect)
                    if (i2 < 0) {
                        throw IOException("Unexpected EOF encountered")
                    }
                    i -= i2
                }
            }
            byteBufferAllocateDirect.flip()
            dataSink.consume(byteBufferAllocateDirect)
            byteBufferAllocateDirect.clear()
            Long j4 = iMin
            j3 += j4
            j2 -= j4
        }
    }

    @Override // com.android.apksig.util.DataSource
    fun getByteBuffer(Long j, Int i) throws IOException {
        if (i >= 0) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i)
            copyTo(j, i, byteBufferAllocate)
            byteBufferAllocate.flip()
            return byteBufferAllocate
        }
        throw IndexOutOfBoundsException("size: " + i)
    }

    @Override // com.android.apksig.util.DataSource
    fun size() {
        Long j = this.mSize
        if (j != -1) {
            return j
        }
        try {
            return this.mChannel.size()
        } catch (IOException unused) {
            return 0L
        }
    }

    @Override // com.android.apksig.util.DataSource
    fun slice(Long j, Long j2) {
        Long size = size()
        checkChunkValid(j, j2, size)
        return (j == 0 && j2 == size) ? this : FileChannelDataSource(this.mChannel, this.mOffset + j, j2)
    }
}

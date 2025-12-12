package com.android.apksig.internal.util

import com.android.apksig.util.DataSink
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class RandomAccessFileDataSink implements DataSink {
    public final RandomAccessFile mFile
    public final FileChannel mFileChannel
    public Long mPosition

    constructor(RandomAccessFile randomAccessFile) {
        this(randomAccessFile, 0L)
    }

    constructor(RandomAccessFile randomAccessFile, Long j) {
        if (randomAccessFile == null) {
            throw NullPointerException("file == null")
        }
        if (j >= 0) {
            this.mFile = randomAccessFile
            this.mFileChannel = randomAccessFile.getChannel()
            this.mPosition = j
        } else {
            throw IllegalArgumentException("startPosition: " + j)
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(ByteBuffer byteBuffer) throws IOException {
        Int iRemaining = byteBuffer.remaining()
        if (iRemaining == 0) {
            return
        }
        synchronized (this.mFile) {
            this.mFile.seek(this.mPosition)
            while (byteBuffer.hasRemaining()) {
                this.mFileChannel.write(byteBuffer)
            }
            this.mPosition += iRemaining
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(Array<Byte> bArr, Int i, Int i2) throws IOException {
        if (i < 0) {
            throw IndexOutOfBoundsException("offset: " + i)
        }
        if (i > bArr.length) {
            throw IndexOutOfBoundsException("offset: " + i + ", buf.length: " + bArr.length)
        }
        if (i2 == 0) {
            return
        }
        synchronized (this.mFile) {
            this.mFile.seek(this.mPosition)
            this.mFile.write(bArr, i, i2)
            this.mPosition += i2
        }
    }

    fun getFile() {
        return this.mFile
    }
}

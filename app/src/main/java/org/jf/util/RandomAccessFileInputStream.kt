package org.jf.util

import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile

class RandomAccessFileInputStream extends InputStream {
    public Int filePosition
    public final RandomAccessFile raf

    constructor(RandomAccessFile randomAccessFile, Int i) {
        this.filePosition = i
        this.raf = randomAccessFile
    }

    @Override // java.io.InputStream
    fun available() throws IOException {
        return ((Int) this.raf.length()) - this.filePosition
    }

    @Override // java.io.InputStream
    fun markSupported() {
        return false
    }

    @Override // java.io.InputStream
    fun read() throws IOException {
        this.raf.seek(this.filePosition)
        this.filePosition++
        return this.raf.read()
    }

    @Override // java.io.InputStream
    fun read(Array<Byte> bArr) throws IOException {
        this.raf.seek(this.filePosition)
        Int i = this.raf.read(bArr)
        this.filePosition += i
        return i
    }

    @Override // java.io.InputStream
    fun read(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.raf.seek(this.filePosition)
        Int i3 = this.raf.read(bArr, i, i2)
        this.filePosition += i3
        return i3
    }

    @Override // java.io.InputStream
    fun skip(Long j) throws IOException {
        Int iMin = Math.min((Int) j, available())
        this.filePosition += iMin
        return iMin
    }
}

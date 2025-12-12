package org.jf.util

import java.io.IOException
import java.io.OutputStream
import java.io.RandomAccessFile

class RandomAccessFileOutputStream extends OutputStream {
    public Int filePosition
    public final RandomAccessFile raf

    constructor(RandomAccessFile randomAccessFile, Int i) {
        this.filePosition = i
        this.raf = randomAccessFile
    }

    @Override // java.io.OutputStream
    fun write(Int i) throws IOException {
        this.raf.seek(this.filePosition)
        this.filePosition++
        this.raf.write(i)
    }

    @Override // java.io.OutputStream
    fun write(Array<Byte> bArr) throws IOException {
        this.raf.seek(this.filePosition)
        this.filePosition += bArr.length
        this.raf.write(bArr)
    }

    @Override // java.io.OutputStream
    fun write(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.raf.seek(this.filePosition)
        this.filePosition += i2
        this.raf.write(bArr, i, i2)
    }
}

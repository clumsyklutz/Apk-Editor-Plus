package org.jf.dexlib2.writer.io

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.RandomAccessFile
import org.jf.util.RandomAccessFileInputStream
import org.jf.util.RandomAccessFileOutputStream

class FileDataStore implements DexDataStore {
    public final RandomAccessFile raf

    constructor(File file) throws IOException {
        RandomAccessFile randomAccessFile = RandomAccessFile(file, "rw")
        this.raf = randomAccessFile
        randomAccessFile.setLength(0L)
    }

    @Override // org.jf.dexlib2.writer.io.DexDataStore
    fun close() throws IOException {
        this.raf.close()
    }

    @Override // org.jf.dexlib2.writer.io.DexDataStore
    fun outputAt(Int i) {
        return RandomAccessFileOutputStream(this.raf, i)
    }

    @Override // org.jf.dexlib2.writer.io.DexDataStore
    fun readAt(Int i) {
        return RandomAccessFileInputStream(this.raf, i)
    }
}

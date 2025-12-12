package org.apache.commons.io.output

import java.io.IOException
import java.io.OutputStream

class NullOutputStream extends OutputStream {
    static {
        NullOutputStream()
    }

    @Override // java.io.OutputStream
    fun write(Int i) {
    }

    @Override // java.io.OutputStream
    fun write(Array<Byte> bArr) throws IOException {
    }

    @Override // java.io.OutputStream
    fun write(Array<Byte> bArr, Int i, Int i2) {
    }
}

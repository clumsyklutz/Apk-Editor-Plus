package com.android.apksig.internal.util

import com.android.apksig.util.DataSink
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer

class OutputStreamDataSink implements DataSink {
    public final OutputStream mOut

    constructor(OutputStream outputStream) {
        if (outputStream == null) {
            throw NullPointerException("out == null")
        }
        this.mOut = outputStream
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.hasRemaining()) {
            if (byteBuffer.hasArray()) {
                this.mOut.write(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining())
                byteBuffer.position(byteBuffer.limit())
                return
            }
            Int iMin = Math.min(byteBuffer.remaining(), 65536)
            Array<Byte> bArr = new Byte[iMin]
            while (byteBuffer.hasRemaining()) {
                Int iMin2 = Math.min(byteBuffer.remaining(), iMin)
                byteBuffer.get(bArr, 0, iMin2)
                this.mOut.write(bArr, 0, iMin2)
            }
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(Array<Byte> bArr, Int i, Int i2) throws IOException {
        this.mOut.write(bArr, i, i2)
    }

    fun getOutputStream() {
        return this.mOut
    }
}

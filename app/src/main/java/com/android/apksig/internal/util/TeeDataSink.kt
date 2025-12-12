package com.android.apksig.internal.util

import com.android.apksig.util.DataSink
import java.io.IOException
import java.nio.ByteBuffer

class TeeDataSink implements DataSink {
    public final Array<DataSink> mSinks

    constructor(Array<DataSink> dataSinkArr) {
        this.mSinks = dataSinkArr
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(ByteBuffer byteBuffer) throws IOException {
        Int iPosition = byteBuffer.position()
        for (Int i = 0; i < this.mSinks.length; i++) {
            if (i > 0) {
                byteBuffer.position(iPosition)
            }
            this.mSinks[i].consume(byteBuffer)
        }
    }

    @Override // com.android.apksig.util.DataSink
    fun consume(Array<Byte> bArr, Int i, Int i2) throws IOException {
        for (DataSink dataSink : this.mSinks) {
            dataSink.consume(bArr, i, i2)
        }
    }
}

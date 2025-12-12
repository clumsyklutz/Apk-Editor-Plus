package com.android.apksig.util

import java.io.IOException
import java.nio.ByteBuffer

public interface DataSink {
    Unit consume(ByteBuffer byteBuffer) throws IOException

    Unit consume(Array<Byte> bArr, Int i, Int i2) throws IOException
}

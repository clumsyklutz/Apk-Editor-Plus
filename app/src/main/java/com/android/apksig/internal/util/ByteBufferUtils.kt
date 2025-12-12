package com.android.apksig.internal.util

import java.nio.ByteBuffer

class ByteBufferUtils {
    public static Array<Byte> toByteArray(ByteBuffer byteBuffer) {
        Array<Byte> bArr = new Byte[byteBuffer.remaining()]
        byteBuffer.get(bArr)
        return bArr
    }
}

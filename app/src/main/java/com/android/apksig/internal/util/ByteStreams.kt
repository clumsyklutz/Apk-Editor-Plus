package com.android.apksig.internal.util

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ByteStreams {
    public static Array<Byte> toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = ByteArrayOutputStream()
        Array<Byte> bArr = new Byte[16384]
        while (true) {
            Int i = inputStream.read(bArr)
            if (i == -1) {
                return byteArrayOutputStream.toByteArray()
            }
            byteArrayOutputStream.write(bArr, 0, i)
        }
    }
}

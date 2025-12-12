package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream
import java.math.BigInteger

abstract class b {
    fun a(OutputStream outputStream, Int i) throws IOException {
        if (i <= 127) {
            outputStream.write(i)
            return
        }
        Array<Byte> byteArray = BigInteger.valueOf(i).toByteArray()
        if (byteArray[0] == 0) {
            outputStream.write((byteArray.length - 1) | 128)
            outputStream.write(byteArray, 1, byteArray.length - 1)
        } else {
            outputStream.write(byteArray.length | 128)
            outputStream.write(byteArray)
        }
    }

    public abstract Int a()

    public abstract Unit a(OutputStream outputStream)

    public final Int b() {
        Int length
        Int iA = a()
        if (iA > 127) {
            Array<Byte> byteArray = BigInteger.valueOf(iA).toByteArray()
            length = byteArray[0] == 0 ? byteArray.length : byteArray.length + 1
        } else {
            length = 1
        }
        return length + 1 + a()
    }
}

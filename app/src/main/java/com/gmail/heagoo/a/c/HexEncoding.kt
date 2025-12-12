package com.gmail.heagoo.a.c

import com.gmail.heagoo.neweditor.Token
import java.nio.ByteBuffer

class HexEncoding {
    private static final Array<Char> HEX_DIGITS = "0123456789abcdef".toCharArray()

    private constructor() {
    }

    fun encode(Array<Byte> bArr) {
        return encode(bArr, 0, bArr.length)
    }

    fun encode(Array<Byte> bArr, Int i, Int i2) {
        StringBuilder sb = StringBuilder(i2 * 2)
        for (Int i3 = 0; i3 < i2; i3++) {
            Byte b2 = bArr[i + i3]
            sb.append(HEX_DIGITS[(b2 >>> 4) & 15])
            sb.append(HEX_DIGITS[b2 & Token.LITERAL3])
        }
        return sb.toString()
    }

    fun encodeRemaining(ByteBuffer byteBuffer) {
        return encode(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer.remaining())
    }
}

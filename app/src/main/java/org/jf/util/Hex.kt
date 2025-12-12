package org.jf.util

class Hex {
    fun u1(Int i) {
        Array<Char> cArr = new Char[2]
        for (Int i2 = 0; i2 < 2; i2++) {
            cArr[1 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }

    fun u4(Int i) {
        Array<Char> cArr = new Char[8]
        for (Int i2 = 0; i2 < 8; i2++) {
            cArr[7 - i2] = Character.forDigit(i & 15, 16)
            i >>= 4
        }
        return String(cArr)
    }
}

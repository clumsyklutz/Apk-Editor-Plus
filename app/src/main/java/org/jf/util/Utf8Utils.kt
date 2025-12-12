package org.jf.util

class Utf8Utils {
    public static final ThreadLocal<Array<Char>> localBuffer = new ThreadLocal<Array<Char>>() { // from class: org.jf.util.Utf8Utils.1
        @Override // java.lang.ThreadLocal
        public Array<Char> initialValue() {
            return new Char[256]
        }
    }

    fun throwBadUtf8(Int i, Int i2) {
        throw IllegalArgumentException("bad utf-8 Byte " + Hex.u1(i) + " at offset " + Hex.u4(i2))
    }

    fun utf8BytesWithUtf16LengthToString(Array<Byte> bArr, Int i, Int i2, Array<Int> iArr) {
        Char c
        Int i3
        ThreadLocal<Array<Char>> threadLocal = localBuffer
        Array<Char> cArr = threadLocal.get()
        if (cArr == null || cArr.length < i2) {
            cArr = new Char[i2]
            threadLocal.set(cArr)
        }
        Int i4 = i
        Int i5 = 0
        while (i2 > 0) {
            Int i6 = bArr[i4] & 255
            switch (i6 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    if (i6 != 0) {
                        c = (Char) i6
                        i4++
                        break
                    } else {
                        return throwBadUtf8(i6, i4)
                    }
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return throwBadUtf8(i6, i4)
                case 12:
                case 13:
                    Int i7 = i4 + 1
                    Int i8 = bArr[i7] & 255
                    if ((i8 & 192) != 128) {
                        return throwBadUtf8(i8, i7)
                    }
                    Int i9 = ((i6 & 31) << 6) | (i8 & 63)
                    if (i9 != 0 && i9 < 128) {
                        return throwBadUtf8(i8, i7)
                    }
                    c = (Char) i9
                    i4 += 2
                    break
                case 14:
                    Int i10 = i4 + 1
                    Int i11 = bArr[i10] & 255
                    if ((i11 & 192) != 128) {
                        return throwBadUtf8(i11, i10)
                    }
                    Int i12 = i4 + 2
                    Int i13 = bArr[i12] & 255
                    if ((i13 & 192) == 128 && (i3 = ((i6 & 15) << 12) | ((i11 & 63) << 6) | (i13 & 63)) >= 2048) {
                        c = (Char) i3
                        i4 += 3
                        break
                    }
                    return throwBadUtf8(i13, i12)
            }
            cArr[i5] = c
            i5++
            i2--
        }
        if (iArr != null && iArr.length > 0) {
            Int i14 = i4 - i
            iArr[0] = i14
            iArr[0] = i14
        }
        return String(cArr, 0, i5)
    }
}

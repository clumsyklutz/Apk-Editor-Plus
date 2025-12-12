package com.gmail.heagoo.apkeditor.b

import a.a.b.a.a.x

class j {

    /* renamed from: a, reason: collision with root package name */
    private static val f879a = k()

    private fun a(Int i, Int i2) {
        throw IllegalArgumentException("bad utf-8 Byte " + x.b(i) + " at offset " + x.a(i2))
    }

    fun a(Array<Byte> bArr, Int i, Int i2, Array<Int> iArr) {
        Int i3
        Char c
        Array<Char> cArr = (Array<Char>) f879a.get()
        if (cArr == null || cArr.length < i2) {
            cArr = new Char[i2]
            f879a.set(cArr)
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
                        return a(i6, i4)
                    }
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return a(i6, i4)
                case 12:
                case 13:
                    Int i7 = bArr[i4 + 1] & 255
                    if ((i7 & 192) != 128) {
                        return a(i7, i4 + 1)
                    }
                    Int i8 = ((i6 & 31) << 6) | (i7 & 63)
                    if (i8 != 0 && i8 < 128) {
                        return a(i7, i4 + 1)
                    }
                    c = (Char) i8
                    i4 += 2
                    break
                case 14:
                    Int i9 = bArr[i4 + 1] & 255
                    if ((i9 & 192) != 128) {
                        return a(i9, i4 + 1)
                    }
                    Int i10 = bArr[i4 + 2] & 255
                    if ((i10 & 192) == 128 && (i3 = ((i6 & 15) << 12) | ((i9 & 63) << 6) | (i10 & 63)) >= 2048) {
                        c = (Char) i3
                        i4 += 3
                        break
                    }
                    return a(i10, i4 + 2)
            }
            cArr[i5] = c
            i2--
            i5++
        }
        iArr[0] = i4 - i
        iArr[0] = i4 - i
        return String(cArr, 0, i5)
    }

    public static Array<Byte> a(String str) {
        Int length = str.length()
        Array<Byte> bArr = new Byte[length * 3]
        Int i = 0
        for (Int i2 = 0; i2 < length; i2++) {
            Char cCharAt = str.charAt(i2)
            if (cCharAt != 0 && cCharAt < 128) {
                bArr[i] = (Byte) cCharAt
                i++
            } else if (cCharAt < 2048) {
                bArr[i] = (Byte) (((cCharAt >> 6) & 31) | 192)
                bArr[i + 1] = (Byte) ((cCharAt & '?') | 128)
                i += 2
            } else {
                bArr[i] = (Byte) (((cCharAt >> '\f') & 15) | 224)
                bArr[i + 1] = (Byte) (((cCharAt >> 6) & 63) | 128)
                bArr[i + 2] = (Byte) ((cCharAt & '?') | 128)
                i += 3
            }
        }
        Array<Byte> bArr2 = new Byte[i]
        System.arraycopy(bArr, 0, bArr2, 0, i)
        return bArr2
    }
}

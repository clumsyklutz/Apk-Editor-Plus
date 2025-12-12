package org.b.a

import java.io.IOException
import java.io.InputStream

class a {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f1583a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Int> f1584b
    private final Array<String> c
    private final Int d
    private Int e

    constructor(InputStream inputStream) {
        this(a(inputStream))
    }

    private constructor(Array<Byte> bArr) {
        this(bArr, 0)
    }

    private constructor(Array<Byte> bArr, Int i) {
        Int iA
        this.f1583a = bArr
        this.f1584b = new Int[a(8)]
        Int length = this.f1584b.length
        this.c = new String[length]
        Int i2 = 0
        Int i3 = 1
        Int i4 = 10
        while (i3 < length) {
            this.f1584b[i3] = i4 + 1
            switch (bArr[i4]) {
                case 1:
                    iA = a(i4 + 1) + 3
                    if (iA <= i2) {
                        break
                    } else {
                        i2 = iA
                        break
                    }
                case 2:
                case 7:
                case 8:
                default:
                    iA = 3
                    break
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    iA = 5
                    break
                case 5:
                case 6:
                    iA = 9
                    i3++
                    break
            }
            i3++
            i4 = iA + i4
        }
        this.d = i2
        this.e = i4
    }

    private fun a(Int i) {
        Array<Byte> bArr = this.f1583a
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8)
    }

    private static Array<Byte> a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw IOException("Class not found")
        }
        Array<Byte> bArr = new Byte[inputStream.available()]
        Int i = 0
        while (true) {
            Int i2 = inputStream.read(bArr, i, bArr.length - i)
            if (i2 == -1) {
                if (i >= bArr.length) {
                    return bArr
                }
                Array<Byte> bArr2 = new Byte[i]
                System.arraycopy(bArr, 0, bArr2, 0, i)
                return bArr2
            }
            Int i3 = i2 + i
            if (i3 == bArr.length) {
                Int i4 = inputStream.read()
                if (i4 < 0) {
                    return bArr
                }
                Array<Byte> bArr3 = new Byte[bArr.length + 1000]
                System.arraycopy(bArr, 0, bArr3, 0, i3)
                i = i3 + 1
                bArr3[i3] = (Byte) i4
                bArr = bArr3
            } else {
                i = i3
            }
        }
    }

    public final String a() {
        Int i = this.e + 2
        Array<Char> cArr = new Char[this.d]
        Int iA = a(this.f1584b[a(i)])
        String str = this.c[iA]
        if (str != null) {
            return str
        }
        Int i2 = this.f1584b[iA]
        Array<String> strArr = this.c
        Int i3 = i2 + 2
        Int iA2 = i3 + a(i2)
        Array<Byte> bArr = this.f1583a
        Char c = 0
        Int i4 = 0
        Int i5 = i3
        Char c2 = 0
        while (i5 < iA2) {
            Int i6 = i5 + 1
            Byte b2 = bArr[i5]
            switch (c) {
                case 0:
                    Int i7 = b2 & 255
                    if (i7 < 128) {
                        cArr[i4] = (Char) i7
                        i4++
                        i5 = i6
                    } else if (i7 >= 224 || i7 <= 191) {
                        c2 = (Char) (i7 & 15)
                        c = 2
                        i5 = i6
                        continue
                    } else {
                        c2 = (Char) (i7 & 31)
                        c = 1
                        i5 = i6
                    }
                    break
                case 1:
                    cArr[i4] = (Char) ((b2 & 63) | (c2 << 6))
                    i4++
                    i5 = i6
                    c = 0
                    continue
                case 2:
                    c2 = (Char) ((c2 << 6) | (b2 & 63))
                    c = 1
                    break
            }
            i5 = i6
        }
        String str2 = String(cArr, 0, i4)
        strArr[iA] = str2
        return str2
    }
}

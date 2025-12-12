package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder
import java.util.ArrayList
import java.util.List

class s {
    private static val m = Charset.forName("UTF-16LE").newDecoder()
    private static val n = Charset.forName("UTF-8").newDecoder()

    /* renamed from: a, reason: collision with root package name */
    public Int f806a

    /* renamed from: b, reason: collision with root package name */
    Int f807b
    Int c
    Array<String> d
    private Int f
    private Int g
    private Int h
    private Boolean i
    private Array<Object> j
    private Array<Byte> e = new Byte[28]
    private Array<Int> k = null
    private Array<Int> l = null
    private List o = ArrayList()

    private fun a(Array<Byte> bArr, Int i, Int i2) {
        try {
            return (this.i ? n : m).decode(ByteBuffer.wrap(bArr, i, i2)).toString()
        } catch (Exception e) {
            return null
        }
    }

    public static Array<Byte> a(Int i) {
        return i > 16383 ? new Array<Byte>{(Byte) ((i & 127) | 128), (Byte) (((i >> 7) & 127) | 128), (Byte) (i >> 14)} : i >= 128 ? new Array<Byte>{(Byte) ((i & 127) | 128), (Byte) (i >> 7)} : new Array<Byte>{(Byte) i}
    }

    private static Array<Byte> b(String str) throws UnsupportedEncodingException {
        Array<Byte> bArr
        UnsupportedEncodingException e
        try {
            Array<Byte> bytes = str.getBytes("UTF-8")
            Int length = str.length()
            Int length2 = bytes.length
            Array<Byte> bArrA = a(length)
            Array<Byte> bArrA2 = a(length2)
            bArr = new Byte[bArrA.length + bArrA2.length + bytes.length + 1]
            try {
                System.arraycopy(bArrA, 0, bArr, 0, bArrA.length)
                System.arraycopy(bArrA2, 0, bArr, bArrA.length, bArrA2.length)
                System.arraycopy(bytes, 0, bArr, bArrA2.length + bArrA.length, bytes.length)
            } catch (UnsupportedEncodingException e2) {
                e = e2
                e.printStackTrace()
                return bArr
            }
        } catch (UnsupportedEncodingException e3) {
            bArr = null
            e = e3
        }
        return bArr
    }

    private static Array<Byte> c(String str) throws UnsupportedEncodingException {
        Array<Byte> bArr
        UnsupportedEncodingException e
        Array<Byte> bytes
        Int i
        Int i2 = 0
        try {
            bytes = str.getBytes("UTF-16LE")
            Int length = bytes.length
            if (bytes.length >= 2 && (bytes[0] & 255) == 255 && (bytes[1] & 255) == 254) {
                i2 = 2
                i = length - 2
            } else {
                i = length
            }
            bArr = new Byte[i]
        } catch (UnsupportedEncodingException e2) {
            bArr = null
            e = e2
        }
        try {
            System.arraycopy(bytes, i2, bArr, 0, i)
        } catch (UnsupportedEncodingException e3) {
            e = e3
            e.printStackTrace()
            return bArr
        }
        return bArr
    }

    private Array<Byte> d(String str) {
        return this.i ? b(str) : c(str)
    }

    public final Int a(String str) {
        for (Int i = 0; i < this.d.length; i++) {
            if (this.d[i].equals(str)) {
                return i
            }
        }
        return -1
    }

    public final List a() {
        return this.o
    }

    public final Unit a(Int i, String str) {
        Array<Byte> bArrD = d(str)
        this.d[i] = str
        this.j[i] = bArrD
    }

    public final Unit a(l lVar) throws IOException {
        Array<Byte> bArr
        Array<Int> iArr = new Int[this.f807b]
        Int i = 0
        for (Int i2 = 0; i2 < this.f807b; i2++) {
            iArr[i2] = i
            Int length = ((Array<Byte>) this.j[i2]).length
            i = this.i ? i + length : i + (length / 2 <= 32767 ? 2 : 4) + length + 2
        }
        this.f806a = (this.l != null ? this.l.length : 0) + (this.f > 0 ? this.f << 2 : 0) + (this.f807b << 2) + 28 + i
        Int i3 = (4 - (this.f806a % 4)) % 4
        this.f806a += i3
        e.a(this.e, 4, this.f806a)
        if (this.g > 0) {
            this.g = this.f806a - this.l.length
            e.a(this.e, 24, this.g)
        }
        lVar.a(this.e)
        lVar.a(iArr)
        if (this.f > 0) {
            lVar.a(this.k)
        }
        for (Int i4 = 0; i4 < this.f807b; i4++) {
            Array<Byte> bArr2 = (Array<Byte>) this.j[i4]
            if (this.i) {
                lVar.a(bArr2, 0, bArr2.length)
            } else {
                Int length2 = bArr2.length / 2
                if (length2 > 32767) {
                    bArr = new Byte[4]
                    e.b(bArr, 0, (length2 >> 15) & 32767)
                    e.b(bArr, 2, length2 & 32767)
                } else {
                    bArr = new Byte[2]
                    e.b(bArr, 0, length2)
                }
                lVar.a(bArr)
                lVar.a(bArr2, 0, bArr2.length)
                lVar.b(0)
            }
        }
        if (i3 > 0) {
            switch (i3) {
                case 1:
                    lVar.a(new Array<Byte>{0})
                    break
                case 2:
                    lVar.b(0)
                    break
                case 3:
                    lVar.a(new Array<Byte>{0, 0, 0})
                    break
            }
        }
        if (this.l != null) {
            lVar.a(this.l)
        }
    }

    public final Unit a(m mVar) {
        Int i
        Int i2
        mVar.a(this.e)
        this.f806a = e.a(this.e, 4)
        this.f807b = e.a(this.e, 8)
        this.f = e.a(this.e, 12)
        this.h = e.a(this.e, 16)
        this.c = e.a(this.e, 20)
        this.g = e.a(this.e, 24)
        this.i = (this.h & 256) != 0
        Array<Int> iArr = new Int[this.f807b]
        this.d = new String[this.f807b]
        mVar.a(iArr)
        if (this.f != 0) {
            this.k = new Int[this.f]
            mVar.a(this.k)
        }
        Int i3 = this.c - (((this.f807b * 4) + 28) + (this.f * 4))
        new Object[1][0] = Integer.valueOf(i3)
        if (i3 > 0 && i3 > 0) {
            mVar.f796b.skip(i3)
            mVar.f795a = i3 + mVar.f795a
        }
        Array<Byte> bArr = new Byte[(this.g == 0 ? this.f806a : this.g) - this.c]
        mVar.a(bArr)
        if (this.g > 0) {
            Int i4 = this.f806a - this.g
            this.l = new Int[i4 / 4]
            mVar.a(this.l)
            Int i5 = i4 % 4
            if (i5 > 0) {
                while (true) {
                    Int i6 = i5 - 1
                    if (i5 <= 0) {
                        break
                    }
                    mVar.f796b.read()
                    mVar.f795a++
                    i5 = i6
                }
            }
        }
        this.j = new Object[iArr.length]
        Array<Object> objArr = this.j
        Array<String> strArr = this.d
        for (Int i7 = 0; i7 < iArr.length; i7++) {
            Int i8 = iArr[i7]
            if (this.i) {
                Int i9 = (bArr[i8] & 128) != 0 ? i8 + 2 : i8 + 1
                Int i10 = (bArr[i9] & 128) != 0 ? i9 + 2 : i9 + 1
                Int i11 = 0
                while (bArr[i10 + i11] != 0) {
                    i11++
                }
                Array<Int> iArr2 = {i10, i11}
                Int i12 = iArr2[0] - i8
                i = iArr2[0]
                i2 = iArr2[1]
                objArr[i7] = new Byte[i2 + i12 + 1]
                System.arraycopy(bArr, i - i12, objArr[i7], 0, i12 + i2)
            } else {
                Int i13 = (bArr[i8] & 255) | ((bArr[i8 + 1] & 255) << 8)
                Array<Int> iArr3 = i13 == 32768 ? new Array<Int>{4, (((bArr[i8 + 3] & 255) << 8) + (bArr[i8 + 2] & 255)) << 1} : new Array<Int>{2, i13 << 1}
                i = iArr3[0] + i8
                i2 = iArr3[1]
                objArr[i7] = new Byte[i2]
                System.arraycopy(bArr, i, objArr[i7], 0, i2)
            }
            strArr[i7] = a(bArr, i, i2)
            Array<Object> objArr2 = {Integer.valueOf(i7), strArr[i7]}
        }
    }

    public final Unit a(String str, Int i) {
        StringBuilder("StringChunk.addString: ").append(str).append(", ").append(i)
        this.f806a = ((str.length() + 2) << 1) + 4 + this.f806a
        this.f807b++
        this.c += 4
        e.a(this.e, 4, this.f806a)
        e.a(this.e, 8, this.f807b)
        e.a(this.e, 20, this.c)
        Array<String> strArr = new String[this.f807b]
        Array<Object> objArr = new Object[this.f807b]
        Int i2 = 0
        for (Int i3 = 0; i3 < this.f807b; i3++) {
            if (i3 != i) {
                strArr[i3] = this.d[i2]
                objArr[i3] = this.j[i2]
                i2++
            } else {
                strArr[i3] = str
                objArr[i3] = d(str)
            }
        }
        this.d = strArr
        this.j = objArr
        this.o.add(0, Integer.valueOf(i))
    }

    public final String b(Int i) {
        if (i < 0 || i >= this.d.length) {
            return null
        }
        return this.d[i]
    }

    public final Array<Int> b() {
        Int i = 0
        Int length = this.d.length - this.o.size()
        Array<Int> iArr = new Int[length]
        for (Int i2 = 0; i2 < length; i2++) {
            if (this.o.contains(Integer.valueOf(i2))) {
                i++
            }
            iArr[i2] = i2 + i
        }
        return iArr
    }

    public final Int c() {
        return this.d.length
    }
}

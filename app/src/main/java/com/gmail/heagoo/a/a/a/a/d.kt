package com.gmail.heagoo.a.a.a.a

import android.util.SparseArray
import com.gmail.heagoo.neweditor.Token
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

class d {

    /* renamed from: b, reason: collision with root package name */
    private Array<Int> f731b
    private Array<Byte> c
    private Boolean d
    private Array<Int> e

    /* renamed from: a, reason: collision with root package name */
    private SparseArray f730a = SparseArray()
    private val f = Charset.forName("UTF-16LE").newDecoder()
    private val g = Charset.forName("UTF-8").newDecoder()

    private constructor() {
    }

    private static final Int a(Array<Byte> bArr, Int i) {
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255)
    }

    fun a(c cVar) throws IOException {
        a(cVar, 1835009, 0)
        Int iC = cVar.c()
        Int iC2 = cVar.c()
        Int iC3 = cVar.c()
        Int iC4 = cVar.c()
        Int iC5 = cVar.c()
        Int iC6 = cVar.c()
        d dVar = d()
        dVar.d = (iC4 & 256) != 0
        dVar.f731b = cVar.a(iC2)
        dVar.e = new Int[iC2]
        for (Int i = 0; i < iC2; i++) {
            dVar.e[i] = -1
        }
        if (iC3 != 0) {
            cVar.a(iC3)
        }
        dVar.c = new Byte[(iC6 == 0 ? iC : iC6) - iC5]
        cVar.a(dVar.c)
        if (iC6 != 0) {
            Int i2 = iC - iC6
            cVar.a(i2 / 4)
            Int i3 = i2 % 4
            if (i3 > 0) {
                while (true) {
                    Int i4 = i3 - 1
                    if (i3 <= 0) {
                        break
                    }
                    cVar.b()
                    i3 = i4
                }
            }
        }
        return dVar
    }

    private fun a(Int i, Int i2) {
        try {
            return (this.d ? this.g : this.f).decode(ByteBuffer.wrap(this.c, i, i2)).toString()
        } catch (Exception e) {
            return null
        }
    }

    private fun a(c cVar, Int i, Int i2) throws IOException {
        Int iC = cVar.c()
        if (iC == i2 || iC < i) {
            a(cVar, i, -1)
        } else if (iC != i) {
            throw IOException(String.format("Expected: 0x%08x, actual: 0x%08x", Integer.valueOf(i), Integer.valueOf(iC)))
        }
    }

    public final Int a(String str) {
        if (str == null) {
            return -1
        }
        for (Int i = 0; i != this.f731b.length; i++) {
            Int i2 = this.f731b[i]
            Int iA = a(this.c, i2)
            if (iA == str.length()) {
                Int i3 = i2
                Int i4 = 0
                while (i4 != iA) {
                    i3 += 2
                    if (str.charAt(i4) != a(this.c, i3)) {
                        break
                    }
                    i4++
                }
                if (i4 == iA) {
                    return i
                }
            }
        }
        return -1
    }

    public final String a(Int i) {
        Int i2
        Int i3
        Int i4
        Int i5
        String str = (String) this.f730a.get(i)
        if (str != null) {
            return str
        }
        if (i < 0 || this.f731b == null || i >= this.f731b.length) {
            return null
        }
        Int i6 = this.f731b[i]
        if (this.d) {
            Array<Byte> bArr = this.c
            Int i7 = (bArr[i6] & 128) != 0 ? i6 + 2 : i6 + 1
            Byte b2 = bArr[i7]
            Int i8 = i7 + 1
            if ((b2 & 128) != 0) {
                i4 = (bArr[i8] & 255) + ((b2 & Token.END) << 8)
                i5 = i8 + 1
            } else {
                i4 = b2
                i5 = i8
            }
            Array<Int> iArr = {i5, i4}
            i2 = iArr[0]
            i3 = iArr[1]
        } else {
            Array<Byte> bArr2 = this.c
            Int i9 = ((bArr2[i6 + 1] & 255) << 8) | (bArr2[i6] & 255)
            Array<Int> iArr2 = (32768 & i9) != 0 ? new Array<Int>{4, (((i9 & 32767) << 16) + ((bArr2[i6 + 2] & 255) + ((bArr2[i6 + 3] & 255) << 8))) << 1} : new Array<Int>{2, i9 << 1}
            i2 = i6 + iArr2[0]
            i3 = iArr2[1]
        }
        return a(i2, i3)
    }
}

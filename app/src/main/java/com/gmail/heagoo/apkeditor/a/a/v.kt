package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

class v {

    /* renamed from: a, reason: collision with root package name */
    private static val f812a = Charset.forName("UTF-16LE").newDecoder()

    /* renamed from: b, reason: collision with root package name */
    private static val f813b = Charset.forName("UTF-8").newDecoder()
    private u c
    private Array<Byte> d
    private Int e
    private Int f
    private Int g
    private Int h
    private Int i
    private Int j
    private Boolean k
    private Array<Int> l
    private Array<Int> m
    private Int n = 0

    constructor(u uVar, Array<Byte> bArr) {
        this.c = uVar
        this.d = bArr
        this.e = uVar.f810a
        this.f = a(bArr, 0)
        this.g = a(bArr, 4)
        this.h = a(bArr, 8)
        this.i = a(bArr, 12)
        this.j = a(bArr, 16)
        this.k = (this.h & 256) != 0
        this.l = new Int[this.f]
        Int i = 20
        for (Int i2 = 0; i2 < this.f; i2++) {
            this.l[i2] = a(bArr, i)
            i += 4
        }
        if (this.g != 0) {
            this.m = new Int[this.g]
            for (Int i3 = 0; i3 < this.g; i3++) {
                this.m[i3] = a(bArr, i)
                i += 4
            }
        }
    }

    private fun a(Array<Byte> bArr, Int i) {
        return (bArr[i + 3] << 24) | ((bArr[i + 2] & 255) << 16) | ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255)
    }

    private fun a(Int i, Int i2) {
        try {
            return (this.k ? f813b : f812a).decode(ByteBuffer.wrap(this.d, i, i2)).toString()
        } catch (Exception e) {
            return null
        }
    }

    private fun a(Int i, Int i2, Int i3, Array<Byte> bArr) {
        Int i4
        this.e += bArr.length - i3
        if (this.e % 4 != 0) {
            i4 = 4 - (this.e % 4)
            this.e += i4
        } else {
            i4 = 0
        }
        Int length = bArr.length - i3
        this.c.a(this.e)
        if (this.j > 0 && this.g > 0) {
            this.j += length + i4
            a(this.d, 16, this.j)
        }
        Int i5 = ((i + 1) << 2) + 20
        for (Int i6 = i + 1; i6 < this.f; i6++) {
            Array<Int> iArr = this.l
            iArr[i6] = iArr[i6] + length
            a(this.d, i5, this.l[i6])
            i5 += 4
        }
        Array<Byte> bArr2 = new Byte[this.d.length + length + i4]
        System.arraycopy(this.d, 0, bArr2, 0, i2)
        System.arraycopy(bArr, 0, bArr2, i2, bArr.length)
        if (this.j > 0) {
            Int i7 = ((this.j - length) - i4) - 8
            System.arraycopy(this.d, i2 + i3, bArr2, bArr.length + i2, (i7 - i2) - i3)
            System.arraycopy(this.d, i7, bArr2, i7 + length + i4, this.d.length - i7)
        } else {
            System.arraycopy(this.d, i2 + i3, bArr2, bArr.length + i2, (this.d.length - i2) - i3)
        }
        this.d = bArr2
        this.n = i4 + length + this.n
    }

    private fun a(Array<Byte> bArr, Int i, Int i2) {
        bArr[i] = (Byte) i2
        bArr[i + 1] = (Byte) (i2 >> 8)
        bArr[i + 2] = (Byte) (i2 >> 16)
        bArr[i + 3] = (Byte) (i2 >>> 24)
    }

    private Array<Byte> a(String str) throws UnsupportedEncodingException {
        try {
            Array<Byte> bytes = str.getBytes("UTF-16LE")
            while (bytes.length > 32767) {
                str = str.substring(0, str.length() - 1)
                bytes = str.getBytes("UTF-16LE")
            }
            Int length = str.length()
            Array<Byte> bArr = new Byte[bytes.length + 4]
            bArr[0] = (Byte) length
            bArr[1] = (Byte) (length >> 8)
            System.arraycopy(bytes, 0, bArr, 2, bytes.length)
            bArr[bytes.length + 2] = 0
            bArr[bytes.length + 3] = 0
            return bArr
        } catch (Exception e) {
            e.printStackTrace()
            return null
        }
    }

    private static Array<Byte> b(String str) throws UnsupportedEncodingException {
        try {
            Array<Byte> bytes = str.getBytes("UTF-8")
            while (bytes.length >= 128) {
                str = str.substring(0, str.length() - 1)
                bytes = str.getBytes("UTF-8")
            }
            Int length = str.length()
            Int length2 = bytes.length
            Array<Byte> bArr = new Byte[length2 + 3]
            bArr[0] = (Byte) length
            bArr[1] = (Byte) length2
            System.arraycopy(bytes, 0, bArr, 2, length2)
            bArr[length2 + 2] = 0
            return bArr
        } catch (Exception e) {
            e.printStackTrace()
            return null
        }
    }

    public final Int a() {
        return this.n
    }

    public final Unit a(OutputStream outputStream) throws IOException {
        this.c.a(outputStream)
        outputStream.write(this.d)
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0090  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final Boolean a(java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            r10 = this
            r6 = 1
            r1 = 0
            r0 = r1
            r2 = r1
        L4:
            Int r3 = r10.f
            if (r0 >= r3) goto L92
            if (r0 < 0) goto L90
            Array<Int> r3 = r10.l
            r3 = r3[r0]
            Boolean r4 = r10.k
            if (r4 == 0) goto L50
            Int r4 = r10.i
            Int r3 = r3 + r4
            Int r3 = r3 + (-8)
            Array<Byte> r4 = r10.d
            Array<Int> r7 = a.a.b.b.n.a(r4, r3)
            r4 = r7[r1]
            Int r5 = r4 - r3
            r3 = r7[r6]
        L23:
            java.lang.String r8 = r10.a(r4, r3)
            if (r8 == 0) goto L90
            r7 = 0
            Boolean r9 = r8.equals(r11)
            if (r9 == 0) goto L67
            if (r12 == 0) goto L67
            r7 = r12
        L33:
            if (r7 == 0) goto L90
            Int r8 = r5 + r3
            Boolean r3 = r10.k
            if (r3 == 0) goto L89
            r3 = r6
        L3c:
            Int r8 = r8 + r3
            Boolean r3 = r10.k
            if (r3 == 0) goto L8b
            Array<Byte> r3 = b(r7)
        L45:
            Int r4 = r4 - r5
            r10.a(r0, r4, r8, r3)
            r3 = r6
        L4a:
            if (r3 == 0) goto L4d
            r2 = r6
        L4d:
            Int r0 = r0 + 1
            goto L4
        L50:
            Array<Byte> r4 = r10.d
            Int r5 = r10.i
            Int r5 = r5 + r3
            Int r5 = r5 + (-8)
            Array<Int> r7 = a.a.b.b.n.b(r4, r5)
            r5 = r7[r1]
            Int r4 = r10.i
            Int r3 = r3 + r4
            Int r3 = r3 + (-8)
            Int r4 = r3 + r5
            r3 = r7[r6]
            goto L23
        L67:
            Boolean r9 = r8.startsWith(r13)
            if (r9 == 0) goto L33
            if (r14 == 0) goto L33
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r14)
            Int r9 = r13.length()
            java.lang.String r8 = r8.substring(r9)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            goto L33
        L89:
            r3 = 2
            goto L3c
        L8b:
            Array<Byte> r3 = r10.a(r7)
            goto L45
        L90:
            r3 = r1
            goto L4a
        L92:
            return r2
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.a.a.v.a(java.lang.String, java.lang.String, java.lang.String, java.lang.String):Boolean")
    }
}

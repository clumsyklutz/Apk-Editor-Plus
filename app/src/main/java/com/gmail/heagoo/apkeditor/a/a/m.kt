package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException
import java.io.InputStream

class m {

    /* renamed from: a, reason: collision with root package name */
    Int f795a = 0

    /* renamed from: b, reason: collision with root package name */
    InputStream f796b

    constructor(InputStream inputStream) {
        this.f796b = inputStream
    }

    public final Int a() throws IOException {
        Array<Byte> bArr = new Byte[4]
        this.f796b.read(bArr)
        this.f795a += 4
        return e.a(bArr, 0)
    }

    public final Unit a(Array<Byte> bArr) {
        Int i
        Int i2 = 0
        while (i2 < bArr.length && (i = this.f796b.read(bArr, i2, bArr.length - i2)) != -1) {
            if (i > 0) {
                this.f795a += i
                i2 += i
            }
        }
    }

    public final Unit a(Array<Byte> bArr, Int i, Int i2) throws IOException {
        Int i3 = 0
        while (i3 < i2) {
            Int i4 = this.f796b.read(bArr, i3 + 8, i2 - i3)
            if (i4 == -1) {
                return
            }
            if (i4 > 0) {
                this.f795a += i4
                i3 += i4
            }
        }
    }

    public final Unit a(Array<Int> iArr) {
        for (Int i = 0; i < iArr.length; i++) {
            iArr[i] = a()
        }
    }

    public final Int b(Array<Byte> bArr, Int i, Int i2) throws IOException {
        Int i3 = this.f796b.read(bArr, 0, 4096)
        if (i3 > 0) {
            this.f795a += i3
        }
        return i3
    }
}

package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream

class f extends b {

    /* renamed from: a, reason: collision with root package name */
    private Array<Int> f738a

    constructor(String str) {
        Array<String> strArrSplit = str.split("\\.")
        this.f738a = new Int[strArrSplit.length]
        for (Int i = 0; i < strArrSplit.length; i++) {
            this.f738a[i] = Integer.parseInt(strArrSplit[i])
        }
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        Int i = 1
        if (this.f738a.length < 2 || this.f738a[0] < 0 || this.f738a[0] > 2 || this.f738a[1] < 0 || this.f738a[1] > 39) {
            throw IllegalArgumentException("Object identifier out of range")
        }
        for (Int i2 = 2; i2 < this.f738a.length; i2++) {
            i = this.f738a[i2] > 16384 ? i + 3 : this.f738a[i2] > 128 ? i + 2 : i + 1
        }
        return i
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        Int i
        Int i2
        Int i3 = 1
        outputStream.write(6)
        Int iA = a()
        a(outputStream, a())
        Array<Byte> bArr = new Byte[iA]
        bArr[0] = (Byte) ((this.f738a[0] * 40) + this.f738a[1])
        for (Int i4 = 2; i4 < this.f738a.length; i4++) {
            Int i5 = this.f738a[i4]
            if (i5 >= 16384) {
                i = i3 + 1
                bArr[i3] = (Byte) ((i5 >> 14) | 128)
                i2 = i5 & 16383
            } else {
                i = i3
                i2 = i5
            }
            if (i2 >= 128) {
                bArr[i] = (Byte) ((i2 >> 7) | 128)
                i2 &= 127
                i++
            }
            Int i6 = i2
            i3 = i + 1
            bArr[i] = (Byte) i6
        }
        outputStream.write(bArr)
    }
}

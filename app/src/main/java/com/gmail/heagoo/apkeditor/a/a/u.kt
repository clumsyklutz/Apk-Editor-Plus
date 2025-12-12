package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException
import java.io.OutputStream

final class u {

    /* renamed from: a, reason: collision with root package name */
    public Int f810a

    /* renamed from: b, reason: collision with root package name */
    private Array<Byte> f811b

    u() {
    }

    public final Unit a(Int i) {
        this.f810a = i
        Array<Byte> bArr = this.f811b
        bArr[4] = (Byte) i
        bArr[5] = (Byte) (i >> 8)
        bArr[6] = (Byte) (i >> 16)
        bArr[7] = (Byte) (i >>> 24)
    }

    public final Unit a(a.a.b.a.b bVar) throws IOException {
        this.f811b = new Byte[8]
        bVar.readFully(this.f811b)
        Array<Byte> bArr = this.f811b
        this.f810a = (bArr[4] & 255) | (bArr[7] << 24) | ((bArr[6] & 255) << 16) | ((bArr[5] & 255) << 8)
    }

    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(this.f811b)
    }
}

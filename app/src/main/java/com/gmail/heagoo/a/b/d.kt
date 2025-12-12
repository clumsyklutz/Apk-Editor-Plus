package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream
import java.math.BigInteger

class d extends b {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f736a

    /* renamed from: b, reason: collision with root package name */
    private BigInteger f737b

    constructor(Int i) {
        this.f737b = BigInteger.valueOf(1L)
        this.f736a = this.f737b.toByteArray()
    }

    constructor(BigInteger bigInteger) {
        this.f737b = bigInteger
        this.f736a = this.f737b.toByteArray()
    }

    private fun c() {
        return this.f737b.signum() > 0 && (this.f736a[0] & 128) != 0
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        return !c() ? this.f736a.length : this.f736a.length + 1
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(2)
        a(outputStream, a())
        if (c()) {
            outputStream.write(0)
        }
        outputStream.write(this.f736a)
    }
}

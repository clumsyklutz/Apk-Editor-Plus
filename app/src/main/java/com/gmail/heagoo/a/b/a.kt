package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream

class a extends b {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f733a

    /* renamed from: b, reason: collision with root package name */
    private b f734b

    constructor(b bVar) {
        this.f734b = bVar
        this.f733a = null
    }

    constructor(Array<Byte> bArr) {
        this.f734b = null
        this.f733a = bArr
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        return this.f734b == null ? this.f733a.length : this.f734b.b()
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(160)
        a(outputStream, a())
        if (this.f734b == null) {
            outputStream.write(this.f733a)
        } else {
            this.f734b.a(outputStream)
        }
    }
}

package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream

class g extends b {

    /* renamed from: a, reason: collision with root package name */
    private Array<Byte> f739a

    constructor(Array<Byte> bArr) {
        this.f739a = bArr
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        return this.f739a.length
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(4)
        a(outputStream, this.f739a.length)
        outputStream.write(this.f739a)
    }
}

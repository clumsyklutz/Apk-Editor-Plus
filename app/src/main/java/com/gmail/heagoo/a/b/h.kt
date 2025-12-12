package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream

class h extends b {

    /* renamed from: a, reason: collision with root package name */
    private String f740a

    constructor(String str) {
        this.f740a = str
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        return this.f740a.length()
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(19)
        a(outputStream, this.f740a.length())
        outputStream.write(this.f740a.getBytes())
    }
}

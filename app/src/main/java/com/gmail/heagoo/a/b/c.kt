package com.gmail.heagoo.a.b

import java.io.IOException
import java.io.OutputStream

class c extends b {

    /* renamed from: a, reason: collision with root package name */
    private String f735a

    constructor(String str) {
        this.f735a = str
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Int a() {
        return this.f735a.length()
    }

    @Override // com.gmail.heagoo.a.b.b
    public final Unit a(OutputStream outputStream) throws IOException {
        outputStream.write(22)
        a(outputStream, this.f735a.length())
        outputStream.write(this.f735a.getBytes())
    }
}

package com.gmail.heagoo.apkeditor.a.a

import java.io.IOException

class r {

    /* renamed from: a, reason: collision with root package name */
    public Array<Int> f804a

    /* renamed from: b, reason: collision with root package name */
    private Int f805b
    private Int c

    public final Int a() {
        if (this.f804a != null) {
            return this.f804a.length
        }
        return 0
    }

    public final Unit a(Int i, Int i2) {
        this.c += 4
        Array<Int> iArr = new Int[this.f804a.length + 1]
        iArr[0] = 16843447
        for (Int i3 = 1; i3 < iArr.length; i3++) {
            iArr[i3] = this.f804a[i3 - 1]
        }
        this.f804a = iArr
    }

    public final Unit a(l lVar) throws IOException {
        lVar.a(this.f805b)
        lVar.a(this.c)
        lVar.a(this.f804a)
    }

    public final Unit a(m mVar) {
        this.f805b = mVar.a()
        this.c = mVar.a()
        Int i = (this.c - 8) / 4
        this.f804a = new Int[i]
        StringBuilder("Attr Count: ").append(i)
        for (Int i2 = 0; i2 < i; i2++) {
            this.f804a[i2] = mVar.a()
            StringBuilder("\t").append(this.f804a[i2])
        }
    }
}

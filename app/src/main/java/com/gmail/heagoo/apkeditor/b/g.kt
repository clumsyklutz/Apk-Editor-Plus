package com.gmail.heagoo.apkeditor.b

import com.gmail.heagoo.apkeditor.a.a.s

class g {

    /* renamed from: a, reason: collision with root package name */
    Array<Byte> f875a

    /* renamed from: b, reason: collision with root package name */
    Int f876b
    Int c
    String d

    constructor(Int i, String str) {
        this.d = str
    }

    constructor(Int i, String str, Array<Byte> bArr, Int i2, Int i3) {
        this.d = str
        this.f875a = bArr
        this.f876b = i2
        this.c = i3
    }

    private fun b() {
        Array<Int> iArr = new Int[1]
        d dVar = d(this.f875a, this.f876b)
        Int iA = 0
        try {
            iA = dVar.a()
        } catch (i e) {
            e.printStackTrace()
        }
        this.d = j.a(this.f875a, dVar.b(), iA, iArr)
    }

    private fun c() {
        Array<Byte> bArrA = s.a(this.d.length())
        Array<Byte> bArrA2 = j.a(this.d)
        this.f875a = new Byte[bArrA.length + bArrA2.length + 1]
        System.arraycopy(bArrA, 0, this.f875a, 0, bArrA.length)
        System.arraycopy(bArrA2, 0, this.f875a, bArrA.length, bArrA2.length)
        this.f876b = 0
        this.c = bArrA.length + bArrA2.length + 1
    }

    public final Int a() {
        if (this.f875a != null && this.c > 0) {
            return this.c
        }
        c()
        return this.c
    }

    public final Int a(g gVar) {
        if (this.d == null) {
            b()
        }
        if (gVar.d == null) {
            gVar.b()
        }
        return this.d.compareTo(gVar.d)
    }

    public final Unit a(String str) {
        this.d = str
        c()
    }
}

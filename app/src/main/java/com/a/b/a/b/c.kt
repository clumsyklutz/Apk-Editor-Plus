package com.a.b.a.b

class c implements com.a.b.h.l {

    /* renamed from: a, reason: collision with root package name */
    private final Int f197a

    /* renamed from: b, reason: collision with root package name */
    private final Int f198b
    private final Int c
    private final com.a.b.h.j d
    private final e e

    constructor(Int i, Int i2, Int i3, com.a.b.h.j jVar, e eVar) {
        if (i < 0) {
            throw IllegalArgumentException("label < 0")
        }
        if (i2 < 0) {
            throw IllegalArgumentException("start < 0")
        }
        if (i3 <= i2) {
            throw IllegalArgumentException("end <= start")
        }
        if (jVar == null) {
            throw NullPointerException("targets == null")
        }
        Int iB = jVar.b()
        for (Int i4 = 0; i4 < iB; i4++) {
            if (jVar.b(i4) < 0) {
                throw IllegalArgumentException("successors[" + i4 + "] == " + jVar.b(i4))
            }
        }
        if (eVar == null) {
            throw NullPointerException("catches == null")
        }
        this.f197a = i
        this.f198b = i2
        this.c = i3
        this.d = jVar
        this.e = eVar
    }

    @Override // com.a.b.h.l
    public final Int a() {
        return this.f197a
    }

    public final Int b() {
        return this.f198b
    }

    public final Int c() {
        return this.c
    }

    public final com.a.b.h.j d() {
        return this.d
    }

    public final e e() {
        return this.e
    }

    public final String toString() {
        return "{" + com.gmail.heagoo.a.c.a.v(this.f197a) + ": " + com.gmail.heagoo.a.c.a.v(this.f198b) + ".." + com.gmail.heagoo.a.c.a.v(this.c) + '}'
    }
}

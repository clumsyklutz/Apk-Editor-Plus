package com.a.b.a.b

class w implements com.a.b.f.d.d {

    /* renamed from: a, reason: collision with root package name */
    private final Int f224a

    constructor(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("subroutineAddress < 0")
        }
        this.f224a = i
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.k
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.d b() {
        return this
    }

    @Override // com.a.b.f.d.d
    public final Int c() {
        return com.a.b.f.d.c.k.c()
    }

    @Override // com.a.b.h.s
    public final String d() {
        return toString()
    }

    @Override // com.a.b.f.d.d
    public final Int e() {
        return com.a.b.f.d.c.k.e()
    }

    public final Boolean equals(Object obj) {
        return (obj is w) && this.f224a == ((w) obj).f224a
    }

    @Override // com.a.b.f.d.d
    public final Boolean f() {
        return false
    }

    public final Int g() {
        return this.f224a
    }

    public final Int hashCode() {
        return this.f224a
    }

    public final String toString() {
        return "<addr:" + com.gmail.heagoo.a.c.a.v(this.f224a) + ">"
    }
}

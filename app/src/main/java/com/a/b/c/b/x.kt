package com.a.b.c.b

class x extends an {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.b.r f342a

    constructor(com.a.b.f.b.z zVar, com.a.b.f.b.r rVar) {
        super(zVar)
        if (rVar == null) {
            throw NullPointerException("local == null")
        }
        this.f342a = rVar
    }

    fun a(com.a.b.f.b.r rVar) {
        return rVar.m() + ' ' + rVar.i().toString() + ": " + rVar.h().d()
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        return x(i(), this.f342a)
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.g.ag agVar) {
        return x(i(), agVar.a(this.f342a))
    }

    @Override // com.a.b.c.b.l
    protected final String a(Boolean z) {
        return "local-start " + a(this.f342a)
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        return this.f342a.toString()
    }

    public final com.a.b.f.b.r c() {
        return this.f342a
    }

    @Override // com.a.b.c.b.an, com.a.b.c.b.l
    public final l d(Int i) {
        return x(i(), this.f342a.b(i))
    }
}

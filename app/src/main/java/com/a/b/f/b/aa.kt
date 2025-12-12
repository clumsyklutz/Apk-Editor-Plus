package com.a.b.f.b

class aa extends i {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.h.j f495a

    constructor(w wVar, z zVar, r rVar, t tVar, com.a.b.h.j jVar) {
        super(wVar, zVar, rVar, tVar)
        if (wVar.d() != 5) {
            throw IllegalArgumentException("bogus branchingness")
        }
        if (jVar == null) {
            throw NullPointerException("cases == null")
        }
        this.f495a = jVar
    }

    @Override // com.a.b.f.b.i
    public final i a(r rVar, t tVar) {
        return aa(f(), g(), rVar, tVar, this.f495a)
    }

    @Override // com.a.b.f.b.i
    public final i a(com.a.b.f.d.c cVar) {
        throw UnsupportedOperationException("unsupported")
    }

    @Override // com.a.b.f.b.i
    public final String a() {
        return this.f495a.toString()
    }

    @Override // com.a.b.f.b.i
    public final Unit a(k kVar) {
        kVar.a(this)
    }

    @Override // com.a.b.f.b.i
    public final Boolean a(i iVar) {
        return false
    }

    @Override // com.a.b.f.b.i
    public final com.a.b.f.d.e b() {
        return com.a.b.f.d.b.f565a
    }

    public final com.a.b.h.j c() {
        return this.f495a
    }
}

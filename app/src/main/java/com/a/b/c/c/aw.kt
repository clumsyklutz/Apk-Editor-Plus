package com.a.b.c.c

class aw extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.y f375a

    constructor(com.a.b.f.c.y yVar) {
        super(1, com.gmail.heagoo.a.c.a.d(yVar.m()) + yVar.l() + 1)
        this.f375a = yVar
    }

    @Override // com.a.b.c.c.ap
    protected final Int a(ap apVar) {
        return this.f375a.compareTo(((aw) apVar).f375a)
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.n
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
    }

    @Override // com.a.b.c.c.ap
    public final Unit a_(r rVar, com.a.b.h.r rVar2) {
        com.a.b.h.c cVarK = this.f375a.k()
        Int iM = this.f375a.m()
        if (rVar2.b()) {
            rVar2.a(com.gmail.heagoo.a.c.a.d(iM), "utf16_size: " + com.gmail.heagoo.a.c.a.t(iM))
            rVar2.a(cVarK.a() + 1, this.f375a.i())
        }
        rVar2.e(iM)
        rVar2.a(cVarK)
        rVar2.d(0)
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return this.f375a.i()
    }
}

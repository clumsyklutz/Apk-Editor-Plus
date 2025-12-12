package com.a.b.c.c

abstract class ag extends ab {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.u f354a

    constructor(com.a.b.f.c.u uVar) {
        super(uVar.k())
        this.f354a = uVar
    }

    @Override // com.a.b.c.c.ab, com.a.b.c.c.ad
    fun a(r rVar) {
        super.a(rVar)
        rVar.h().a(this.f354a.l().a())
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        ba baVarK = rVar.k()
        ay ayVarH = rVar.h()
        com.a.b.f.c.w wVarL = this.f354a.l()
        Int iB = baVarK.b(d())
        Int iB2 = ayVarH.b(wVarL.a())
        Int iB3 = b(rVar)
        if (rVar2.b()) {
            rVar2.a(0, j() + ' ' + this.f354a.d())
            rVar2.a(2, "  class_idx: " + com.gmail.heagoo.a.c.a.v(iB))
            rVar2.a(2, String.format("  %-10s %s", c() + ':', com.gmail.heagoo.a.c.a.v(iB3)))
            rVar2.a(4, "  name_idx:  " + com.gmail.heagoo.a.c.a.t(iB2))
        }
        rVar2.b(iB)
        rVar2.b(iB3)
        rVar2.c(iB2)
    }

    protected abstract Int b(r rVar)

    protected abstract String c()

    public final com.a.b.f.c.u e() {
        return this.f354a
    }

    @Override // com.a.b.c.c.ad
    public final Int e_() {
        return 8
    }
}

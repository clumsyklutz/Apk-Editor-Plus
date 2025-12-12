package com.a.b.c.c

class e extends ap {

    /* renamed from: a, reason: collision with root package name */
    private d f388a

    constructor(d dVar) {
        super(4, 4)
        this.f388a = dVar
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.t
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        this.f388a = (d) rVar.e().b(this.f388a)
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Int iF = this.f388a.f()
        if (rVar2.b()) {
            rVar2.a(4, "  annotations_off: " + com.gmail.heagoo.a.c.a.t(iF))
        }
        rVar2.c(iF)
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return this.f388a.b()
    }
}

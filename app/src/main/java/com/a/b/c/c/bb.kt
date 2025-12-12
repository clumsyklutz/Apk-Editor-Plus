package com.a.b.c.c

class bb extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.e f381a

    constructor(com.a.b.f.d.e eVar) {
        super(4, (eVar.d_() << 1) + 4)
        this.f381a = eVar
    }

    @Override // com.a.b.c.c.ap
    protected final Int a(ap apVar) {
        return com.a.b.f.d.b.b(this.f381a, ((bb) apVar).f381a)
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.i
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        ba baVarK = rVar.k()
        Int iD_ = this.f381a.d_()
        for (Int i = 0; i < iD_; i++) {
            baVarK.a(this.f381a.a(i))
        }
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        ba baVarK = rVar.k()
        Int iD_ = this.f381a.d_()
        if (rVar2.b()) {
            rVar2.a(0, h() + " type_list")
            rVar2.a(4, "  size: " + com.gmail.heagoo.a.c.a.t(iD_))
            for (Int i = 0; i < iD_; i++) {
                com.a.b.f.d.c cVarA = this.f381a.a(i)
                rVar2.a(2, "  " + com.gmail.heagoo.a.c.a.v(baVarK.b(cVarA)) + " // " + cVarA.d())
            }
        }
        rVar2.c(iD_)
        for (Int i2 = 0; i2 < iD_; i2++) {
            rVar2.b(baVarK.b(this.f381a.a(i2)))
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        throw RuntimeException("unsupported")
    }

    public final com.a.b.f.d.e c() {
        return this.f381a
    }

    public final Int hashCode() {
        return com.a.b.f.d.b.b(this.f381a)
    }
}

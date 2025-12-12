package com.a.b.c.c

class s extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.d f410a

    /* renamed from: b, reason: collision with root package name */
    private Array<Byte> f411b

    constructor(com.a.b.f.c.d dVar) {
        super(1, -1)
        if (dVar == null) {
            throw NullPointerException("array == null")
        }
        this.f410a = dVar
        this.f411b = null
    }

    @Override // com.a.b.c.c.ap
    protected final Int a(ap apVar) {
        return this.f410a.compareTo(((s) apVar).f410a)
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.q
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        com.a.b.h.r rVar = new com.a.b.h.r()
        be(atVar.e(), rVar).a(this.f410a, false)
        this.f411b = rVar.g()
        a(this.f411b.length)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        be.a(rVar, this.f410a)
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        if (!rVar2.b()) {
            rVar2.a(this.f411b)
        } else {
            rVar2.a(0, h() + " encoded array")
            be(rVar, rVar2).a(this.f410a, true)
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return this.f410a.d()
    }

    public final Int hashCode() {
        return this.f410a.hashCode()
    }
}

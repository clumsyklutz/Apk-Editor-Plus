package com.a.b.f.b

class ab extends e {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.e f496a

    constructor(w wVar, z zVar, t tVar, com.a.b.f.d.e eVar, com.a.b.f.c.a aVar) {
        super(wVar, zVar, null, tVar, aVar)
        if (wVar.d() != 6) {
            throw IllegalArgumentException("bogus branchingness")
        }
        if (eVar == null) {
            throw NullPointerException("catches == null")
        }
        this.f496a = eVar
    }

    @Override // com.a.b.f.b.i
    public final i a(r rVar, t tVar) {
        return ab(f(), g(), tVar, this.f496a, g_())
    }

    @Override // com.a.b.f.b.i
    public final i a(com.a.b.f.d.c cVar) {
        return ab(f(), g(), j(), this.f496a.a(cVar), g_())
    }

    @Override // com.a.b.f.b.e, com.a.b.f.b.i
    public final String a() {
        com.a.b.f.c.a aVarG_ = g_()
        return (aVarG_ is com.a.b.f.c.y ? ((com.a.b.f.c.y) aVarG_).i() : aVarG_.d()) + " " + ac.a(this.f496a)
    }

    @Override // com.a.b.f.b.i
    public final Unit a(k kVar) {
        kVar.a(this)
    }

    @Override // com.a.b.f.b.i
    public final com.a.b.f.d.e b() {
        return this.f496a
    }
}

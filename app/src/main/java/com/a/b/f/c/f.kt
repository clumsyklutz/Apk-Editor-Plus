package com.a.b.f.c

abstract class f extends u {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.a f535a

    /* renamed from: b, reason: collision with root package name */
    private com.a.b.f.d.a f536b

    f(z zVar, w wVar) {
        super(zVar, wVar)
        this.f535a = com.a.b.f.d.a.a(l().b().j())
        this.f536b = null
    }

    public final com.a.b.f.d.a a(Boolean z) {
        if (z) {
            return this.f535a
        }
        if (this.f536b == null) {
            this.f536b = this.f535a.a(k().i())
        }
        return this.f536b
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return this.f535a.a()
    }

    @Override // com.a.b.f.c.u, com.a.b.f.c.a
    protected final Int b(a aVar) {
        Int iB = super.b(aVar)
        return iB != 0 ? iB : this.f535a.compareTo(((f) aVar).f535a)
    }

    public final Int b(Boolean z) {
        return a(z).b().e()
    }

    public final com.a.b.f.d.a i() {
        return this.f535a
    }
}

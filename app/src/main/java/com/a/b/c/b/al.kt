package com.a.b.c.b

class al extends p {

    /* renamed from: a, reason: collision with root package name */
    private h f312a

    constructor(n nVar, com.a.b.f.b.z zVar, com.a.b.f.b.t tVar, h hVar) {
        super(nVar, zVar, tVar)
        if (hVar == null) {
            throw NullPointerException("target == null")
        }
        this.f312a = hVar
    }

    public final al a(h hVar) {
        return al(h().g(), i(), j(), hVar)
    }

    @Override // com.a.b.c.b.l
    public final l a(n nVar) {
        return al(nVar, i(), j(), this.f312a)
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        return al(h(), i(), tVar, this.f312a)
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        return this.f312a == null ? "????" : this.f312a.l()
    }

    public final h c() {
        return this.f312a
    }

    public final Int d() {
        return this.f312a.g()
    }

    public final Int e() {
        return this.f312a.g() - g()
    }

    public final Boolean n() {
        return f() && this.f312a.f()
    }
}

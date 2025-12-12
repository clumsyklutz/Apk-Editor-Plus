package com.a.b.c.b

class b {

    /* renamed from: a, reason: collision with root package name */
    private final Array<h> f313a

    /* renamed from: b, reason: collision with root package name */
    private final Array<h> f314b
    private final Array<h> c

    constructor(com.a.b.f.b.x xVar) {
        Int iJ = xVar.a().j()
        this.f313a = new h[iJ]
        this.f314b = new h[iJ]
        this.c = new h[iJ]
        a(xVar)
    }

    private fun a(com.a.b.f.b.x xVar) {
        com.a.b.f.b.c cVarA = xVar.a()
        Int iD_ = cVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.b.a aVarA = cVarA.a(i)
            Int iA = aVarA.a()
            this.f313a[iA] = h(aVarA.b().a(0).g())
            com.a.b.f.b.z zVarG = aVarA.g().g()
            this.f314b[iA] = h(zVarG)
            this.c[iA] = h(zVarG)
        }
    }

    public final h a(Int i) {
        return this.f313a[i]
    }

    public final h a(com.a.b.f.b.a aVar) {
        return this.f313a[aVar.a()]
    }

    public final h b(com.a.b.f.b.a aVar) {
        return this.f314b[aVar.a()]
    }

    public final h c(com.a.b.f.b.a aVar) {
        return this.c[aVar.a()]
    }
}

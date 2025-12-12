package com.a.b.c.b

class i extends p {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.a f322a

    /* renamed from: b, reason: collision with root package name */
    private Int f323b
    private Int c

    constructor(n nVar, com.a.b.f.b.z zVar, com.a.b.f.b.t tVar, com.a.b.f.c.a aVar) {
        super(nVar, zVar, tVar)
        if (aVar == null) {
            throw NullPointerException("constant == null")
        }
        this.f322a = aVar
        this.f323b = -1
        this.c = -1
    }

    @Override // com.a.b.c.b.l
    public final l a(n nVar) {
        i iVar = i(nVar, i(), j(), this.f322a)
        if (this.f323b >= 0) {
            iVar.a(this.f323b)
        }
        if (this.c >= 0) {
            iVar.b(this.c)
        }
        return iVar
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        i iVar = i(h(), i(), tVar, this.f322a)
        if (this.f323b >= 0) {
            iVar.a(this.f323b)
        }
        if (this.c >= 0) {
            iVar.b(this.c)
        }
        return iVar
    }

    public final Unit a(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("index < 0")
        }
        if (this.f323b >= 0) {
            throw RuntimeException("index already set")
        }
        this.f323b = i
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        return this.f322a.d()
    }

    public final Unit b(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("index < 0")
        }
        if (this.c >= 0) {
            throw RuntimeException("class index already set")
        }
        this.c = i
    }

    public final com.a.b.f.c.a c() {
        return this.f322a
    }

    public final Int d() {
        if (this.f323b < 0) {
            throw RuntimeException("index not yet set for " + this.f322a)
        }
        return this.f323b
    }

    public final Boolean e() {
        return this.f323b >= 0
    }
}

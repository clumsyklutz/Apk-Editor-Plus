package com.a.b.a.b

class v extends s {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.Array<d> f223a

    constructor(Int i) {
        super(i != 0)
        this.f223a = new com.a.b.f.d.d[i]
    }

    @Override // com.a.b.a.b.s
    public final s a(s sVar) {
        return sVar is v ? a((v) sVar) : sVar.a(this)
    }

    @Override // com.a.b.a.b.s
    public final t a(s sVar, Int i) {
        return t(this.f223a.length).a(sVar, i)
    }

    public final v a(v vVar) {
        try {
            return com.gmail.heagoo.a.c.a.a(this, vVar)
        } catch (ah e) {
            e.a("underlay locals:")
            a(e)
            e.a("overlay locals:")
            vVar.a(e)
            throw e
        }
    }

    @Override // com.a.b.a.b.s
    public final com.a.b.f.d.d a(Int i) {
        com.a.b.f.d.d dVar = this.f223a[i]
        if (dVar == null) {
            throw ah("local " + com.gmail.heagoo.a.c.a.v(i) + ": invalid")
        }
        return dVar
    }

    @Override // com.a.b.a.b.s
    public final Unit a(Int i, com.a.b.f.d.d dVar) {
        com.a.b.f.d.d dVar2
        l()
        try {
            com.a.b.f.d.d dVarB = dVar.b()
            if (i < 0) {
                throw IndexOutOfBoundsException("idx < 0")
            }
            if (dVarB.a().k()) {
                this.f223a[i + 1] = null
            }
            this.f223a[i] = dVarB
            if (i == 0 || (dVar2 = this.f223a[i - 1]) == null || !dVar2.a().k()) {
                return
            }
            this.f223a[i - 1] = null
        } catch (NullPointerException e) {
            throw NullPointerException("type == null")
        }
    }

    @Override // com.a.b.a.b.s
    public final Unit a(com.a.a.a.d dVar) {
        for (Int i = 0; i < this.f223a.length; i++) {
            com.a.b.f.d.d dVar2 = this.f223a[i]
            dVar.a("locals[" + com.gmail.heagoo.a.c.a.v(i) + "]: " + (dVar2 == null ? "<invalid>" : dVar2.toString()))
        }
    }

    @Override // com.a.b.a.b.s
    public final Unit a(com.a.b.f.b.r rVar) {
        a(rVar.g(), rVar)
    }

    @Override // com.a.b.a.b.s
    public final Unit a(com.a.b.f.d.c cVar) {
        Int length = this.f223a.length
        if (length == 0) {
            return
        }
        l()
        com.a.b.f.d.c cVarQ = cVar.q()
        for (Int i = 0; i < length; i++) {
            if (this.f223a[i] == cVar) {
                this.f223a[i] = cVarQ
            }
        }
    }

    @Override // com.a.b.a.b.s
    protected final v b() {
        return this
    }

    public final Unit b(Int i) {
        l()
        this.f223a[i] = null
    }

    public final com.a.b.f.d.d c(Int i) {
        return this.f223a[i]
    }

    @Override // com.a.b.h.s
    public final String d() {
        StringBuilder sb = StringBuilder()
        for (Int i = 0; i < this.f223a.length; i++) {
            com.a.b.f.d.d dVar = this.f223a[i]
            sb.append("locals[" + com.gmail.heagoo.a.c.a.v(i) + "]: " + (dVar == null ? "<invalid>" : dVar.toString()) + "\n")
        }
        return sb.toString()
    }

    @Override // com.a.b.a.b.s
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public final v a() {
        v vVar = v(this.f223a.length)
        System.arraycopy(this.f223a, 0, vVar.f223a, 0, this.f223a.length)
        return vVar
    }

    public final Int f() {
        return this.f223a.length
    }
}

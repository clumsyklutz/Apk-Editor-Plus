package com.a.b.g

class a extends ag {

    /* renamed from: a, reason: collision with root package name */
    private com.a.b.h.j f569a

    /* renamed from: b, reason: collision with root package name */
    private Int f570b

    constructor(Int i) {
        this.f569a = new com.a.b.h.j(i)
    }

    @Override // com.a.b.g.ag
    public final Int a() {
        return this.f570b
    }

    public final Int a(Int i) {
        if (i >= this.f569a.b()) {
            return -1
        }
        return this.f569a.b(i)
    }

    @Override // com.a.b.g.ag
    public final com.a.b.f.b.r a(com.a.b.f.b.r rVar) {
        Int iB
        if (rVar == null) {
            return null
        }
        try {
            iB = this.f569a.b(rVar.g())
        } catch (IndexOutOfBoundsException e) {
            iB = -1
        }
        if (iB < 0) {
            throw RuntimeException("no mapping specified for register")
        }
        return rVar.a(iB)
    }

    fun a(Int i, Int i2, Int i3) {
        if (i >= this.f569a.b()) {
            for (Int iB = i - this.f569a.b(); iB >= 0; iB--) {
                this.f569a.c(-1)
            }
        }
        this.f569a.b(i, i2)
        if (this.f570b < i2 + i3) {
            this.f570b = i2 + i3
        }
    }
}

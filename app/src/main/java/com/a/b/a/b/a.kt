package com.a.b.a.b

import java.util.ArrayList

abstract class a implements u {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.a f175a

    /* renamed from: b, reason: collision with root package name */
    private com.a.b.f.d.Array<d> f176b
    private Int c
    private com.a.b.f.d.c d
    private Int e
    private com.a.b.f.c.a f
    private Int g
    private ak h
    private ArrayList i
    private Int j
    private Boolean k
    private com.a.b.f.b.r l
    private com.a.b.f.d.Array<d> m
    private Int n

    constructor(com.a.b.f.d.a aVar) {
        if (aVar == null) {
            throw NullPointerException("prototype == null")
        }
        this.f175a = aVar
        this.f176b = new com.a.b.f.d.d[10]
        this.m = new com.a.b.f.d.d[6]
        b()
    }

    fun a(com.a.b.f.d.d dVar, com.a.b.f.d.d dVar2) {
        throw ah("local variable type mismatch: attempt to set or access a value of type " + dVar.d() + " using a local variable of type " + dVar2.d() + ". This is symptomatic of .class transformation tools that ignore local variable information.")
    }

    @Override // com.a.b.a.b.u
    public final com.a.b.f.d.a a() {
        return this.f175a
    }

    @Override // com.a.b.a.b.u
    public final Unit a(Int i) {
        this.e = i
    }

    @Override // com.a.b.a.b.u
    public final Unit a(Int i, com.a.b.f.d.c cVar, com.a.b.f.b.m mVar) {
        this.l = com.a.b.f.b.r.b(i, cVar, mVar)
    }

    @Override // com.a.b.a.b.u
    public final Unit a(ak akVar) {
        if (akVar == null) {
            throw NullPointerException("cases == null")
        }
        this.h = akVar
    }

    protected final Unit a(n nVar) {
        if (this.n < 0) {
            throw ah("results never set")
        }
        if (this.n == 0) {
            return
        }
        if (this.l != null) {
            nVar.c().a(b(false))
            return
        }
        m mVarD = nVar.d()
        for (Int i = 0; i < this.n; i++) {
            if (this.k) {
                mVarD.d()
            }
            mVarD.a(this.m[i])
        }
    }

    @Override // com.a.b.a.b.u
    public final Unit a(n nVar, Int i) {
        m mVarD = nVar.d()
        b()
        if (i > this.f176b.length) {
            this.f176b = new com.a.b.f.d.d[i + 10]
        }
        for (Int i2 = i - 1; i2 >= 0; i2--) {
            this.f176b[i2] = mVarD.e()
        }
        this.c = i
    }

    @Override // com.a.b.a.b.u
    public final Unit a(n nVar, com.a.b.f.d.a aVar) {
        com.a.b.f.d.b bVarB = aVar.b()
        Int iD_ = bVarB.d_()
        a(nVar, iD_)
        for (Int i = 0; i < iD_; i++) {
            if (!com.gmail.heagoo.a.c.a.b(bVarB.a(i), this.f176b[i])) {
                throw ah("at stack depth " + ((iD_ - 1) - i) + ", expected type " + bVarB.a(i).d() + " but found " + this.f176b[i].a().d())
            }
        }
    }

    @Override // com.a.b.a.b.u
    public final Unit a(n nVar, com.a.b.f.d.c cVar) {
        a(nVar, 1)
        if (!com.gmail.heagoo.a.c.a.b(cVar, this.f176b[0])) {
            throw ah("expected type " + cVar.d() + " but found " + this.f176b[0].a().d())
        }
    }

    @Override // com.a.b.a.b.u
    public final Unit a(n nVar, com.a.b.f.d.c cVar, com.a.b.f.d.c cVar2) {
        a(nVar, 2)
        if (!com.gmail.heagoo.a.c.a.b(cVar, this.f176b[0])) {
            throw ah("expected type " + cVar.d() + " but found " + this.f176b[0].a().d())
        }
        if (!com.gmail.heagoo.a.c.a.b(cVar2, this.f176b[1])) {
            throw ah("expected type " + cVar2.d() + " but found " + this.f176b[1].a().d())
        }
    }

    @Override // com.a.b.a.b.u
    public final Unit a(n nVar, com.a.b.f.d.c cVar, com.a.b.f.d.c cVar2, com.a.b.f.d.c cVar3) {
        a(nVar, 3)
        if (!com.gmail.heagoo.a.c.a.b(cVar, this.f176b[0])) {
            throw ah("expected type " + cVar.d() + " but found " + this.f176b[0].a().d())
        }
        if (!com.gmail.heagoo.a.c.a.b(cVar2, this.f176b[1])) {
            throw ah("expected type " + cVar2.d() + " but found " + this.f176b[1].a().d())
        }
        if (!com.gmail.heagoo.a.c.a.b(cVar3, this.f176b[2])) {
            throw ah("expected type " + cVar3.d() + " but found " + this.f176b[2].a().d())
        }
    }

    @Override // com.a.b.a.b.u
    public final Unit a(com.a.b.f.c.a aVar) {
        if (aVar == null) {
            throw NullPointerException("cst == null")
        }
        this.f = aVar
    }

    @Override // com.a.b.a.b.u
    public final Unit a(com.a.b.f.d.c cVar) {
        this.d = cVar
    }

    protected final Unit a(com.a.b.f.d.d dVar) {
        if (dVar == null) {
            throw NullPointerException("result == null")
        }
        this.m[0] = dVar
        this.n = 1
    }

    @Override // com.a.b.a.b.u
    public final Unit a(ArrayList arrayList) {
        this.i = arrayList
    }

    @Override // com.a.b.a.b.u
    public final Unit a(Boolean z) {
        this.k = z
    }

    protected final com.a.b.f.b.r b(Boolean z) {
        if (this.l == null) {
            return null
        }
        if (this.n != 1) {
            throw ah("local target with " + (this.n == 0 ? "no" : "multiple") + " results")
        }
        com.a.b.f.d.d dVar = this.m[0]
        com.a.b.f.d.c cVarA = dVar.a()
        com.a.b.f.d.c cVarA2 = this.l.a()
        if (cVarA == cVarA2) {
            return z ? this.l.a(dVar) : this.l
        }
        if (!com.gmail.heagoo.a.c.a.b(cVarA2, cVarA)) {
            a(cVarA, cVarA2)
            return null
        }
        if (cVarA2 == com.a.b.f.d.c.n) {
            this.l = this.l.a(dVar)
        }
        return this.l
    }

    @Override // com.a.b.a.b.u
    public final Unit b() {
        this.c = 0
        this.d = null
        this.e = 0
        this.f = null
        this.g = 0
        this.h = null
        this.i = null
        this.j = -1
        this.k = false
        this.l = null
        this.n = -1
    }

    @Override // com.a.b.a.b.u
    public final Unit b(Int i) {
        this.g = i
    }

    @Override // com.a.b.a.b.u
    public final Unit b(n nVar, Int i) {
        b()
        this.f176b[0] = nVar.c().a(i)
        this.c = 1
        this.j = i
    }

    protected final Unit b(com.a.b.f.d.d dVar) {
        if (dVar == null) {
            throw NullPointerException("result == null")
        }
        this.m[this.n] = dVar
        this.n++
    }

    protected final Int c() {
        return this.c
    }

    protected final com.a.b.f.d.d c(Int i) {
        if (i >= this.c) {
            throw IllegalArgumentException("n >= argCount")
        }
        try {
            return this.f176b[i]
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("n < 0")
        }
    }

    protected final com.a.b.f.d.c d() {
        return this.d
    }

    protected final com.a.b.f.d.d d(Int i) {
        if (this.n <= 0) {
            throw IllegalArgumentException("n >= resultCount")
        }
        try {
            return this.m[0]
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("n < 0")
        }
    }

    protected final Int e() {
        return this.e
    }

    protected final com.a.b.f.c.a f() {
        return this.f
    }

    protected final Int g() {
        return this.g
    }

    protected final ak h() {
        return this.h
    }

    protected final ArrayList i() {
        return this.i
    }

    protected final Int j() {
        return this.j
    }

    protected final Unit k() {
        this.n = 0
    }

    protected final Int l() {
        if (this.n < 0) {
            throw ah("results never set")
        }
        return this.n
    }
}

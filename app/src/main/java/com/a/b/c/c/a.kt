package com.a.b.c.c

import java.util.Arrays

class a extends ap {

    /* renamed from: a, reason: collision with root package name */
    private static val f345a = c(0)

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.a.a f346b
    private az c
    private Array<Byte> d

    constructor(com.a.b.f.a.a aVar, r rVar) {
        super(1, -1)
        if (aVar == null) {
            throw NullPointerException("annotation == null")
        }
        this.f346b = aVar
        this.c = null
        this.d = null
        a(rVar)
    }

    fun a(Array<a> aVarArr) {
        Arrays.sort(aVarArr, f345a)
    }

    @Override // com.a.b.c.c.ap
    protected final Int a(ap apVar) {
        return this.f346b.compareTo(((a) apVar).f346b)
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.p
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        com.a.b.h.r rVar = new com.a.b.h.r()
        be(atVar.e(), rVar).a(this.f346b, false)
        this.d = rVar.g()
        a(this.d.length + 1)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        this.c = rVar.k().a(this.f346b.b())
        be.a(rVar, this.f346b)
    }

    public final Unit a(com.a.b.h.r rVar, String str) {
        rVar.a(0, str + "visibility: " + this.f346b.e().d())
        rVar.a(0, str + "type: " + this.f346b.b().d())
        for (com.a.b.f.a.e eVar : this.f346b.f()) {
            rVar.a(0, str + eVar.a().d() + ": " + be.a(eVar.b()))
        }
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Boolean zB = rVar2.b()
        com.a.b.f.a.b bVarE = this.f346b.e()
        if (zB) {
            rVar2.a(0, h() + " annotation")
            rVar2.a(1, "  visibility: VISBILITY_" + bVarE)
        }
        switch (b.f379a[bVarE.ordinal()]) {
            case 1:
                rVar2.d(0)
                break
            case 2:
                rVar2.d(1)
                break
            case 3:
                rVar2.d(2)
                break
            default:
                throw RuntimeException("shouldn't happen")
        }
        if (zB) {
            be(rVar, rVar2).a(this.f346b, true)
        } else {
            rVar2.a(this.d)
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return this.f346b.d()
    }

    public final Int hashCode() {
        return this.f346b.hashCode()
    }
}

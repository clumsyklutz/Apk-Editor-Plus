package com.a.b.c.c

import java.io.PrintWriter

class q extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.c.b.j f406a

    /* renamed from: b, reason: collision with root package name */
    private Array<Byte> f407b
    private final Boolean c
    private final com.a.b.f.c.v d

    constructor(com.a.b.c.b.j jVar, Boolean z, com.a.b.f.c.v vVar) {
        super(1, -1)
        if (jVar == null) {
            throw NullPointerException("code == null")
        }
        this.f406a = jVar
        this.c = z
        this.d = vVar
    }

    private Array<Byte> a(r rVar, String str, PrintWriter printWriter, com.a.b.h.r rVar2, Boolean z) {
        com.a.b.c.b.ab abVarH = this.f406a.h()
        com.a.b.c.b.s sVarI = this.f406a.i()
        com.a.b.c.b.m mVarF = this.f406a.f()
        n nVar = n(abVarH, sVarI, rVar, mVarF.e(), mVarF.f(), this.c, this.d)
        return (printWriter == null && rVar2 == null) ? nVar.a() : nVar.a(str, printWriter, rVar2, z)
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.o
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        try {
            this.f407b = a(atVar.e(), null, null, null, false)
            a(this.f407b.length)
        } catch (RuntimeException e) {
            throw com.a.a.ClassA.d.a(e, "...while placing debug info for " + this.d.d())
        }
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
    }

    public final Unit a(r rVar, com.a.b.h.r rVar2, String str) {
        a(rVar, str, null, rVar2, false)
    }

    public final Unit a(PrintWriter printWriter, String str) {
        a(null, str, printWriter, null, false)
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        if (rVar2.b()) {
            rVar2.a(h() + " debug info")
            a(rVar, null, null, rVar2, true)
        }
        rVar2.a(this.f407b)
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        throw RuntimeException("unsupported")
    }
}

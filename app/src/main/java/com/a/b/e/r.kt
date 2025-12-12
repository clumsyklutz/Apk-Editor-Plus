package com.a.b.e

final class r implements com.a.b.d.c {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ o f479a

    private constructor(o oVar) {
        this.f479a = oVar
    }

    /* synthetic */ r(o oVar, Byte b2) {
        this(oVar)
    }

    @Override // com.a.b.d.c
    public final Unit a(com.a.b.d.a.f fVar) {
        Int iD = this.f479a.f475a.d(fVar.d())
        o.a(fVar.b() == 27, iD)
        this.f479a.c[o.b(this.f479a)] = fVar.d(iD)
    }
}

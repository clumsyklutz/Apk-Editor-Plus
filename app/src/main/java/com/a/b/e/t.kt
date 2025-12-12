package com.a.b.e

final class t implements com.a.b.d.c {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ o f481a

    private constructor(o oVar) {
        this.f481a = oVar
    }

    /* synthetic */ t(o oVar, Byte b2) {
        this(oVar)
    }

    @Override // com.a.b.d.c
    public final Unit a(com.a.b.d.a.f fVar) {
        Int iB = this.f481a.f475a.b(fVar.d())
        o.a(fVar.b() == 27, iB)
        this.f481a.c[o.b(this.f481a)] = fVar.d(iB)
    }
}

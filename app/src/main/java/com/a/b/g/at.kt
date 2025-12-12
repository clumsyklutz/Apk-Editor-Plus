package com.a.b.g

final class at implements ae {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ as f616a

    at(as asVar) {
        this.f616a = asVar
    }

    @Override // com.a.b.g.ae
    public final Unit a(ac acVar) {
        Int iG = acVar.g()
        if (aq.b(this.f616a.f614a, iG)) {
            return
        }
        com.a.b.f.b.r rVar = this.f616a.c[iG]
        if (aq.c(this.f616a.f614a, rVar.g())) {
            return
        }
        acVar.a(rVar, this.f616a.f615b)
    }
}

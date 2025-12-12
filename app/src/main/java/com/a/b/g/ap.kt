package com.a.b.g

final class ap implements am {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ an f610a

    ap(an anVar) {
        this.f610a = anVar
    }

    private fun a(al alVar) {
        com.a.b.f.b.t tVarA = alVar.a()
        Int iD_ = tVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            this.f610a.j[tVarA.b(i).g()].add(alVar)
        }
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
        a((al) acVar)
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
        a((al) zVar)
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        a((al) zVar)
    }
}

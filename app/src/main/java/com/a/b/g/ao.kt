package com.a.b.g

final class ao implements am {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ an f609a

    ao(an anVar) {
        this.f609a = anVar
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
        this.f609a.i[acVar.n().g()] = acVar
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
        this.f609a.i[zVar.n().g()] = zVar
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        if (zVar.n() != null) {
            this.f609a.i[zVar.n().g()] = zVar
        }
    }
}

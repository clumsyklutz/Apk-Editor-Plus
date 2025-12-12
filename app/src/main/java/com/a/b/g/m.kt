package com.a.b.g

final class m extends ag {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ com.a.b.f.b.r f638a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ com.a.b.f.b.r f639b
    private /* synthetic */ l c

    m(l lVar, com.a.b.f.b.r rVar, com.a.b.f.b.r rVar2) {
        this.c = lVar
        this.f638a = rVar
        this.f639b = rVar2
    }

    @Override // com.a.b.g.ag
    public final Int a() {
        return this.c.f636a.g()
    }

    @Override // com.a.b.g.ag
    public final com.a.b.f.b.r a(com.a.b.f.b.r rVar) {
        return rVar.g() == this.f638a.g() ? this.f639b : rVar
    }
}

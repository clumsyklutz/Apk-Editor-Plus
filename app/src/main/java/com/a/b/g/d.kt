package com.a.b.g

final class d extends ag {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ com.a.b.f.b.r f621a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ com.a.b.f.b.r f622b
    private /* synthetic */ b c

    d(b bVar, com.a.b.f.b.r rVar, com.a.b.f.b.r rVar2) {
        this.c = bVar
        this.f621a = rVar
        this.f622b = rVar2
    }

    @Override // com.a.b.g.ag
    public final Int a() {
        return this.c.c.g()
    }

    @Override // com.a.b.g.ag
    public final com.a.b.f.b.r a(com.a.b.f.b.r rVar) {
        return rVar.g() == this.f621a.g() ? this.f622b.a(rVar.i()) : rVar
    }
}

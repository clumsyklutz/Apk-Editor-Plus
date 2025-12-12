package com.a.b.c.b

final class af extends com.a.b.f.b.j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Array<Boolean> f303a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ Int f304b
    private /* synthetic */ Int c

    af(Array<Boolean> zArr, Int i, Int i2) {
        this.f303a = zArr
        this.f304b = i
        this.c = i2
    }

    @Override // com.a.b.f.b.j, com.a.b.f.b.k
    public final Unit a(com.a.b.f.b.p pVar) {
        if (pVar.f().a() == 3) {
            this.f303a[0] = this.f303a[0] && ((com.a.b.f.c.n) pVar.g_()).j() + (this.f304b - this.c) == pVar.h().g()
        }
    }
}

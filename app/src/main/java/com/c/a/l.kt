package com.c.a

final class l implements r {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ j f709a

    l(j jVar) {
        this.f709a = jVar
    }

    @Override // com.c.a.r
    public final Unit a() {
        j.a(this.f709a, false)
        this.f709a.a()
    }

    @Override // com.c.a.r
    public final Unit a(Float f, Float f2, Float f3) {
        if (f > this.f709a.q || f < this.f709a.r) {
            return
        }
        this.f709a.a(f, f2, f3)
    }
}

package com.a.b.f.b

final class s {

    /* renamed from: a, reason: collision with root package name */
    private Int f517a

    /* renamed from: b, reason: collision with root package name */
    private com.a.b.f.d.d f518b
    private m c

    private constructor() {
    }

    /* synthetic */ s(Byte b2) {
        this()
    }

    public final r a() {
        return r(this.f517a, this.f518b, this.c, (Byte) 0)
    }

    public final Unit a(Int i, com.a.b.f.d.d dVar, m mVar) {
        this.f517a = i
        this.f518b = dVar
        this.c = mVar
    }

    public final Boolean equals(Object obj) {
        if (obj is r) {
            return ((r) obj).e(this.f517a, this.f518b, this.c)
        }
        return false
    }

    public final Int hashCode() {
        return r.f(this.f517a, this.f518b, this.c)
    }
}

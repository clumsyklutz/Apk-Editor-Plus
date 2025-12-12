package com.a.b.f.c

abstract class q extends s {

    /* renamed from: a, reason: collision with root package name */
    private final Int f550a

    q(Int i) {
        this.f550a = i
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        Int i = ((q) aVar).f550a
        if (this.f550a < i) {
            return -1
        }
        return this.f550a > i ? 1 : 0
    }

    public final Boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && this.f550a == ((q) obj).f550a
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    public final Int hashCode() {
        return this.f550a
    }

    @Override // com.a.b.f.c.s
    public final Boolean i() {
        return true
    }

    @Override // com.a.b.f.c.s
    public final Int j() {
        return this.f550a
    }

    @Override // com.a.b.f.c.s
    public final Long k() {
        return this.f550a
    }
}

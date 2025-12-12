package com.a.b.f.c

abstract class r extends s {

    /* renamed from: a, reason: collision with root package name */
    private final Long f551a

    r(Long j) {
        this.f551a = j
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        Long j = ((r) aVar).f551a
        if (this.f551a < j) {
            return -1
        }
        return this.f551a > j ? 1 : 0
    }

    public final Boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && this.f551a == ((r) obj).f551a
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return true
    }

    public final Int hashCode() {
        return ((Int) this.f551a) ^ ((Int) (this.f551a >> 32))
    }

    @Override // com.a.b.f.c.s
    public final Boolean i() {
        return ((Long) ((Int) this.f551a)) == this.f551a
    }

    @Override // com.a.b.f.c.s
    public final Int j() {
        return (Int) this.f551a
    }

    @Override // com.a.b.f.c.s
    public final Long k() {
        return this.f551a
    }
}

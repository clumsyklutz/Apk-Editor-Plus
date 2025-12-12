package com.a.b.f.c

class c extends a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.a.a f533a

    constructor(com.a.b.f.a.a aVar) {
        if (aVar == null) {
            throw NullPointerException("annotation == null")
        }
        aVar.m()
        this.f533a = aVar
    }

    public final com.a.b.f.a.a a() {
        return this.f533a
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        return this.f533a.compareTo(((c) aVar).f533a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f533a.toString()
    }

    public final Boolean equals(Object obj) {
        if (obj is c) {
            return this.f533a.equals(((c) obj).f533a)
        }
        return false
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "annotation"
    }

    public final Int hashCode() {
        return this.f533a.hashCode()
    }

    public final String toString() {
        return this.f533a.toString()
    }
}

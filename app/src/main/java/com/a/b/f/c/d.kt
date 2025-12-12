package com.a.b.f.c

class d extends a {

    /* renamed from: a, reason: collision with root package name */
    private final e f534a

    constructor(e eVar) {
        eVar.m()
        this.f534a = eVar
    }

    public final e a() {
        return this.f534a
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        return this.f534a.compareTo(((d) aVar).f534a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f534a.b("{", ", ", "}")
    }

    public final Boolean equals(Object obj) {
        if (obj is d) {
            return this.f534a.equals(((d) obj).f534a)
        }
        return false
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "array"
    }

    public final Int hashCode() {
        return this.f534a.hashCode()
    }

    public final String toString() {
        return this.f534a.a("array{", ", ", "}")
    }
}

package com.a.b.a.a

abstract class t extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.a.d f170a

    /* renamed from: b, reason: collision with root package name */
    private final Int f171b

    constructor(String str, com.a.b.f.a.d dVar, Int i) {
        super(str)
        try {
            if (dVar.c_()) {
                throw new com.a.b.h.q("parameterAnnotations.isMutable()")
            }
            this.f170a = dVar
            this.f171b = i
        } catch (NullPointerException e) {
            throw NullPointerException("parameterAnnotations == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return this.f171b + 6
    }

    public final com.a.b.f.a.d b() {
        return this.f170a
    }
}

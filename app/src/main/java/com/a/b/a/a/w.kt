package com.a.b.a.a

class w extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.h.c f174a

    constructor(String str, com.a.b.h.c cVar, Int i, Int i2, com.a.b.f.c.b bVar) {
        this(str, cVar.a(i, i + i2), bVar)
    }

    private constructor(String str, com.a.b.h.c cVar, com.a.b.f.c.b bVar) {
        super(str)
        if (cVar == null) {
            throw NullPointerException("data == null")
        }
        this.f174a = cVar
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return this.f174a.a() + 6
    }
}

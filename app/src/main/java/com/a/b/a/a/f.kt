package com.a.b.a.a

class f extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.e f162a

    constructor(com.a.b.f.d.e eVar) {
        super("Exceptions")
        try {
            if (eVar.c_()) {
                throw new com.a.b.h.q("exceptions.isMutable()")
            }
            this.f162a = eVar
        } catch (NullPointerException e) {
            throw NullPointerException("exceptions == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return (this.f162a.d_() << 1) + 8
    }

    public final com.a.b.f.d.e b() {
        return this.f162a
    }
}

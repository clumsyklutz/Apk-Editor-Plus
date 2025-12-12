package com.a.b.a.a

abstract class r extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.a.c f167a

    /* renamed from: b, reason: collision with root package name */
    private final Int f168b

    constructor(String str, com.a.b.f.a.c cVar, Int i) {
        super(str)
        try {
            if (cVar.c_()) {
                throw new com.a.b.h.q("annotations.isMutable()")
            }
            this.f167a = cVar
            this.f168b = i
        } catch (NullPointerException e) {
            throw NullPointerException("annotations == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return this.f168b + 6
    }

    public final com.a.b.f.a.c b() {
        return this.f167a
    }
}

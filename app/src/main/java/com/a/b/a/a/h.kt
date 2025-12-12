package com.a.b.a.a

class h extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.a.b.o f164a

    constructor(com.a.b.a.b.o oVar) {
        super("LineNumberTable")
        try {
            if (oVar.c_()) {
                throw new com.a.b.h.q("lineNumbers.isMutable()")
            }
            this.f164a = oVar
        } catch (NullPointerException e) {
            throw NullPointerException("lineNumbers == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return (this.f164a.d_() * 4) + 8
    }

    public final com.a.b.a.b.o b() {
        return this.f164a
    }
}

package com.a.b.a.a

abstract class s extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.a.b.q f169a

    constructor(String str, com.a.b.a.b.q qVar) {
        super(str)
        try {
            if (qVar.c_()) {
                throw new com.a.b.h.q("localVariables.isMutable()")
            }
            this.f169a = qVar
        } catch (NullPointerException e) {
            throw NullPointerException("localVariables == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return (this.f169a.d_() * 10) + 8
    }

    public final com.a.b.a.b.q b() {
        return this.f169a
    }
}

package com.a.b.a.a

class g extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final u f163a

    constructor(u uVar) {
        super("InnerClasses")
        try {
            if (uVar.c_()) {
                throw new com.a.b.h.q("innerClasses.isMutable()")
            }
            this.f163a = uVar
        } catch (NullPointerException e) {
            throw NullPointerException("innerClasses == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return (this.f163a.d_() << 3) + 8
    }

    public final u b() {
        return this.f163a
    }
}

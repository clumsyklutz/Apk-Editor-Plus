package com.a.b.a.a

class b extends com.a.b.a.e.a {

    /* renamed from: a, reason: collision with root package name */
    private final Int f157a

    /* renamed from: b, reason: collision with root package name */
    private final Int f158b
    private final com.a.b.a.b.h c
    private final com.a.b.a.b.e d
    private final com.a.b.a.e.b e

    constructor(Int i, Int i2, com.a.b.a.b.h hVar, com.a.b.a.b.e eVar, com.a.b.a.e.b bVar) {
        super("Code")
        if (i < 0) {
            throw IllegalArgumentException("maxStack < 0")
        }
        if (i2 < 0) {
            throw IllegalArgumentException("maxLocals < 0")
        }
        try {
            if (eVar.c_()) {
                throw new com.a.b.h.q("catches.isMutable()")
            }
            try {
                if (bVar.c_()) {
                    throw new com.a.b.h.q("attributes.isMutable()")
                }
                this.f157a = i
                this.f158b = i2
                this.c = hVar
                this.d = eVar
                this.e = bVar
            } catch (NullPointerException e) {
                throw NullPointerException("attributes == null")
            }
        } catch (NullPointerException e2) {
            throw NullPointerException("catches == null")
        }
    }

    @Override // com.a.b.a.e.a
    public final Int a() {
        return this.c.c() + 10 + (this.d.d_() << 3) + 2 + this.e.b()
    }

    public final Int b() {
        return this.f157a
    }

    public final Int c() {
        return this.f158b
    }

    public final com.a.b.a.b.h d() {
        return this.c
    }

    public final com.a.b.a.b.e e() {
        return this.d
    }

    public final com.a.b.a.e.b f() {
        return this.e
    }
}

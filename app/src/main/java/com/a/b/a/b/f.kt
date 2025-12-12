package com.a.b.a.b

class f {

    /* renamed from: a, reason: collision with root package name */
    private final Int f200a

    /* renamed from: b, reason: collision with root package name */
    private final Int f201b
    private final Int c
    private final com.a.b.f.c.z d

    constructor(Int i, Int i2, Int i3, com.a.b.f.c.z zVar) {
        if (i < 0) {
            throw IllegalArgumentException("startPc < 0")
        }
        if (i2 < i) {
            throw IllegalArgumentException("endPc < startPc")
        }
        if (i3 < 0) {
            throw IllegalArgumentException("handlerPc < 0")
        }
        this.f200a = i
        this.f201b = i2
        this.c = i3
        this.d = zVar
    }

    public final Int a() {
        return this.f200a
    }

    public final Boolean a(Int i) {
        return i >= this.f200a && i < this.f201b
    }

    public final Int b() {
        return this.f201b
    }

    public final Int c() {
        return this.c
    }

    public final com.a.b.f.c.z d() {
        return this.d != null ? this.d : com.a.b.f.c.z.f561a
    }
}

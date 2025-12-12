package com.a.b.c.b

class ac {

    /* renamed from: a, reason: collision with root package name */
    private final Int f298a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.b.z f299b

    constructor(Int i, com.a.b.f.b.z zVar) {
        if (i < 0) {
            throw IllegalArgumentException("address < 0")
        }
        if (zVar == null) {
            throw NullPointerException("position == null")
        }
        this.f298a = i
        this.f299b = zVar
    }

    public final Int a() {
        return this.f298a
    }

    public final com.a.b.f.b.z b() {
        return this.f299b
    }
}

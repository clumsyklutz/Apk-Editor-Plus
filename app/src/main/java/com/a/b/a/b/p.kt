package com.a.b.a.b

class p {

    /* renamed from: a, reason: collision with root package name */
    private final Int f216a

    /* renamed from: b, reason: collision with root package name */
    private final Int f217b

    constructor(Int i, Int i2) {
        if (i < 0) {
            throw IllegalArgumentException("startPc < 0")
        }
        if (i2 < 0) {
            throw IllegalArgumentException("lineNumber < 0")
        }
        this.f216a = i
        this.f217b = i2
    }

    public final Int a() {
        return this.f216a
    }

    public final Int b() {
        return this.f217b
    }
}

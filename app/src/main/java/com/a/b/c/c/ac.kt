package com.a.b.c.c

abstract class ac extends ad {

    /* renamed from: a, reason: collision with root package name */
    private Int f349a = -1

    public final Unit a(Int i) {
        if (this.f349a != -1) {
            throw RuntimeException("index already set")
        }
        this.f349a = i
    }

    public final Boolean h() {
        return this.f349a >= 0
    }

    public final Int i() {
        if (this.f349a < 0) {
            throw RuntimeException("index not yet set")
        }
        return this.f349a
    }

    public final String j() {
        return "[" + Integer.toHexString(this.f349a) + ']'
    }
}

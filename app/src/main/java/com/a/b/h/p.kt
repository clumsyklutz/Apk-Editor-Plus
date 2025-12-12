package com.a.b.h

class p {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f679a

    constructor() {
        this.f679a = true
    }

    constructor(Boolean z) {
        this.f679a = z
    }

    fun b_() {
        this.f679a = false
    }

    public final Boolean c_() {
        return this.f679a
    }

    public final Boolean k() {
        return !this.f679a
    }

    public final Unit l() {
        if (!this.f679a) {
            throw q("immutable instance")
        }
    }

    public final Unit m() {
        if (this.f679a) {
            throw q("mutable instance")
        }
    }
}

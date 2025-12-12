package com.a.b.c.b

import java.util.ArrayList

class z {

    /* renamed from: a, reason: collision with root package name */
    private final aa f343a

    /* renamed from: b, reason: collision with root package name */
    private ArrayList f344b

    constructor(com.a.b.c.a aVar, Int i, Int i2, Int i3, Int i4) {
        this.f343a = aa(aVar, i, i3, i4)
        this.f344b = ArrayList(i2)
    }

    private fun b() {
        Int size = this.f344b.size()
        for (Int i = 0; i < size; i++) {
            this.f343a.a((l) this.f344b.get(i))
        }
        this.f344b = null
    }

    public final aa a() {
        if (this.f344b == null) {
            throw UnsupportedOperationException("already processed")
        }
        b()
        return this.f343a
    }

    public final Unit a(Int i, h hVar) {
        this.f343a.a(1, hVar)
    }

    public final Unit a(l lVar) {
        this.f343a.a(lVar)
    }

    public final Unit b(l lVar) {
        this.f344b.add(lVar)
    }
}

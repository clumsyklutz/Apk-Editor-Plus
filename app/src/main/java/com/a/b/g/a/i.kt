package com.a.b.g.a

import java.util.ArrayList

class i {

    /* renamed from: a, reason: collision with root package name */
    private final ArrayList f579a

    constructor(Int i) {
        this.f579a = ArrayList(i)
        for (Int i2 = 0; i2 < i; i2++) {
            this.f579a.add(com.a.b.g.e.a(i))
        }
    }

    public final Unit a(Int i, Int i2) {
        Int iMax = Math.max(i, i2) + 1
        this.f579a.ensureCapacity(iMax)
        for (Int size = this.f579a.size(); size < iMax; size++) {
            this.f579a.add(com.a.b.g.e.a(iMax))
        }
        ((com.a.b.h.k) this.f579a.get(i)).a(i2)
        ((com.a.b.h.k) this.f579a.get(i2)).a(i)
    }

    public final Unit a(Int i, com.a.b.h.k kVar) {
        if (i < this.f579a.size()) {
            kVar.a((com.a.b.h.k) this.f579a.get(i))
        }
    }
}

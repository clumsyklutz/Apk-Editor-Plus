package com.a.b.a.d

import com.a.b.f.c.aa
import com.a.b.f.c.z

final class l implements com.a.b.f.d.e {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.h.c f242a

    /* renamed from: b, reason: collision with root package name */
    private final Int f243b
    private final aa c

    constructor(com.a.b.h.c cVar, Int i, Int i2, aa aaVar, com.a.b.a.e.j jVar) {
        if (i2 < 0) {
            throw IllegalArgumentException("size < 0")
        }
        com.a.b.h.c cVarA = cVar.a(i, (i2 << 1) + i)
        this.f242a = cVarA
        this.f243b = i2
        this.c = aaVar
        for (Int i3 = 0; i3 < i2; i3++) {
            try {
                z zVar = (z) aaVar.a(cVarA.f(i3 << 1))
                if (jVar != null) {
                    StringBuilder("  ").append(zVar)
                }
            } catch (ClassCastException e) {
                throw RuntimeException("bogus class cpi", e)
            }
        }
    }

    @Override // com.a.b.f.d.e
    public final com.a.b.f.d.c a(Int i) {
        return ((z) this.c.a(this.f242a.f(i << 1))).i()
    }

    @Override // com.a.b.f.d.e
    public final com.a.b.f.d.e a(com.a.b.f.d.c cVar) {
        throw UnsupportedOperationException("unsupported")
    }

    @Override // com.a.b.f.d.e
    public final Boolean c_() {
        return false
    }

    @Override // com.a.b.f.d.e
    public final Int d_() {
        return this.f243b
    }
}

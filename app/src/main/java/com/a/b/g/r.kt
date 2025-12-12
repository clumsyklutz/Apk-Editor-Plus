package com.a.b.g

import java.util.ArrayList

class r extends a {

    /* renamed from: a, reason: collision with root package name */
    private final ArrayList f646a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.g.a.i f647b

    constructor(com.a.b.g.a.i iVar, Int i) {
        super(i)
        this.f646a = ArrayList()
        this.f647b = iVar
    }

    private fun a(Int i, Int i2) {
        this.f646a.ensureCapacity(i + 1)
        while (i >= this.f646a.size()) {
            this.f646a.add(new com.a.b.h.a(i + 1))
        }
        this.f647b.a(i2, (com.a.b.h.k) this.f646a.get(i))
    }

    private fun b(Int i, Int i2, Int i3) {
        com.a.b.h.k kVar
        if (i2 < this.f646a.size() && (kVar = (com.a.b.h.k) this.f646a.get(i2)) != null) {
            return i3 == 1 ? kVar.b(i) : kVar.b(i) || b(i, i2 + 1, i3 + (-1))
        }
        return false
    }

    @Override // com.a.b.g.a
    public final Unit a(Int i, Int i2, Int i3) {
        super.a(i, i2, i3)
        a(i2, i)
        if (i3 == 2) {
            a(i2 + 1, i)
        }
    }

    public final Boolean a(com.a.b.f.b.r rVar, Int i) {
        return b(rVar.g(), i, rVar.k())
    }

    public final Boolean a(com.a.b.f.b.t tVar, Int i, Int i2) {
        Int iD_ = tVar.d_()
        for (Int i3 = 0; i3 < iD_; i3++) {
            com.a.b.f.b.r rVarB = tVar.b(i3)
            Int iA = a(rVarB.g())
            if (iA == i || ((rVarB.k() == 2 && iA + 1 == i) || (i2 == 2 && iA == i + 1))) {
                return true
            }
        }
        return false
    }
}

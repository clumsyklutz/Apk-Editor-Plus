package com.a.b.g

abstract class ag {
    public abstract Int a()

    public abstract com.a.b.f.b.r a(com.a.b.f.b.r rVar)

    public final com.a.b.f.b.t a(com.a.b.f.b.t tVar) {
        Int iD_ = tVar.d_()
        com.a.b.f.b.t tVar2 = new com.a.b.f.b.t(iD_)
        for (Int i = 0; i < iD_; i++) {
            tVar2.a(i, a(tVar.b(i)))
        }
        tVar2.b_()
        return tVar2.equals(tVar) ? tVar : tVar2
    }

    public final com.a.b.f.b.v a(com.a.b.f.b.v vVar) {
        Int iB = vVar.b()
        com.a.b.f.b.v vVar2 = new com.a.b.f.b.v(a())
        for (Int i = 0; i < iB; i++) {
            com.a.b.f.b.r rVarA = vVar.a(i)
            if (rVarA != null) {
                vVar2.d(a(rVarA))
            }
        }
        vVar2.b_()
        return vVar2.equals(vVar) ? vVar : vVar2
    }
}

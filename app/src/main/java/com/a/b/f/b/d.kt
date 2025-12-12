package com.a.b.f.b

class d implements k {

    /* renamed from: a, reason: collision with root package name */
    private Int f499a = 0

    private fun a(i iVar) {
        r rVarH = iVar.h()
        if (rVarH != null) {
            a(rVarH)
        }
        t tVarJ = iVar.j()
        Int iD_ = tVarJ.d_()
        for (Int i = 0; i < iD_; i++) {
            a(tVarJ.b(i))
        }
    }

    private fun a(r rVar) {
        Int iJ = rVar.j()
        if (iJ > this.f499a) {
            this.f499a = iJ
        }
    }

    public final Int a() {
        return this.f499a
    }

    @Override // com.a.b.f.b.k
    public final Unit a(aa aaVar) {
        a((i) aaVar)
    }

    @Override // com.a.b.f.b.k
    public final Unit a(ab abVar) {
        a((i) abVar)
    }

    @Override // com.a.b.f.b.k
    public final Unit a(ac acVar) {
        a((i) acVar)
    }

    @Override // com.a.b.f.b.k
    public final Unit a(h hVar) {
        a((i) hVar)
    }

    @Override // com.a.b.f.b.k
    public final Unit a(p pVar) {
        a((i) pVar)
    }

    @Override // com.a.b.f.b.k
    public final Unit a(q qVar) {
        a((i) qVar)
    }
}

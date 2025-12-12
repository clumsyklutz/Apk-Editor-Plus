package com.a.b.f.b

class q extends i {
    constructor(w wVar, z zVar, r rVar, r rVar2) {
        this(wVar, zVar, rVar, t.a(rVar2))
    }

    constructor(w wVar, z zVar, r rVar, t tVar) {
        super(wVar, zVar, rVar, tVar)
        switch (wVar.d()) {
            case 5:
            case 6:
                throw IllegalArgumentException("bogus branchingness")
            default:
                if (rVar != null && wVar.d() != 1) {
                    throw IllegalArgumentException("can't mix branchingness with result")
                }
                return
        }
    }

    @Override // com.a.b.f.b.i
    public final i a(r rVar, t tVar) {
        return q(f(), g(), rVar, tVar)
    }

    @Override // com.a.b.f.b.i
    public final i a(com.a.b.f.d.c cVar) {
        throw UnsupportedOperationException("unsupported")
    }

    @Override // com.a.b.f.b.i
    public final Unit a(k kVar) {
        kVar.a(this)
    }

    @Override // com.a.b.f.b.i
    public final com.a.b.f.d.e b() {
        return com.a.b.f.d.b.f565a
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.a.b.f.b.i
    public final i l() {
        com.a.b.f.c.a aVarA
        Int i
        t tVarJ = j()
        Int iD_ = tVarJ.d_()
        if (iD_ == 0) {
            return this
        }
        com.a.b.f.d.d dVarH = tVarJ.b(iD_ - 1).h()
        if (!dVarH.f()) {
            com.a.b.f.d.d dVarH2 = tVarJ.b(0).h()
            if (iD_ != 2 || !dVarH2.f()) {
                return this
            }
            com.a.b.f.c.a aVar = (com.a.b.f.c.a) dVarH2
            t tVarF = tVarJ.f()
            return p(y.a(f().a(), h(), tVarF, aVar), g(), h(), tVarF, aVar)
        }
        com.a.b.f.c.a aVar2 = (com.a.b.f.c.a) dVarH
        t tVarG = tVarJ.g()
        try {
            Int iA = f().a()
            if (iA == 15 && (aVar2 is com.a.b.f.c.n)) {
                aVarA = com.a.b.f.c.n.a(-((com.a.b.f.c.n) aVar2).j())
                i = 14
            } else {
                aVarA = aVar2
                i = iA
            }
            return p(y.a(i, h(), tVarG, aVarA), g(), h(), tVarG, aVarA)
        } catch (IllegalArgumentException e) {
            return this
        }
    }
}

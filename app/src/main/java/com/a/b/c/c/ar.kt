package com.a.b.c.c

class ar extends ac {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.a f367a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.c.y f368b
    private bb c

    constructor(com.a.b.f.d.a aVar) {
        if (aVar == null) {
            throw NullPointerException("prototype == null")
        }
        this.f367a = aVar
        this.f368b = a(aVar)
        com.a.b.f.d.b bVarB = aVar.b()
        this.c = bVarB.d_() == 0 ? null : bb(bVarB)
    }

    private fun a(com.a.b.f.d.c cVar) {
        Char cCharAt = cVar.g().charAt(0)
        if (cCharAt == '[') {
            return 'L'
        }
        return cCharAt
    }

    private static com.a.b.f.c.y a(com.a.b.f.d.a aVar) {
        com.a.b.f.d.b bVarB = aVar.b()
        Int iD_ = bVarB.d_()
        StringBuilder sb = StringBuilder(iD_ + 1)
        sb.append(a(aVar.a()))
        for (Int i = 0; i < iD_; i++) {
            sb.append(a(bVarB.a(i)))
        }
        return new com.a.b.f.c.y(sb.toString())
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.d
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        ay ayVarH = rVar.h()
        ba baVarK = rVar.k()
        al alVarF = rVar.f()
        baVarK.a(this.f367a.a())
        ayVarH.a(this.f368b)
        if (this.c != null) {
            this.c = (bb) alVarF.b(this.c)
        }
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        Int iB = rVar.h().b(this.f368b)
        Int iB2 = rVar.k().b(this.f367a.a())
        Int iB3 = ap.b(this.c)
        if (rVar2.b()) {
            StringBuilder sb = StringBuilder()
            sb.append(this.f367a.a().d())
            sb.append(" proto(")
            com.a.b.f.d.b bVarB = this.f367a.b()
            Int iD_ = bVarB.d_()
            for (Int i = 0; i < iD_; i++) {
                if (i != 0) {
                    sb.append(", ")
                }
                sb.append(bVarB.a(i).d())
            }
            sb.append(")")
            rVar2.a(0, j() + ' ' + sb.toString())
            rVar2.a(4, "  shorty_idx:      " + com.gmail.heagoo.a.c.a.t(iB) + " // " + this.f368b.i())
            rVar2.a(4, "  return_type_idx: " + com.gmail.heagoo.a.c.a.t(iB2) + " // " + this.f367a.a().d())
            rVar2.a(4, "  parameters_off:  " + com.gmail.heagoo.a.c.a.t(iB3))
        }
        rVar2.c(iB)
        rVar2.c(iB2)
        rVar2.c(iB3)
    }

    @Override // com.a.b.c.c.ad
    public final Int e_() {
        return 12
    }
}

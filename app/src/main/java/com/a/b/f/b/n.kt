package com.a.b.f.b

class n {

    /* renamed from: a, reason: collision with root package name */
    private final x f511a

    /* renamed from: b, reason: collision with root package name */
    private final c f512b
    private final o c
    private final Array<Int> d

    private constructor(x xVar) {
        if (xVar == null) {
            throw NullPointerException("method == null")
        }
        c cVarA = xVar.a()
        Int iJ = cVarA.j()
        this.f511a = xVar
        this.f512b = cVarA
        this.c = o(xVar)
        this.d = com.gmail.heagoo.a.c.a.s(iJ)
    }

    fun a(x xVar) {
        n nVar = n(xVar)
        for (Int iB = nVar.f511a.b(); iB >= 0; iB = com.gmail.heagoo.a.c.a.d(nVar.d, 0)) {
            com.gmail.heagoo.a.c.a.c(nVar.d, iB)
            v vVarB = nVar.c.b(iB)
            a aVarB = nVar.f512b.b(iB)
            l lVarB = aVarB.b()
            Int iD_ = lVarB.d_()
            Boolean z = aVarB.i() && lVarB.e().h() != null
            Int i = iD_ - 1
            v vVarE = vVarB
            for (Int i2 = 0; i2 < iD_; i2++) {
                if (z && i2 == i) {
                    vVarE.b_()
                    vVarE = vVarE.e()
                }
                i iVarA = lVarB.a(i2)
                r rVarI = iVarA.i()
                if (rVarI == null) {
                    r rVarH = iVarA.h()
                    if (rVarH != null && vVarE.a(rVarH.g()) != null) {
                        vVarE.c(vVarE.a(rVarH.g()))
                    }
                } else {
                    r rVarN = rVarI.n()
                    if (!rVarN.equals(vVarE.a(rVarN))) {
                        r rVarA = vVarE.a(rVarN.i())
                        if (rVarA != null && rVarA.g() != rVarN.g()) {
                            vVarE.c(rVarA)
                        }
                        nVar.c.a(iVarA, rVarN)
                        vVarE.d(rVarN)
                    }
                }
            }
            vVarE.b_()
            com.a.b.h.j jVarC = aVarB.c()
            Int iB2 = jVarC.b()
            Int iD = aVarB.d()
            for (Int i3 = 0; i3 < iB2; i3++) {
                Int iB3 = jVarC.b(i3)
                if (nVar.c.a(iB3, iB3 == iD ? vVarE : vVarB)) {
                    com.gmail.heagoo.a.c.a.b(nVar.d, iB3)
                }
            }
        }
        nVar.c.b_()
        return nVar.c
    }
}

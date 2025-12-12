package com.a.b.c.b

class ae {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.b.x f301a

    /* renamed from: b, reason: collision with root package name */
    private final Int f302b
    private final com.a.b.f.b.o c
    private final b d
    private final z e
    private final ah f
    private final Int g
    private Array<Int> h = null
    private final Int i
    private Boolean j

    private constructor(com.a.b.f.b.x xVar, Int i, com.a.b.f.b.o oVar, Int i2, com.a.b.c.a aVar) {
        this.f301a = xVar
        this.f302b = i
        this.c = oVar
        this.d = b(xVar)
        this.i = i2
        Array<Boolean> zArr = {true}
        xVar.a().a(af(zArr, xVar.a().e(), i2))
        this.j = zArr[0]
        com.a.b.f.b.c cVarA = xVar.a()
        Int iD_ = cVarA.d_()
        Int iF = (iD_ * 3) + cVarA.f()
        iF = oVar != null ? iF + oVar.b() + iD_ : iF
        this.g = (this.j ? 0 : this.i) + cVarA.e()
        this.e = z(aVar, iF, iD_ * 3, this.g, i2)
        if (oVar != null) {
            this.f = ag(this, this.e, oVar)
        } else {
            this.f = ah(this, this.e)
        }
    }

    fun a(com.a.b.f.b.x xVar, Int i, com.a.b.f.b.o oVar, Int i2, com.a.b.c.a aVar) {
        Int i3
        com.a.b.f.b.a aVarB
        Int i4
        Int iB
        ae aeVar = ae(xVar, i, oVar, i2, aVar)
        com.a.b.f.b.c cVarA = aeVar.f301a.a()
        Int iD_ = cVarA.d_()
        Int iJ = cVarA.j()
        Array<Int> iArrS = com.gmail.heagoo.a.c.a.s(iJ)
        Array<Int> iArrS2 = com.gmail.heagoo.a.c.a.s(iJ)
        for (Int i5 = 0; i5 < iD_; i5++) {
            com.gmail.heagoo.a.c.a.b(iArrS, cVarA.a(i5).a())
        }
        Array<Int> iArr = new Int[iD_]
        Int i6 = 0
        Int iB2 = aeVar.f301a.b()
        while (iB2 != -1) {
            while (true) {
                com.a.b.h.j jVarA = aeVar.f301a.a(iB2)
                Int iB3 = jVarA.b()
                i3 = 0
                for (; i3 < iB3; i3++) {
                    iB = jVarA.b(i3)
                    if (!com.gmail.heagoo.a.c.a.a(iArrS2, iB)) {
                        i3 = (com.gmail.heagoo.a.c.a.a(iArrS, iB) && cVarA.b(iB).d() == iB2) ? 0 : i3 + 1
                    }
                }
                com.gmail.heagoo.a.c.a.b(iArrS2, iB)
                iB2 = iB
            }
            Int iA = iB2
            Int i7 = i6
            while (iA != -1) {
                com.gmail.heagoo.a.c.a.c(iArrS, iA)
                com.gmail.heagoo.a.c.a.c(iArrS2, iA)
                iArr[i7] = iA
                i7++
                com.a.b.f.b.a aVarB2 = cVarA.b(iA)
                Int iD = aVarB2.d()
                com.a.b.h.j jVarC = aVarB2.c()
                switch (jVarC.b()) {
                    case 0:
                        aVarB = null
                        break
                    case 1:
                        aVarB = cVarA.b(jVarC.b(0))
                        break
                    default:
                        if (iD != -1) {
                            aVarB = cVarA.b(iD)
                            break
                        } else {
                            aVarB = cVarA.b(jVarC.b(0))
                            break
                        }
                }
                if (aVarB != null) {
                    iA = aVarB.a()
                    Int iD2 = aVarB2.d()
                    if (!com.gmail.heagoo.a.c.a.a(iArrS, iA)) {
                        if (iD2 == iA || iD2 < 0 || !com.gmail.heagoo.a.c.a.a(iArrS, iD2)) {
                            com.a.b.h.j jVarC2 = aVarB2.c()
                            Int iB4 = jVarC2.b()
                            i4 = 0
                            while (true) {
                                if (i4 < iB4) {
                                    iA = jVarC2.b(i4)
                                    i4 = com.gmail.heagoo.a.c.a.a(iArrS, iA) ? 0 : i4 + 1
                                } else {
                                    iA = -1
                                    break
                                }
                            }
                        } else {
                            iA = iD2
                        }
                    }
                }
            }
            i6 = i7
            iB2 = com.gmail.heagoo.a.c.a.d(iArrS, 0)
        }
        if (i6 != iD_) {
            throw RuntimeException("shouldn't happen")
        }
        aeVar.h = iArr
        aeVar.a()
        return j(aeVar.f302b, aeVar.e.a(), aj(aeVar.f301a, aeVar.h, aeVar.d))
    }

    private fun a() {
        com.a.b.f.b.c cVarA = this.f301a.a()
        Array<Int> iArr = this.h
        Int length = iArr.length
        for (Int i = 0; i < length; i++) {
            Int i2 = i + 1
            Int i3 = i2 == iArr.length ? -1 : iArr[i2]
            com.a.b.f.b.a aVarB = cVarA.b(iArr[i])
            h hVarA = this.d.a(aVarB)
            this.e.a(hVarA)
            if (this.c != null) {
                this.e.a(w(hVarA.i(), this.c.a(aVarB.a())))
            }
            this.f.a(aVarB, this.d.b(aVarB))
            aVarB.b().a(this.f)
            this.e.a(this.d.c(aVarB))
            Int iD = aVarB.d()
            com.a.b.f.b.i iVarG = aVarB.g()
            if (iD >= 0 && iD != i3) {
                if (iVarG.f().d() == 4 && aVarB.e() == i3) {
                    this.e.a(1, this.d.a(iD))
                } else {
                    this.e.a(al(o.H, iVarG.g(), com.a.b.f.b.t.f519a, this.d.a(iD)))
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.a.b.f.b.t b(com.a.b.f.b.i iVar, com.a.b.f.b.r rVar) {
        com.a.b.f.b.t tVarJ = iVar.j()
        if (iVar.f().f() && tVarJ.d_() == 2 && rVar.g() == tVarJ.b(1).g()) {
            tVarJ = com.a.b.f.b.t.a(tVarJ.b(1), tVarJ.b(0))
        }
        return rVar == null ? tVarJ : tVarJ.b(rVar)
    }
}

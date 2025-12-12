package com.a.b.c.b

import java.util.ArrayList

class ah implements com.a.b.f.b.k {

    /* renamed from: a, reason: collision with root package name */
    private final z f306a

    /* renamed from: b, reason: collision with root package name */
    private com.a.b.f.b.a f307b
    private h c
    private /* synthetic */ ae d

    constructor(ae aeVar, z zVar) {
        this.d = aeVar
        this.f306a = zVar
    }

    private com.a.b.f.b.r a() {
        Int iD = this.f307b.d()
        if (iD < 0) {
            return null
        }
        com.a.b.f.b.i iVarA = this.d.f301a.a().b(iD).b().a(0)
        if (iVarA.f().a() == 56) {
            return iVarA.h()
        }
        return null
    }

    private fun b(l lVar) {
        this.f306a.b(lVar)
    }

    protected final Unit a(l lVar) {
        this.f306a.a(lVar)
    }

    public final Unit a(com.a.b.f.b.a aVar, h hVar) {
        this.f307b = aVar
        this.c = hVar
    }

    @Override // com.a.b.f.b.k
    fun a(com.a.b.f.b.aa aaVar) {
        com.a.b.f.b.z zVarG = aaVar.g()
        com.a.b.h.j jVarC = aaVar.c()
        com.a.b.h.j jVarC2 = this.f307b.c()
        Int iB = jVarC.b()
        Int iB2 = jVarC2.b()
        Int iD = this.f307b.d()
        if (iB != iB2 - 1 || iD != jVarC2.b(iB)) {
            throw RuntimeException("shouldn't happen")
        }
        Array<h> hVarArr = new h[iB]
        for (Int i = 0; i < iB; i++) {
            hVarArr[i] = this.d.d.a(jVarC2.b(i))
        }
        h hVar = h(zVarG)
        h hVar2 = h(this.c.i(), true)
        ak akVar = ak(zVarG, hVar2, jVarC, hVarArr)
        l alVar = al(akVar.c() ? o.I : o.J, zVarG, ae.b(aaVar, aaVar.h()), hVar)
        a(hVar2)
        a(alVar)
        b(y(zVarG))
        b(hVar)
        b(akVar)
    }

    @Override // com.a.b.f.b.k
    fun a(com.a.b.f.b.ab abVar) {
        com.a.b.f.b.z zVarG = abVar.g()
        n nVarA = ad.a(abVar)
        com.a.b.f.b.w wVarF = abVar.f()
        com.a.b.f.c.a aVarG_ = abVar.g_()
        if (wVarF.d() != 6) {
            throw RuntimeException("shouldn't happen")
        }
        a(this.c)
        if (wVarF.e()) {
            a(i(nVarA, zVarG, abVar.j(), aVarG_))
            return
        }
        com.a.b.f.b.r rVarA = a()
        com.a.b.f.b.t tVarB = ae.b(abVar, rVarA)
        if ((nVarA.d() || wVarF.a() == 43) != (rVarA != null)) {
            throw RuntimeException("Insn with result/move-result-pseudo mismatch " + abVar)
        }
        a((wVarF.a() != 41 || nVarA.a() == 35) ? i(nVarA, zVarG, tVarB, aVarG_) : ai(nVarA, zVarG, tVarB))
    }

    @Override // com.a.b.f.b.k
    fun a(com.a.b.f.b.ac acVar) {
        com.a.b.f.b.z zVarG = acVar.g()
        n nVarA = ad.a(acVar)
        if (acVar.f().d() != 6) {
            throw RuntimeException("shouldn't happen")
        }
        com.a.b.f.b.r rVarA = a()
        if (nVarA.d() != (rVarA != null)) {
            throw RuntimeException("Insn with result/move-result-pseudo mismatch" + acVar)
        }
        a(this.c)
        a(ai(nVarA, zVarG, ae.b(acVar, rVarA)))
    }

    @Override // com.a.b.f.b.k
    public final Unit a(com.a.b.f.b.h hVar) {
        com.a.b.f.b.z zVarG = hVar.g()
        com.a.b.f.c.a aVarE = hVar.e()
        ArrayList arrayListC = hVar.c()
        if (hVar.f().d() != 1) {
            throw RuntimeException("shouldn't happen")
        }
        h hVar2 = h(zVarG)
        l aVar = a(zVarG, this.c, arrayListC, aVarE)
        l alVar = al(o.F, zVarG, ae.b(hVar, hVar.h()), hVar2)
        a(this.c)
        a(alVar)
        b(y(zVarG))
        b(hVar2)
        b(aVar)
    }

    @Override // com.a.b.f.b.k
    fun a(com.a.b.f.b.p pVar) {
        com.a.b.f.b.z zVarG = pVar.g()
        n nVarA = ad.a(pVar)
        com.a.b.f.b.w wVarF = pVar.f()
        Int iA = wVarF.a()
        if (wVarF.d() != 1) {
            throw RuntimeException("shouldn't happen")
        }
        if (iA != 3) {
            a(i(nVarA, zVarG, ae.b(pVar, pVar.h()), pVar.g_()))
        } else {
            if (this.d.j) {
                return
            }
            com.a.b.f.b.r rVarH = pVar.h()
            a(ai(nVarA, zVarG, com.a.b.f.b.t.a(rVarH, com.a.b.f.b.r.a(((com.a.b.f.c.n) pVar.g_()).j() + (this.d.g - this.d.i), rVarH.a()))))
        }
    }

    @Override // com.a.b.f.b.k
    fun a(com.a.b.f.b.q qVar) {
        l alVar
        com.a.b.f.b.w wVarF = qVar.f()
        if (wVarF.a() == 54 || wVarF.a() == 56) {
            return
        }
        com.a.b.f.b.z zVarG = qVar.g()
        n nVarA = ad.a(qVar)
        switch (wVarF.d()) {
            case 1:
            case 2:
            case 6:
                alVar = ai(nVarA, zVarG, ae.b(qVar, qVar.h()))
                break
            case 3:
                return
            case 4:
                alVar = al(nVarA, zVarG, ae.b(qVar, qVar.h()), this.d.d.a(this.f307b.c().b(1)))
                break
            case 5:
            default:
                throw RuntimeException("shouldn't happen")
        }
        a(alVar)
    }
}

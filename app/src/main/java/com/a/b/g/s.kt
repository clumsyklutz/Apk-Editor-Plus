package com.a.b.g

import java.util.ArrayList

class s {

    /* renamed from: a, reason: collision with root package name */
    private final an f648a

    private constructor(an anVar) {
        this.f648a = anVar
    }

    fun a(an anVar) {
        s sVar = s(anVar)
        sVar.f648a.a(t(sVar, aa.b()))
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(z zVar, com.a.b.f.b.t tVar, Int i, com.a.b.f.c.a aVar) {
        com.a.b.f.b.i iVarE = zVar.e()
        com.a.b.f.b.w wVarA = com.a.b.f.b.y.a(i, zVar.n(), tVar, aVar)
        z zVar2 = z(aVar == null ? new com.a.b.f.b.q(wVarA, iVarE.g(), zVar.n(), tVar) : new com.a.b.f.b.p(wVarA, iVarE.g(), zVar.n(), tVar, aVar), zVar.o())
        ArrayList arrayListC = zVar.o().c()
        this.f648a.b(zVar)
        arrayListC.set(arrayListC.lastIndexOf(zVar), zVar2)
        this.f648a.a(zVar2)
    }

    static /* synthetic */ Boolean a(com.a.b.f.b.r rVar) {
        Boolean z
        com.a.b.f.d.d dVarH = rVar.h()
        if (!(dVarH is com.a.b.f.c.s)) {
            z = false
        } else {
            if (((com.a.b.f.c.s) dVarH).k() != 0) {
                return false
            }
            z = true
        }
        return z
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Boolean a(s sVar, z zVar) {
        com.a.b.f.b.w wVarF = zVar.e().f()
        com.a.b.f.b.r rVarN = zVar.n()
        if (rVarN != null && !sVar.f648a.a(rVarN) && wVarF.a() != 5) {
            com.a.b.f.d.d dVarH = zVar.n().h()
            if (dVarH.f() && dVarH.c() == 6) {
                sVar.a(zVar, com.a.b.f.b.t.f519a, 5, (com.a.b.f.c.a) dVarH)
                if (wVarF.a() == 56) {
                    sVar.a((z) ((ai) sVar.f648a.j().get(zVar.o().g().nextSetBit(0))).c().get(r0.size() - 1), com.a.b.f.b.t.f519a, 6, null)
                }
                return true
            }
        }
        return false
    }
}

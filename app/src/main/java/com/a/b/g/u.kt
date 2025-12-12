package com.a.b.g

import java.util.ArrayList
import java.util.BitSet

class u {

    /* renamed from: a, reason: collision with root package name */
    private final an f651a

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList f652b
    private final v c
    private final BitSet d

    private constructor(an anVar) {
        if (anVar == null) {
            throw NullPointerException("method == null")
        }
        ArrayList arrayListJ = anVar.j()
        this.f651a = anVar
        this.f652b = arrayListJ
        this.c = v(anVar)
        this.d = BitSet(arrayListJ.size())
    }

    fun a(an anVar) {
        com.a.b.f.b.v vVarE
        u uVar = u(anVar)
        if (uVar.f651a.g() > 0) {
            for (Int iC = uVar.f651a.c(); iC >= 0; iC = uVar.d.nextSetBit(0)) {
                uVar.d.clear(iC)
                com.a.b.f.b.v vVarB = uVar.c.b(iC)
                ai aiVar = (ai) uVar.f652b.get(iC)
                ArrayList arrayListC = aiVar.c()
                Int size = arrayListC.size()
                if (iC != uVar.f651a.e()) {
                    al alVar = (al) arrayListC.get(size - 1)
                    Boolean z = (alVar.e().b().d_() != 0) && alVar.n() != null
                    Int i = size - 1
                    Int i2 = 0
                    com.a.b.f.b.v vVar = vVarB
                    while (i2 < size) {
                        if (z && i2 == i) {
                            vVar.b_()
                            vVarE = vVar.e()
                        } else {
                            vVarE = vVar
                        }
                        al alVar2 = (al) arrayListC.get(i2)
                        com.a.b.f.b.r rVarF = alVar2.f()
                        if (rVarF == null) {
                            com.a.b.f.b.r rVarN = alVar2.n()
                            if (rVarN != null && vVarE.a(rVarN.g()) != null) {
                                vVarE.c(vVarE.a(rVarN.g()))
                            }
                        } else {
                            com.a.b.f.b.r rVarN2 = rVarF.n()
                            if (!rVarN2.equals(vVarE.a(rVarN2))) {
                                com.a.b.f.b.r rVarA = vVarE.a(rVarN2.i())
                                if (rVarA != null && rVarA.g() != rVarN2.g()) {
                                    vVarE.c(rVarA)
                                }
                                uVar.c.a(alVar2, rVarN2)
                                vVarE.d(rVarN2)
                            }
                        }
                        i2++
                        vVar = vVarE
                    }
                    vVar.b_()
                    com.a.b.h.j jVarI = aiVar.i()
                    Int iB = jVarI.b()
                    Int iJ = aiVar.j()
                    for (Int i3 = 0; i3 < iB; i3++) {
                        Int iB2 = jVarI.b(i3)
                        if (uVar.c.a(iB2, iB2 == iJ ? vVar : vVarB)) {
                            uVar.d.set(iB2)
                        }
                    }
                }
            }
        }
        uVar.c.b_()
        return uVar.c
    }
}

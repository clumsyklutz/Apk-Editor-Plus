package com.a.b.g

import java.util.BitSet
import java.util.List

class af {

    /* renamed from: a, reason: collision with root package name */
    private an f599a

    /* renamed from: b, reason: collision with root package name */
    private final BitSet f600b

    private constructor(an anVar) {
        this.f599a = anVar
        this.f600b = BitSet(anVar.g())
    }

    fun a(an anVar) {
        Boolean z
        af afVar = af(anVar)
        Int iG = afVar.f599a.g()
        for (Int i = 0; i < iG; i++) {
            al alVarC = afVar.f599a.c(i)
            if (alVarC != null && alVarC.n().c() == 0) {
                afVar.f600b.set(i)
            }
        }
        while (true) {
            Int iNextSetBit = afVar.f600b.nextSetBit(0)
            if (iNextSetBit < 0) {
                return
            }
            afVar.f600b.clear(iNextSetBit)
            ac acVar = (ac) afVar.f599a.c(iNextSetBit)
            acVar.a(afVar.f599a)
            com.a.b.f.b.t tVarA = acVar.a()
            Int i2 = -1
            Int iD_ = tVarA.d_()
            Int i3 = 0
            com.a.b.f.b.r rVar = null
            while (i3 < iD_) {
                com.a.b.f.b.r rVarB = tVarA.b(i3)
                if (rVarB.c() != 0) {
                    i2 = i3
                } else {
                    rVarB = rVar
                }
                i3++
                rVar = rVarB
            }
            if (rVar == null) {
                z = false
            } else {
                com.a.b.f.b.m mVarI = rVar.i()
                com.a.b.f.d.d dVarA = rVar.a()
                Boolean z2 = true
                for (Int i4 = 0; i4 < iD_; i4++) {
                    if (i4 != i2) {
                        com.a.b.f.b.r rVarB2 = tVarA.b(i4)
                        if (rVarB2.c() != 0) {
                            z2 = z2 && a(mVarI, rVarB2.i())
                            dVarA = com.gmail.heagoo.a.c.a.a(dVarA, rVarB2.a())
                        }
                    }
                }
                if (dVarA == null) {
                    StringBuilder sb = StringBuilder()
                    for (Int i5 = 0; i5 < iD_; i5++) {
                        sb.append(tVarA.b(i5).toString())
                        sb.append(' ')
                    }
                    throw RuntimeException("Couldn't map types in phi insn:" + ((Object) sb))
                }
                com.a.b.f.b.m mVar = z2 ? mVarI : null
                com.a.b.f.b.r rVarN = acVar.n()
                if (rVarN.h() == dVarA && a(mVar, rVarN.i())) {
                    z = false
                } else {
                    acVar.a(dVarA, mVar)
                    z = true
                }
            }
            if (z) {
                List listD = afVar.f599a.d(iNextSetBit)
                Int size = listD.size()
                for (Int i6 = 0; i6 < size; i6++) {
                    al alVar = (al) listD.get(i6)
                    com.a.b.f.b.r rVarN2 = alVar.n()
                    if (rVarN2 != null && (alVar is ac)) {
                        afVar.f600b.set(rVarN2.g())
                    }
                }
            }
        }
    }

    private fun a(com.a.b.f.b.m mVar, com.a.b.f.b.m mVar2) {
        return mVar == mVar2 || (mVar != null && mVar.equals(mVar2))
    }
}

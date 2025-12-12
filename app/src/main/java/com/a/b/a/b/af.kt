package com.a.b.a.b

import java.util.ArrayList
import java.util.BitSet
import java.util.HashMap

final class af {

    /* renamed from: a, reason: collision with root package name */
    private val f185a = HashMap()

    /* renamed from: b, reason: collision with root package name */
    private final BitSet f186b
    private Int c
    private Int d
    private final ad e
    private final ArrayList f
    private /* synthetic */ x g

    af(x xVar, ad adVar, ArrayList arrayList) {
        this.g = xVar
        this.f186b = BitSet(xVar.d)
        this.e = adVar
        this.f = arrayList
    }

    private fun a(Int i) {
        Integer num = (Integer) this.f185a.get(Integer.valueOf(i))
        if (num != null) {
            return num.intValue()
        }
        Int i2 = this.c
        com.a.b.h.j jVar = (com.a.b.h.j) this.f.get(i)
        if (!(jVar != null && jVar.b() > 0 && jVar.d() == i2)) {
            return i
        }
        Int iA = this.e.a()
        this.f186b.set(i)
        this.f185a.put(Integer.valueOf(i), Integer.valueOf(iA))
        while (this.f.size() <= iA) {
            this.f.add(null)
        }
        this.f.set(iA, this.f.get(i))
        return iA
    }

    final Unit a(com.a.b.f.b.a aVar) {
        com.a.b.h.j jVarA
        this.d = aVar.c().b(0)
        this.c = aVar.c().b(1)
        Int iA = a(this.c)
        Int iNextSetBit = this.f186b.nextSetBit(0)
        while (iNextSetBit >= 0) {
            this.f186b.clear(iNextSetBit)
            Int iIntValue = ((Integer) this.f185a.get(Integer.valueOf(iNextSetBit))).intValue()
            com.a.b.f.b.a aVarC = this.g.c(iNextSetBit)
            com.a.b.h.j jVarC = aVarC.c()
            Int i = -1
            if (this.g.a(aVarC)) {
                jVarA = com.a.b.h.j.a(a(jVarC.b(0)), jVarC.b(1))
            } else {
                ae aeVarB = x.b(this.g, iNextSetBit)
                if (aeVarB == null) {
                    Int iD = aVarC.d()
                    Int iB = jVarC.b()
                    com.a.b.h.j jVar = new com.a.b.h.j(iB)
                    Int i2 = 0
                    while (i2 < iB) {
                        Int iB2 = jVarC.b(i2)
                        Int iA2 = a(iB2)
                        jVar.c(iA2)
                        if (iD != iB2) {
                            iA2 = i
                        }
                        i2++
                        i = iA2
                    }
                    jVar.b_()
                    jVarA = jVar
                } else {
                    if (aeVarB.c != this.c) {
                        throw RuntimeException("ret instruction returns to label " + com.gmail.heagoo.a.c.a.v(aeVarB.c) + " expected: " + com.gmail.heagoo.a.c.a.v(this.c))
                    }
                    jVarA = com.a.b.h.j.a(this.d)
                    i = this.d
                }
            }
            this.g.a(new com.a.b.f.b.a(iIntValue, x.a(this.g, aVarC.b()), jVarA, i), (com.a.b.h.j) this.f.get(iIntValue))
            if (this.g.a(this.g.c(iNextSetBit))) {
                af(this.g, this.e, this.f).a(this.g.c(iIntValue))
            }
            iNextSetBit = this.f186b.nextSetBit(0)
        }
        x.a(this.g, new com.a.b.f.b.a(aVar.a(), aVar.b(), com.a.b.h.j.a(iA), iA), (com.a.b.h.j) this.f.get(aVar.a()))
    }
}

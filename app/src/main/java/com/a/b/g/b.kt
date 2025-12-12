package com.a.b.g

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.Map

class b {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f618a = false

    /* renamed from: b, reason: collision with root package name */
    private static Boolean f619b = false
    private final an c

    private constructor(an anVar) {
        this.c = anVar
    }

    fun a(an anVar) {
        b bVar = b(anVar)
        Int iG = bVar.c.g()
        Int iG2 = bVar.c.g()
        HashMap map = HashMap()
        HashSet()
        for (Int i = 0; i < iG2; i++) {
            al alVarC = bVar.c.c(i)
            if (alVarC != null && alVarC.c() != null) {
                com.a.b.f.b.r rVarN = alVarC.n()
                com.a.b.f.d.d dVarH = rVarN.h()
                if (dVarH.f()) {
                    com.a.b.f.c.ab abVar = (com.a.b.f.c.ab) dVarH
                    if (alVarC.c().a() == 56) {
                        alVarC = (al) ((ai) bVar.c.j().get(alVarC.o().g().nextSetBit(0))).c().get(r3.size() - 1)
                    }
                    if (alVarC.j()) {
                        if (abVar is com.a.b.f.c.y) {
                        }
                    } else if (!bVar.c.a(rVarN)) {
                        Integer num = (Integer) map.get(abVar)
                        if (num == null) {
                            map.put(abVar, 1)
                        } else {
                            map.put(abVar, Integer.valueOf(num.intValue() + 1))
                        }
                    }
                }
            }
        }
        ArrayList arrayList = ArrayList()
        for (Map.Entry entry : map.entrySet()) {
            if (((Integer) entry.getValue()).intValue() > 1) {
                arrayList.add(entry.getKey())
            }
        }
        Collections.sort(arrayList, c(bVar, map))
        Int iMin = Math.min(arrayList.size(), 5)
        ai aiVarD = bVar.c.d()
        HashMap map2 = HashMap(iMin)
        Int i2 = 0
        while (true) {
            Int i3 = i2
            if (i3 >= iMin) {
                bVar.a(map2, iG)
                return
            }
            com.a.b.f.c.ab abVar2 = (com.a.b.f.c.ab) arrayList.get(i3)
            com.a.b.f.b.r rVarA = com.a.b.f.b.r.a(bVar.c.o(), abVar2)
            com.a.b.f.b.w wVarF = com.a.b.f.b.y.f(abVar2)
            if (wVarF.d() == 1) {
                aiVarD.a(new com.a.b.f.b.p(com.a.b.f.b.y.f(abVar2), com.a.b.f.b.z.f530a, rVarA, com.a.b.f.b.t.f519a, abVar2))
            } else {
                ai aiVarD2 = bVar.c.d()
                ai aiVarL = aiVarD2.l()
                ai aiVarB = aiVarD2.b(aiVarL)
                aiVarB.b(new com.a.b.f.b.ab(wVarF, com.a.b.f.b.z.f530a, com.a.b.f.b.t.f519a, com.a.b.f.d.b.f565a, abVar2))
                aiVarB.b(aiVarL).a(new com.a.b.f.b.q(com.a.b.f.b.y.e(rVarA.h()), com.a.b.f.b.z.f530a, rVarA, com.a.b.f.b.t.f519a))
            }
            map2.put(abVar2, rVarA)
            i2 = i3 + 1
        }
    }

    private fun a(HashMap map, Int i) {
        com.a.b.f.b.r rVar
        HashSet()
        Array<ArrayList> arrayListArrN = this.c.n()
        for (Int i2 = 0; i2 < i; i2++) {
            al alVarC = this.c.c(i2)
            if (alVarC != null) {
                com.a.b.f.b.r rVarN = alVarC.n()
                com.a.b.f.d.d dVarH = alVarC.n().h()
                if (dVarH.f() && (rVar = (com.a.b.f.b.r) map.get((com.a.b.f.c.ab) dVarH)) != null && !this.c.a(rVarN)) {
                    d dVar = d(this, rVarN, rVar)
                    Iterator it = arrayListArrN[rVarN.g()].iterator()
                    while (it.hasNext()) {
                        al alVar = (al) it.next()
                        if (!alVar.j() || alVar.o().h().cardinality() <= 1) {
                            alVar.a(dVar)
                        }
                    }
                }
            }
        }
    }
}

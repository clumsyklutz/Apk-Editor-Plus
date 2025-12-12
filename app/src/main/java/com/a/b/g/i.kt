package com.a.b.g

import java.util.ArrayList
import java.util.BitSet
import java.util.HashSet

class i {

    /* renamed from: a, reason: collision with root package name */
    private final Boolean f630a

    /* renamed from: b, reason: collision with root package name */
    private final an f631b
    private final ArrayList c
    private final Array<j> d
    private val e = ArrayList()
    private final Array<h> f

    private constructor(an anVar, Array<h> hVarArr, Boolean z) {
        this.f631b = anVar
        this.f = hVarArr
        this.f630a = z
        this.c = anVar.j()
        this.d = new j[this.c.size() + 2]
    }

    private fun a(ai aiVar) {
        j jVar = this.d[aiVar.e()]
        if (jVar.d == null) {
            return aiVar
        }
        if (this.d[this.d[aiVar.e()].d.e()].d != null) {
            ArrayList arrayList = ArrayList()
            HashSet hashSet = HashSet()
            arrayList.add(aiVar)
            while (!arrayList.isEmpty()) {
                Int size = arrayList.size()
                j jVar2 = this.d[((ai) arrayList.get(size - 1)).e()]
                ai aiVar2 = jVar2.d
                j jVar3 = this.d[aiVar2.e()]
                if (!hashSet.add(aiVar2) || jVar3.d == null) {
                    arrayList.remove(size - 1)
                    if (jVar3.d != null) {
                        ai aiVar3 = jVar3.c
                        if (this.d[aiVar3.e()].f632a < this.d[jVar2.c.e()].f632a) {
                            jVar2.c = aiVar3
                        }
                        jVar2.d = jVar3.d
                    }
                } else {
                    arrayList.add(aiVar2)
                }
            }
        }
        return jVar.c
    }

    fun a(an anVar, Array<h> hVarArr, Boolean z) {
        Int i
        Byte b2 = 0
        i iVar = i(anVar, hVarArr, false)
        ai aiVarF = iVar.f630a ? iVar.f631b.f() : iVar.f631b.d()
        if (aiVarF != null) {
            iVar.e.add(aiVarF)
            iVar.f[aiVarF.e()].f629b = aiVarF.e()
        }
        iVar.f631b.a(iVar.f630a, k(iVar, b2))
        Int size = iVar.e.size() - 1
        for (Int i2 = size; i2 >= 2; i2--) {
            ai aiVar = (ai) iVar.e.get(i2)
            j jVar = iVar.d[aiVar.e()]
            BitSet bitSetH = iVar.f630a ? aiVar.h() : aiVar.g()
            for (Int iNextSetBit = bitSetH.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetH.nextSetBit(iNextSetBit + 1)) {
                ai aiVar2 = (ai) iVar.c.get(iNextSetBit)
                if (iVar.d[aiVar2.e()] != null && (i = iVar.d[iVar.a(aiVar2).e()].f632a) < jVar.f632a) {
                    jVar.f632a = i
                }
            }
            iVar.d[((ai) iVar.e.get(jVar.f632a)).e()].e.add(aiVar)
            jVar.d = jVar.f633b
            ArrayList arrayList = iVar.d[jVar.f633b.e()].e
            while (!arrayList.isEmpty()) {
                ai aiVar3 = (ai) arrayList.remove(arrayList.size() - 1)
                ai aiVarA = iVar.a(aiVar3)
                if (iVar.d[aiVarA.e()].f632a < iVar.d[aiVar3.e()].f632a) {
                    iVar.f[aiVar3.e()].f629b = aiVarA.e()
                } else {
                    iVar.f[aiVar3.e()].f629b = jVar.f633b.e()
                }
            }
        }
        for (Int i3 = 2; i3 <= size; i3++) {
            ai aiVar4 = (ai) iVar.e.get(i3)
            if (iVar.f[aiVar4.e()].f629b != ((ai) iVar.e.get(iVar.d[aiVar4.e()].f632a)).e()) {
                iVar.f[aiVar4.e()].f629b = iVar.f[iVar.f[aiVar4.e()].f629b].f629b
            }
        }
        return iVar
    }
}

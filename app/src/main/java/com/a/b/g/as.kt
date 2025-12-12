package com.a.b.g

import java.util.ArrayList
import java.util.BitSet
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator

final class as implements am {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ aq f614a

    /* renamed from: b, reason: collision with root package name */
    private final ai f615b
    private final com.a.b.f.b.Array<r> c
    private val d = HashSet()
    private val e = HashMap()
    private val f = au(this)

    as(aq aqVar, ai aiVar) {
        this.f614a = aqVar
        this.f615b = aiVar
        this.c = aqVar.e[aiVar.e()]
        aqVar.e[aiVar.e()] = null
    }

    private fun a(Int i, com.a.b.f.b.r rVar) {
        Int iG = rVar.g()
        com.a.b.f.b.m mVarI = rVar.i()
        this.c[i] = rVar
        for (Int length = this.c.length - 1; length >= 0; length--) {
            if (iG == this.c[length].g()) {
                this.c[length] = rVar
            }
        }
        if (mVarI == null) {
            return
        }
        aq.a(this.f614a, rVar)
        for (Int length2 = this.c.length - 1; length2 >= 0; length2--) {
            com.a.b.f.b.r rVar2 = this.c[length2]
            if (iG != rVar2.g() && mVarI.equals(rVar2.i())) {
                this.c[length2] = rVar2.a((com.a.b.f.b.m) null)
            }
        }
    }

    private fun a(al alVar) {
        com.a.b.f.b.r rVarN = alVar.n()
        if (rVarN == null) {
            return
        }
        Int iG = rVarN.g()
        if (aq.b(this.f614a, iG)) {
            return
        }
        alVar.c(this.f614a.f612b)
        a(iG, alVar.n())
        aq.d(this.f614a)
    }

    public final Unit a() {
        Boolean z
        this.f615b.a(this)
        at atVar = at(this)
        BitSet bitSetH = this.f615b.h()
        for (Int iNextSetBit = bitSetH.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetH.nextSetBit(iNextSetBit + 1)) {
            ((ai) this.f614a.f611a.j().get(iNextSetBit)).a(atVar)
        }
        ArrayList arrayListC = this.f615b.c()
        for (Int size = arrayListC.size() - 1; size >= 0; size--) {
            al alVar = (al) arrayListC.get(size)
            al alVar2 = (al) this.e.get(alVar)
            if (alVar2 != null) {
                arrayListC.set(size, alVar2)
            } else if (alVar.h() && !this.d.contains(alVar)) {
                arrayListC.remove(size)
            }
        }
        Boolean z2 = true
        Iterator it = this.f615b.a().iterator()
        while (it.hasNext()) {
            ai aiVar = (ai) it.next()
            if (aiVar != this.f615b) {
                this.f614a.e[aiVar.e()] = z2 ? this.c : aq.a(this.c)
                z = false
            } else {
                z = z2
            }
            z2 = z
        }
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
        a((al) acVar)
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
        com.a.b.f.b.r rVarN = zVar.n()
        Int iG = rVarN.g()
        Int iG2 = zVar.a().b(0).g()
        zVar.a(this.f)
        Int iG3 = zVar.a().b(0).g()
        com.a.b.f.b.m mVarI = this.c[iG2].i()
        com.a.b.f.b.m mVarI2 = rVarN.i()
        if (mVarI2 == null) {
            mVarI2 = mVarI
        }
        com.a.b.f.b.m mVarA = aq.a(this.f614a, iG3)
        Boolean z = mVarA == null || mVarI2 == null || mVarI2.equals(mVarA)
        com.a.b.f.b.r rVarB = com.a.b.f.b.r.b(iG3, rVarN.a(), mVarI2)
        if (!aa.a() || (z && aq.a(mVarI2, mVarI) && this.f614a.d == 0)) {
            a(iG, rVarB)
            return
        }
        if (z && mVarI == null && this.f614a.d == 0) {
            this.e.put(zVar, al.a(new com.a.b.f.b.q(com.a.b.f.b.y.g(rVarB), com.a.b.f.b.z.f530a, (com.a.b.f.b.r) null, com.a.b.f.b.t.a(com.a.b.f.b.r.a(rVarB.g(), rVarB.a(), mVarI2))), this.f615b))
            a(iG, rVarB)
        } else {
            a((al) zVar)
            this.d.add(zVar)
        }
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        zVar.a(this.f)
        a((al) zVar)
    }
}

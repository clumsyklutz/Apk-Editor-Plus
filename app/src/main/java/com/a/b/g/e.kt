package com.a.b.g

import java.util.ArrayList
import java.util.BitSet
import java.util.HashSet
import java.util.Iterator

class e {

    /* renamed from: a, reason: collision with root package name */
    private final an f623a

    /* renamed from: b, reason: collision with root package name */
    private final Int f624b
    private final BitSet c
    private final Array<ArrayList> d

    private constructor(an anVar) {
        this.f623a = anVar
        this.f624b = anVar.g()
        this.c = BitSet(this.f624b)
        this.d = this.f623a.n()
    }

    fun a(com.a.b.f.b.x xVar, Int i, Boolean z) {
        an anVarA = an.a(xVar, i, z)
        ArrayList arrayListJ = anVarA.j()
        for (Int size = arrayListJ.size() - 1; size >= 0; size--) {
            ai aiVar = (ai) arrayListJ.get(size)
            if (aiVar.g().cardinality() > 1 && aiVar.h().cardinality() > 1) {
                aiVar.o()
            }
        }
        ArrayList arrayListJ2 = anVarA.j()
        for (Int size2 = arrayListJ2.size() - 1; size2 >= 0; size2--) {
            ai aiVar2 = (ai) arrayListJ2.get(size2)
            if (!aiVar2.q() && aiVar2.g().cardinality() > 1 && ((al) aiVar2.c().get(0)).i()) {
                BitSet bitSet = (BitSet) aiVar2.g().clone()
                for (Int iNextSetBit = bitSet.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1)) {
                    ((ai) arrayListJ2.get(iNextSetBit)).b(aiVar2).c().add(0, ((al) aiVar2.c().get(0)).clone())
                }
                aiVar2.c().remove(0)
            }
        }
        ArrayList arrayListJ3 = anVarA.j()
        for (Int size3 = arrayListJ3.size() - 1; size3 >= 0; size3--) {
            ai aiVar3 = (ai) arrayListJ3.get(size3)
            BitSet bitSet2 = (BitSet) aiVar3.h().clone()
            for (Int iNextSetBit2 = bitSet2.nextSetBit(0); iNextSetBit2 >= 0; iNextSetBit2 = bitSet2.nextSetBit(iNextSetBit2 + 1)) {
                ai aiVar4 = (ai) arrayListJ3.get(iNextSetBit2)
                al alVar = (al) aiVar3.c().get(r0.size() - 1)
                if ((alVar.n() != null || alVar.a().d_() > 0) && aiVar4.g().cardinality() > 1) {
                    aiVar3.b(aiVar4)
                }
            }
        }
        a(anVarA, u.a(anVarA), 0)
        aq(anVarA).run()
        anVarA.a()
        return anVarA
    }

    public static com.a.b.h.k a(Int i) {
        return i <= 3072 ? new com.a.b.h.a(i) : new com.a.b.h.n()
    }

    fun a(an anVar) {
        e eVar = e(anVar)
        HashSet hashSet = HashSet()
        eVar.f623a.l()
        Iterator it = eVar.f623a.j().iterator()
        while (it.hasNext()) {
            ai aiVar = (ai) it.next()
            if (!aiVar.r()) {
                for (Int i = 0; i < aiVar.c().size(); i++) {
                    al alVar = (al) aiVar.c().get(i)
                    com.a.b.f.b.t tVarA = alVar.a()
                    Int iD_ = tVarA.d_()
                    if (iD_ != 0) {
                        hashSet.add(alVar)
                    }
                    for (Int i2 = 0; i2 < iD_; i2++) {
                        eVar.d[tVarA.b(i2).g()].remove(alVar)
                    }
                    com.a.b.f.b.r rVarN = alVar.n()
                    if (rVarN != null) {
                        Iterator it2 = eVar.d[rVarN.g()].iterator()
                        while (it2.hasNext()) {
                            al alVar2 = (al) it2.next()
                            if (alVar2 is ac) {
                                ((ac) alVar2).a(rVarN)
                            }
                        }
                    }
                }
            }
        }
        eVar.f623a.a(hashSet)
        HashSet hashSet2 = HashSet()
        eVar.f623a.a(f(eVar.c))
        while (true) {
            Int iNextSetBit = eVar.c.nextSetBit(0)
            if (iNextSetBit < 0) {
                eVar.f623a.a(hashSet2)
                return
            }
            eVar.c.clear(iNextSetBit)
            if (eVar.d[iNextSetBit].size() == 0 || eVar.a(iNextSetBit, (BitSet) null)) {
                al alVarC = eVar.f623a.c(iNextSetBit)
                if (!hashSet2.contains(alVarC)) {
                    com.a.b.f.b.t tVarA2 = alVarC.a()
                    Int iD_2 = tVarA2.d_()
                    for (Int i3 = 0; i3 < iD_2; i3++) {
                        com.a.b.f.b.r rVarB = tVarA2.b(i3)
                        eVar.d[rVarB.g()].remove(alVarC)
                        if (!b(eVar.f623a.c(rVarB.g()))) {
                            eVar.c.set(rVarB.g())
                        }
                    }
                    hashSet2.add(alVarC)
                }
            }
        }
    }

    fun a(an anVar, Int i) {
        a(anVar, u.a(anVar), i)
        aq(anVar, i).run()
    }

    private fun a(an anVar, v vVar, Int i) {
        ArrayList arrayListJ = anVar.j()
        Int size = arrayListJ.size()
        Int iG = anVar.g() - i
        Array<h> hVarArrA = g(anVar).a()
        Array<BitSet> bitSetArr = new BitSet[iG]
        Array<BitSet> bitSetArr2 = new BitSet[iG]
        for (Int i2 = 0; i2 < iG; i2++) {
            bitSetArr[i2] = BitSet(size)
            bitSetArr2[i2] = BitSet(size)
        }
        Int size2 = arrayListJ.size()
        for (Int i3 = 0; i3 < size2; i3++) {
            Iterator it = ((ai) arrayListJ.get(i3)).c().iterator()
            while (it.hasNext()) {
                com.a.b.f.b.r rVarN = ((al) it.next()).n()
                if (rVarN != null && rVarN.g() - i >= 0) {
                    bitSetArr[rVarN.g() - i].set(i3)
                }
            }
        }
        for (Int i4 = 0; i4 < iG; i4++) {
            BitSet bitSet = (BitSet) bitSetArr[i4].clone()
            while (true) {
                Int iNextSetBit = bitSet.nextSetBit(0)
                if (iNextSetBit >= 0) {
                    bitSet.clear(iNextSetBit)
                    com.a.b.h.i iVarB = hVarArrA[iNextSetBit].f628a.b()
                    while (iVarB.a()) {
                        Int iB = iVarB.b()
                        if (!bitSetArr2[i4].get(iB)) {
                            bitSetArr2[i4].set(iB)
                            Int i5 = i4 + i
                            com.a.b.f.b.r rVarA = vVar.a(iB).a(i5)
                            if (rVarA == null) {
                                ((ai) arrayListJ.get(iB)).a(i5)
                            } else {
                                ((ai) arrayListJ.get(iB)).a(rVarA)
                            }
                            if (!bitSetArr[i4].get(iB)) {
                                bitSet.set(iB)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun a(Int i, BitSet bitSet) {
        if (bitSet != null && bitSet.get(i)) {
            return true
        }
        Iterator it = this.d[i].iterator()
        while (it.hasNext()) {
            if (b((al) it.next())) {
                return false
            }
        }
        if (bitSet == null) {
            bitSet = BitSet(this.f624b)
        }
        bitSet.set(i)
        Iterator it2 = this.d[i].iterator()
        while (it2.hasNext()) {
            com.a.b.f.b.r rVarN = ((al) it2.next()).n()
            if (rVarN == null || !a(rVarN.g(), bitSet)) {
                return false
            }
        }
        return true
    }

    static com.a.b.h.k b(Int i) {
        return i <= 3072 ? new com.a.b.h.a(i) : new com.a.b.h.n()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(al alVar) {
        if (alVar == null) {
            return true
        }
        return alVar.l()
    }
}

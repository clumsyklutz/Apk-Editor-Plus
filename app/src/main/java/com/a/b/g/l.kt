package com.a.b.g

import androidx.appcompat.R
import java.util.ArrayList
import java.util.HashSet
import java.util.Iterator

class l {

    /* renamed from: a, reason: collision with root package name */
    private an f636a

    /* renamed from: b, reason: collision with root package name */
    private Int f637b
    private ArrayList c = ArrayList()

    private constructor(an anVar) {
        this.f636a = anVar
        this.f637b = anVar.g()
    }

    private fun a(com.a.b.f.b.r rVar) {
        Int i
        Int i2 = 0
        while (true) {
            i = i2
            if (i >= this.c.size() || ((p) this.c.get(i)).f642a.get(rVar.g())) {
                break
            }
            i2 = i + 1
        }
        return i
    }

    private fun a(al alVar) {
        return (al) ((ai) this.f636a.j().get(alVar.o().g().nextSetBit(0))).c().get(r0.size() - 1)
    }

    private fun a() {
        for (Int i = 0; i < this.f636a.g(); i++) {
            al alVarC = this.f636a.c(i)
            if (alVarC != null && alVarC.c() != null && alVarC.c().a() == 2) {
                Array<ArrayList> arrayListArrN = this.f636a.n()
                com.a.b.f.b.r rVarB = alVarC.a().b(0)
                com.a.b.f.b.r rVarN = alVarC.n()
                if (rVarB.g() >= this.f637b || rVarN.g() >= this.f637b) {
                    m mVar = m(this, rVarN, rVarB)
                    Iterator it = arrayListArrN[rVarN.g()].iterator()
                    while (it.hasNext()) {
                        ((al) it.next()).a(mVar)
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x00e6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x002a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(com.a.b.f.b.r r10, com.a.b.g.p r11) {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.g.l.a(com.a.b.f.b.r, com.a.b.g.p):Unit")
    }

    private fun a(al alVar, Int i, ArrayList arrayList) {
        com.a.b.f.d.c cVarA = alVar.n().a()
        for (Int i2 = 0; i2 < i; i2++) {
            com.a.b.f.c.a aVarA = com.gmail.heagoo.a.c.a.a(cVarA.s())
            com.a.b.f.b.r rVarA = com.a.b.f.b.r.a(this.f636a.o(), (com.a.b.f.c.ab) aVarA)
            arrayList.add(rVarA)
            a(alVar, com.a.b.f.b.t.f519a, rVarA, 5, aVarA)
        }
    }

    private fun a(al alVar, com.a.b.f.b.r rVar, HashSet hashSet) {
        com.a.b.f.c.z zVar = new com.a.b.f.c.z(com.a.b.f.b.g.f503a)
        b(alVar, com.a.b.f.b.t.f519a, null, 40, zVar)
        ai aiVarO = alVar.o()
        ai aiVarB = aiVarO.b(aiVarO.l())
        al alVar2 = (al) aiVarB.c().get(0)
        com.a.b.f.b.r rVarA = com.a.b.f.b.r.a(this.f636a.o(), zVar)
        a(alVar2, com.a.b.f.b.t.f519a, rVarA, 56, null)
        ai aiVarB2 = aiVarB.b(aiVarB.l())
        al alVar3 = (al) aiVarB2.c().get(0)
        b(alVar3, com.a.b.f.b.t.a(rVarA, rVar), null, 52, new com.a.b.f.c.v(zVar, new com.a.b.f.c.w(new com.a.b.f.c.y("<init>"), new com.a.b.f.c.y("(I)V"))))
        hashSet.add(alVar3)
        ai aiVarB3 = aiVarB2.b(aiVarB2.l())
        al alVar4 = (al) aiVarB3.c().get(0)
        b(alVar4, com.a.b.f.b.t.a(rVarA), null, 35, null)
        aiVarB3.a(aiVarB3.j(), this.f636a.f().e())
        hashSet.add(alVar4)
    }

    private fun a(al alVar, com.a.b.f.b.t tVar, com.a.b.f.b.r rVar, Int i, com.a.b.f.c.a aVar) {
        com.a.b.f.b.i iVarE = alVar.e()
        com.a.b.f.b.w wVarE = i == 56 ? com.a.b.f.b.y.e(rVar.a()) : com.a.b.f.b.y.a(i, rVar, tVar, aVar)
        z zVar = z(aVar == null ? new com.a.b.f.b.q(wVarE, iVarE.g(), rVar, tVar) : new com.a.b.f.b.p(wVarE, iVarE.g(), rVar, tVar, aVar), alVar.o())
        ArrayList arrayListC = alVar.o().c()
        arrayListC.add(arrayListC.lastIndexOf(alVar), zVar)
        this.f636a.a(zVar)
    }

    private fun a(al alVar, al alVar2, ArrayList arrayList, HashSet hashSet) {
        Int size = arrayList.size()
        switch (alVar.c().a()) {
            case 34:
                Object objH = alVar2.a().b(0).h()
                al alVarB = b(alVar)
                a(alVarB, com.a.b.f.b.t.f519a, alVarB.n(), 5, (com.a.b.f.c.a) objH)
                hashSet.add(alVarB)
                break
            case 38:
                al alVarB2 = b(alVar)
                com.a.b.f.b.t tVarA = alVar.a()
                Int iJ = ((com.a.b.f.c.s) tVarA.b(1).h()).j()
                if (iJ < size) {
                    com.a.b.f.b.r rVar = (com.a.b.f.b.r) arrayList.get(iJ)
                    a(alVarB2, com.a.b.f.b.t.a(rVar), rVar.a(alVarB2.n().g()), 2, null)
                } else {
                    a(alVarB2, tVarA.b(1), hashSet)
                    hashSet.add(alVarB2.o().c().get(2))
                }
                hashSet.add(alVarB2)
                break
            case 39:
                com.a.b.f.b.t tVarA2 = alVar.a()
                Int iJ2 = ((com.a.b.f.c.s) tVarA2.b(2).h()).j()
                if (iJ2 < size) {
                    com.a.b.f.b.r rVarB = tVarA2.b(0)
                    com.a.b.f.b.r rVarA = rVarB.a(((com.a.b.f.b.r) arrayList.get(iJ2)).g())
                    a(alVar, com.a.b.f.b.t.a(rVarB), rVarA, 2, null)
                    arrayList.set(iJ2, rVarA.n())
                    break
                } else {
                    a(alVar, tVarA2.b(2), hashSet)
                    break
                }
            case R.styleable.AppCompatTheme_dividerVertical /* 57 */:
                ArrayList arrayListC = ((com.a.b.f.b.h) alVar.e()).c()
                for (Int i = 0; i < size; i++) {
                    com.a.b.f.b.r rVarA2 = com.a.b.f.b.r.a(((com.a.b.f.b.r) arrayList.get(i)).g(), (com.a.b.f.d.d) arrayListC.get(i))
                    a(alVar, com.a.b.f.b.t.f519a, rVarA2, 5, (com.a.b.f.c.a) arrayListC.get(i))
                    arrayList.set(i, rVarA2)
                }
                break
        }
    }

    fun a(an anVar) {
        l lVar = l(anVar)
        lVar.f636a.a(n(lVar))
        Iterator it = lVar.c.iterator()
        while (it.hasNext()) {
            p pVar = (p) it.next()
            if (pVar.f643b != q.f644a) {
                Iterator it2 = pVar.c.iterator()
                while (it2.hasNext()) {
                    p pVar2 = (p) it2.next()
                    if (pVar.f643b.compareTo(pVar2.f643b) > 0) {
                        pVar2.f643b = pVar.f643b
                    }
                }
            }
        }
        Iterator it3 = lVar.c.iterator()
        while (it3.hasNext()) {
            p pVar3 = (p) it3.next()
            if (pVar3.e && pVar3.f643b == q.f644a) {
                Int iNextSetBit = pVar3.f642a.nextSetBit(0)
                al alVarC = lVar.f636a.c(iNextSetBit)
                al alVarA = lVar.a(alVarC)
                Int iJ = ((com.a.b.f.c.s) alVarA.a().b(0).h()).j()
                ArrayList arrayList = ArrayList(iJ)
                HashSet hashSet = HashSet()
                lVar.a(alVarC, iJ, arrayList)
                hashSet.add(alVarA)
                hashSet.add(alVarC)
                for (al alVar : lVar.f636a.d(iNextSetBit)) {
                    lVar.a(alVar, alVarA, arrayList, hashSet)
                    hashSet.add(alVar)
                }
                lVar.f636a.a(hashSet)
                lVar.f636a.m()
                e.a(lVar.f636a, lVar.f637b)
                lVar.a()
            }
        }
    }

    static /* synthetic */ Unit a(l lVar, al alVar) {
        p pVar
        Int iA = alVar.c().a()
        com.a.b.f.b.r rVarN = alVar.n()
        if (iA != 56 || rVarN.h().c() != 9) {
            if (iA == 3 && rVarN.h().c() == 9) {
                p pVar2 = p(rVarN.g(), lVar.f637b, q.f644a)
                lVar.c.add(pVar2)
                lVar.a(rVarN, pVar2)
                return
            } else {
                if (iA == 55 && rVarN.h().c() == 9) {
                    p pVar3 = p(rVarN.g(), lVar.f637b, q.f644a)
                    lVar.c.add(pVar3)
                    lVar.a(rVarN, pVar3)
                    return
                }
                return
            }
        }
        com.a.b.f.b.r rVarN2 = alVar.n()
        al alVarA = lVar.a(alVar)
        switch (alVarA.c().a()) {
            case 5:
            case 40:
                pVar = p(rVarN2.g(), lVar.f637b, q.f644a)
                lVar.c.add(pVar)
                break
            case 38:
            case 43:
            case 45:
                com.a.b.f.b.r rVarB = alVarA.a().b(0)
                Int iA2 = lVar.a(rVarB)
                if (iA2 == lVar.c.size()) {
                    pVar = rVarB.a() == com.a.b.f.d.c.j ? p(rVarN2.g(), lVar.f637b, q.f644a) : p(rVarN2.g(), lVar.f637b, q.d)
                    lVar.c.add(pVar)
                    break
                } else {
                    pVar = (p) lVar.c.get(iA2)
                    pVar.f642a.set(rVarN2.g())
                    break
                }
            case 41:
            case 42:
                if (alVarA.a().b(0).h().f()) {
                    pVar = p(rVarN2.g(), lVar.f637b, q.f644a)
                    pVar.e = true
                } else {
                    pVar = p(rVarN2.g(), lVar.f637b, q.d)
                }
                lVar.c.add(pVar)
                break
            case 46:
                pVar = p(rVarN2.g(), lVar.f637b, q.d)
                lVar.c.add(pVar)
                break
            default:
                pVar = null
                break
        }
        lVar.a(rVarN, pVar)
    }

    private fun a(p pVar, p pVar2) {
        if (!pVar2.d.contains(pVar)) {
            pVar2.d.add(pVar)
        }
        if (pVar.c.contains(pVar2)) {
            return
        }
        pVar.c.add(pVar2)
    }

    private fun b(al alVar) {
        return (al) ((ai) this.f636a.j().get(alVar.o().h().nextSetBit(0))).c().get(0)
    }

    private fun b(al alVar, com.a.b.f.b.t tVar, com.a.b.f.b.r rVar, Int i, com.a.b.f.c.a aVar) {
        com.a.b.f.b.i iVarE = alVar.e()
        com.a.b.f.b.w wVarA = com.a.b.f.b.y.a(i, null, tVar, aVar)
        z zVar = z(aVar == null ? new com.a.b.f.b.ac(wVarA, iVarE.g(), tVar, com.a.b.f.d.b.f565a) : new com.a.b.f.b.ab(wVarA, iVarE.g(), tVar, com.a.b.f.d.b.f565a, aVar), alVar.o())
        ArrayList arrayListC = alVar.o().c()
        arrayListC.add(arrayListC.lastIndexOf(alVar), zVar)
        this.f636a.a(zVar)
    }

    private fun b(p pVar, p pVar2) {
        Iterator it = pVar2.d.iterator()
        while (it.hasNext()) {
            p pVar3 = (p) it.next()
            pVar3.c.remove(pVar2)
            pVar3.c.add(pVar)
            pVar.d.add(pVar3)
        }
        Iterator it2 = pVar2.c.iterator()
        while (it2.hasNext()) {
            p pVar4 = (p) it2.next()
            pVar4.d.remove(pVar2)
            pVar4.d.add(pVar)
            pVar.c.add(pVar4)
        }
    }
}

package com.a.b.g

import java.util.ArrayList
import java.util.BitSet
import java.util.Iterator

class ah {

    /* renamed from: a, reason: collision with root package name */
    private an f601a

    /* renamed from: b, reason: collision with root package name */
    private Int f602b
    private Array<Int> c
    private com.a.b.f.c.Array<a> d
    private BitSet g
    private ArrayList e = ArrayList()
    private ArrayList f = ArrayList()
    private ArrayList h = ArrayList()
    private ArrayList i = ArrayList()
    private ArrayList j = ArrayList()

    private constructor(an anVar) {
        this.f601a = anVar
        this.f602b = anVar.g()
        this.c = new Int[this.f602b]
        this.d = new com.a.b.f.c.a[this.f602b]
        this.g = BitSet(anVar.j().size())
        for (Int i = 0; i < this.f602b; i++) {
            this.c[i] = 0
            this.d[i] = null
        }
    }

    private fun a(Int i, Int i2) {
        if (i2 == 2) {
            Iterator it = this.f601a.d(i).iterator()
            while (it.hasNext()) {
                this.i.add((al) it.next())
            }
            return
        }
        Iterator it2 = this.f601a.d(i).iterator()
        while (it2.hasNext()) {
            this.h.add((al) it2.next())
        }
    }

    private fun a(ac acVar) {
        Int iG = acVar.n().g()
        if (this.c[iG] == 2) {
            return
        }
        com.a.b.f.b.t tVarA = acVar.a()
        Int iD_ = tVarA.d_()
        Int i = 0
        Int i2 = 0
        com.a.b.f.c.a aVar = null
        while (true) {
            if (i >= iD_) {
                break
            }
            Int iA = acVar.a(i)
            Int iG2 = tVarA.b(i).g()
            Int i3 = this.c[iG2]
            if (this.g.get(iA)) {
                if (i3 != 1) {
                    i2 = i3
                    break
                } else if (aVar == null) {
                    aVar = this.d[iG2]
                    i2 = 1
                } else if (!this.d[iG2].equals(aVar)) {
                    i2 = 2
                    break
                }
            }
            i++
        }
        if (a(iG, i2, aVar)) {
            a(iG, i2)
        }
    }

    private fun a(ai aiVar) {
        if (this.g.get(aiVar.e())) {
            this.f.add(aiVar)
        } else {
            this.e.add(aiVar)
            this.g.set(aiVar.e())
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:163:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d0 A[PHI: r4
  0x00d0: PHI (r4v7 com.a.b.f.c.a) = (r4v0 com.a.b.f.c.a), (r4v0 com.a.b.f.c.a), (r4v2 com.a.b.f.c.a), (r4v0 com.a.b.f.c.a) binds: [B:39:0x00cd, B:157:0x027c, B:123:0x0209, B:113:0x01be] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x012d  */
    /* JADX WARN: Type inference failed for: r1v33, types: [com.a.b.f.c.Array<a>] */
    /* JADX WARN: Type inference failed for: r1v34 */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.a.b.f.c.Array<a>] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private fun a(com.a.b.g.al r12) {
        /*
            Method dump skipped, instructions count: 790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.a.b.g.ah.a(com.a.b.g.al):Unit")
    }

    fun a(an anVar) {
        ah ahVar = ah(anVar)
        ahVar.a(ahVar.f601a.d())
        while (true) {
            if (ahVar.e.isEmpty() && ahVar.f.isEmpty() && ahVar.h.isEmpty() && ahVar.i.isEmpty()) {
                break
            }
            while (!ahVar.e.isEmpty()) {
                Iterator it = ((ai) ahVar.e.remove(ahVar.e.size() - 1)).c().iterator()
                while (it.hasNext()) {
                    al alVar = (al) it.next()
                    if (alVar is ac) {
                        ahVar.a((ac) alVar)
                    } else {
                        ahVar.a(alVar)
                    }
                }
            }
            while (!ahVar.f.isEmpty()) {
                Iterator it2 = ((ai) ahVar.f.remove(ahVar.f.size() - 1)).c().iterator()
                while (it2.hasNext()) {
                    al alVar2 = (al) it2.next()
                    if (alVar2 is ac) {
                        ahVar.a((ac) alVar2)
                    }
                }
            }
            while (!ahVar.i.isEmpty()) {
                al alVar3 = (al) ahVar.i.remove(ahVar.i.size() - 1)
                if (ahVar.g.get(alVar3.o().e())) {
                    if (alVar3 is ac) {
                        ahVar.a((ac) alVar3)
                    } else {
                        ahVar.a(alVar3)
                    }
                }
            }
            while (!ahVar.h.isEmpty()) {
                al alVar4 = (al) ahVar.h.remove(ahVar.h.size() - 1)
                if (ahVar.g.get(alVar4.o().e())) {
                    if (alVar4 is ac) {
                        ahVar.a((ac) alVar4)
                    } else {
                        ahVar.a(alVar4)
                    }
                }
            }
        }
        for (Int i = 0; i < ahVar.f602b; i++) {
            if (ahVar.c[i] == 1 && (ahVar.d[i] instanceof com.a.b.f.c.ab)) {
                al alVarC = ahVar.f601a.c(i)
                if (!alVarC.n().h().f()) {
                    alVarC.b(alVarC.n().a((com.a.b.f.c.ab) ahVar.d[i]))
                    for (al alVar5 : ahVar.f601a.d(i)) {
                        if (!alVar5.k()) {
                            z zVar = (z) alVar5
                            com.a.b.f.b.t tVarA = alVar5.a()
                            Int iC = tVarA.c(i)
                            zVar.a(iC, tVarA.b(iC).a((com.a.b.f.c.ab) ahVar.d[i]))
                        }
                    }
                }
            }
        }
        Iterator it3 = ahVar.j.iterator()
        while (it3.hasNext()) {
            al alVar6 = (al) it3.next()
            ai aiVarO = alVar6.o()
            Int iB = aiVarO.i().b()
            Int i2 = 0
            Int i3 = -1
            while (i2 < iB) {
                Int iB2 = aiVarO.i().b(i2)
                if (ahVar.g.get(iB2)) {
                    iB2 = i3
                }
                i2++
                i3 = iB2
            }
            if (iB == 2 && i3 != -1) {
                aiVarO.b(new com.a.b.f.b.q(com.a.b.f.b.y.s, alVar6.e().g(), (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a))
                aiVarO.b(i3)
            }
        }
    }

    private fun a(Int i, Int i2, com.a.b.f.c.a aVar) {
        if (i2 != 1) {
            if (this.c[i] == i2) {
                return false
            }
            this.c[i] = i2
            return true
        }
        if (this.c[i] == i2 && this.d[i].equals(aVar)) {
            return false
        }
        this.c[i] = i2
        this.d[i] = aVar
        return true
    }
}

package com.a.b.a.b

import java.util.ArrayList
import java.util.BitSet
import java.util.Iterator

class x {

    /* renamed from: a, reason: collision with root package name */
    private final l f225a

    /* renamed from: b, reason: collision with root package name */
    private final d f226b
    private final Int c
    private final Int d
    private final ag e
    private final ai f
    private final Array<n> g
    private final ArrayList h
    private final ArrayList i
    private final Array<aa> j
    private Boolean k
    private final Array<ae> l
    private Boolean m
    private final ac n

    private constructor(l lVar, com.a.b.f.b.ad adVar, com.a.b.a.e.h hVar) {
        if (lVar == null) {
            throw NullPointerException("method == null")
        }
        if (adVar == null) {
            throw NullPointerException("advice == null")
        }
        this.f225a = lVar
        this.f226b = b.a(lVar)
        this.d = this.f226b.j()
        this.c = lVar.j()
        this.e = ag(this, lVar, adVar, hVar)
        this.f = ai(this.e, lVar)
        this.g = new n[this.d]
        this.l = new ae[this.d]
        this.h = ArrayList((this.f226b.d_() << 1) + 10)
        this.i = ArrayList((this.f226b.d_() << 1) + 10)
        this.j = new aa[this.d]
        this.k = false
        this.g[0] = n(this.c, lVar.i())
        this.n = ac(this)
    }

    private fun a(Int i) {
        return this.d + this.f225a.l().d_() + (i ^ (-1))
    }

    static /* synthetic */ com.a.b.f.b.l a(x xVar, com.a.b.f.b.l lVar) {
        Int i
        Int i2 = 0
        Int iD_ = lVar.d_()
        Int i3 = 0
        for (Int i4 = 0; i4 < iD_; i4++) {
            if (lVar.a(i4).f() != com.a.b.f.b.y.g) {
                i3++
            }
        }
        if (i3 == iD_) {
            return lVar
        }
        com.a.b.f.b.l lVar2 = new com.a.b.f.b.l(i3)
        Int i5 = 0
        while (i5 < iD_) {
            com.a.b.f.b.i iVarA = lVar.a(i5)
            if (iVarA.f() != com.a.b.f.b.y.g) {
                i = i2 + 1
                lVar2.a(i2, iVarA)
            } else {
                i = i2
            }
            i5++
            i2 = i
        }
        lVar2.b_()
        return lVar2
    }

    public static com.a.b.f.b.x a(l lVar, com.a.b.f.b.ad adVar, com.a.b.a.e.h hVar) {
        com.a.b.f.b.l lVar2
        try {
            x xVar = x(lVar, adVar, hVar)
            Array<Int> iArrS = com.gmail.heagoo.a.c.a.s(xVar.d)
            com.gmail.heagoo.a.c.a.b(iArrS, 0)
            q qVarM = xVar.f225a.m()
            com.a.b.f.b.z zVarA = xVar.f225a.a(0)
            com.a.b.f.d.b bVarB = xVar.f225a.g().b()
            Int iD_ = bVarB.d_()
            com.a.b.f.b.l lVar3 = new com.a.b.f.b.l(iD_ + 1)
            Int i = 0
            for (Int i2 = 0; i2 < iD_; i2++) {
                com.a.b.f.d.c cVarB = bVarB.b(i2)
                r rVarA = qVarM.a(0, i)
                lVar3.a(i2, (com.a.b.f.b.i) new com.a.b.f.b.p(com.a.b.f.b.y.b(cVarB), zVarA, rVarA == null ? com.a.b.f.b.r.a(i, cVarB) : com.a.b.f.b.r.b(i, cVarB, rVarA.a()), com.a.b.f.b.t.f519a, com.a.b.f.c.n.a(i)))
                i = cVarB.i() + i
            }
            lVar3.a(iD_, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.s, zVarA, (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a))
            lVar3.b_()
            Boolean zD = xVar.d()
            Int iA = zD ? xVar.a(-4) : 0
            xVar.a(new com.a.b.f.b.a(xVar.a(-1), lVar3, com.a.b.h.j.a(iA), iA), com.a.b.h.j.f673a)
            if (zD) {
                com.a.b.f.b.r rVarG = xVar.g()
                if (xVar.e()) {
                    com.a.b.f.b.ab abVar = new com.a.b.f.b.ab(com.a.b.f.b.y.q, zVarA, com.a.b.f.b.t.f519a, com.a.b.f.d.b.f565a, xVar.f225a.f())
                    com.a.b.f.b.l lVar4 = new com.a.b.f.b.l(1)
                    lVar4.a(0, (com.a.b.f.b.i) abVar)
                    lVar2 = lVar4
                } else {
                    com.a.b.f.b.l lVar5 = new com.a.b.f.b.l(2)
                    lVar5.a(0, (com.a.b.f.b.i) new com.a.b.f.b.p(com.a.b.f.b.y.l, zVarA, rVarG, com.a.b.f.b.t.f519a, com.a.b.f.c.n.f547b))
                    lVar5.a(1, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.s, zVarA, (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a))
                    lVar2 = lVar5
                }
                Int iA2 = xVar.a(-5)
                lVar2.b_()
                xVar.a(new com.a.b.f.b.a(iA, lVar2, com.a.b.h.j.a(iA2), iA2), com.a.b.h.j.f673a)
                com.a.b.f.b.l lVar6 = new com.a.b.f.b.l(xVar.e() ? 2 : 1)
                if (xVar.e()) {
                    lVar6.a(0, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.e(rVarG), zVarA, rVarG, com.a.b.f.b.t.f519a))
                }
                lVar6.a(xVar.e() ? 1 : 0, (com.a.b.f.b.i) new com.a.b.f.b.ac(com.a.b.f.b.y.bj, zVarA, com.a.b.f.b.t.a(rVarG), com.a.b.f.d.b.f565a))
                lVar6.b_()
                xVar.a(new com.a.b.f.b.a(iA2, lVar6, com.a.b.h.j.a(0), 0), com.a.b.h.j.f673a)
            }
            xVar.g[0].a(xVar.f225a.g().b())
            xVar.g[0].b()
            while (true) {
                Int iD = com.gmail.heagoo.a.c.a.d(iArrS, 0)
                if (iD < 0) {
                    break
                }
                com.gmail.heagoo.a.c.a.c(iArrS, iD)
                try {
                    xVar.a(xVar.f226b.a(iD), xVar.g[iD], iArrS)
                } catch (ah e) {
                    e.a("...while working on block " + com.gmail.heagoo.a.c.a.v(iD))
                    throw e
                }
            }
            com.a.b.f.b.w wVarN = xVar.e.n()
            if (wVarN != null) {
                com.a.b.f.b.z zVarO = xVar.e.o()
                Int iA3 = xVar.a(-2)
                if (xVar.d()) {
                    com.a.b.f.b.l lVar7 = new com.a.b.f.b.l(1)
                    lVar7.a(0, (com.a.b.f.b.i) new com.a.b.f.b.ac(com.a.b.f.b.y.bk, zVarO, com.a.b.f.b.t.a(xVar.g()), com.a.b.f.d.b.f565a))
                    lVar7.b_()
                    Int iA4 = xVar.a(-3)
                    xVar.a(new com.a.b.f.b.a(iA3, lVar7, com.a.b.h.j.a(iA4), iA4), com.a.b.h.j.f673a)
                    iA3 = iA4
                }
                com.a.b.f.b.l lVar8 = new com.a.b.f.b.l(1)
                com.a.b.f.d.e eVarC = wVarN.c()
                lVar8.a(0, (com.a.b.f.b.i) new com.a.b.f.b.q(wVarN, zVarO, (com.a.b.f.b.r) null, eVarC.d_() == 0 ? com.a.b.f.b.t.f519a : com.a.b.f.b.t.a(com.a.b.f.b.r.a(0, eVarC.a(0)))))
                lVar8.b_()
                xVar.a(new com.a.b.f.b.a(iA3, lVar8, com.a.b.h.j.f673a, -1), com.a.b.h.j.f673a)
            }
            if (xVar.k) {
                com.a.b.f.b.z zVarA2 = xVar.f225a.a(0)
                com.a.b.f.b.r rVarA2 = com.a.b.f.b.r.a(0, com.a.b.f.d.c.q)
                com.a.b.f.b.l lVar9 = new com.a.b.f.b.l(2)
                lVar9.a(0, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.c(com.a.b.f.d.c.q), zVarA2, rVarA2, com.a.b.f.b.t.f519a))
                lVar9.a(1, (com.a.b.f.b.i) new com.a.b.f.b.ac(com.a.b.f.b.y.bk, zVarA2, com.a.b.f.b.t.a(xVar.g()), com.a.b.f.d.b.f565a))
                lVar9.b_()
                Int iA5 = xVar.a(-7)
                xVar.a(new com.a.b.f.b.a(xVar.a(-6), lVar9, com.a.b.h.j.a(iA5), iA5), com.a.b.h.j.f673a)
                com.a.b.f.b.l lVar10 = new com.a.b.f.b.l(1)
                lVar10.a(0, (com.a.b.f.b.i) new com.a.b.f.b.ac(com.a.b.f.b.y.bi, zVarA2, com.a.b.f.b.t.a(rVarA2), com.a.b.f.d.b.f565a))
                lVar10.b_()
                xVar.a(new com.a.b.f.b.a(iA5, lVar10, com.a.b.h.j.f673a, -1), com.a.b.h.j.f673a)
            }
            xVar.i()
            if (xVar.m) {
                xVar.j()
            }
            return xVar.h()
        } catch (ah e2) {
            e2.a("...while working on method " + lVar.a().d())
            throw e2
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(Int i, Int i2, ae aeVar, n nVar, Array<Int> iArr) {
        n nVar2 = this.g[i]
        if (nVar2 != null) {
            n nVarA = aeVar != null ? nVar2.a(nVar, aeVar.a(), i2) : nVar2.a(nVar)
            if (nVarA == nVar2) {
                return
            } else {
                this.g[i] = nVarA
            }
        } else if (aeVar != null) {
            this.g[i] = nVar.b(i, i2)
        } else {
            this.g[i] = nVar
        }
        com.gmail.heagoo.a.c.a.b(iArr, i)
    }

    private fun a(Int i, com.a.b.f.b.b bVar) {
        a(c(i), bVar, BitSet(this.d))
    }

    private fun a(c cVar, n nVar, Array<Int> iArr) {
        Int iB
        com.a.b.h.j jVar
        Int i
        com.a.b.h.j jVarA
        Int iB2
        com.a.b.h.j jVar2
        com.a.b.h.j jVarA2
        e eVarE = cVar.e()
        this.e.a(eVarE.a_())
        n nVarA = nVar.a()
        this.f.a(cVar, nVarA)
        nVarA.b()
        Int iS = this.e.s()
        ArrayList arrayListM = this.e.m()
        Int size = arrayListM.size()
        Int iD_ = eVarE.d_()
        com.a.b.h.j jVarD = cVar.d()
        ae aeVar = null
        if (this.e.u()) {
            iB = 1
            Int iB3 = jVarD.b(1)
            if (this.l[iB3] == null) {
                this.l[iB3] = ae(this, iB3)
            }
            this.l[iB3].b(cVar.a())
            aeVar = this.l[iB3]
            jVar = jVarD
        } else if (this.e.v()) {
            Int iG = this.e.w().g()
            if (this.l[iG] == null) {
                this.l[iG] = ae(this, iG, cVar.a())
            } else {
                this.l[iG].a(cVar.a())
            }
            com.a.b.h.j jVarB = this.l[iG].b()
            this.l[iG].a(nVarA, iArr)
            iB = jVarB.b()
            jVar = jVarB
        } else if (this.e.p()) {
            iB = iD_
            jVar = jVarD
        } else {
            iB = 0
            jVar = jVarD
        }
        Int iB4 = jVar.b()
        for (Int i2 = iB; i2 < iB4; i2++) {
            Int iB5 = jVar.b(i2)
            try {
                a(iB5, cVar.a(), aeVar, nVarA, iArr)
            } catch (ah e) {
                e.a("...while merging to block " + com.gmail.heagoo.a.c.a.v(iB5))
                throw e
            }
        }
        if (iB4 == 0 && this.e.q()) {
            i = 1
            jVarA = com.a.b.h.j.a(a(-2))
        } else {
            i = iB4
            jVarA = jVar
        }
        if (i == 0) {
            iB2 = -1
        } else {
            Int iR = this.e.r()
            iB2 = iR >= 0 ? jVarA.b(iR) : iR
        }
        Boolean z = d() && this.e.t()
        if (z || iD_ != 0) {
            com.a.b.h.j jVar3 = new com.a.b.h.j(i)
            Boolean z2 = false
            Int i3 = 0
            while (i3 < iD_) {
                f fVarA = eVarE.a(i3)
                com.a.b.f.c.z zVarD = fVarA.d()
                Int iC = fVarA.c()
                Boolean z3 = z2 | (zVarD == com.a.b.f.c.z.f561a)
                try {
                    a(iC, cVar.a(), null, nVarA.a(zVarD), iArr)
                    aa aaVar = this.j[iC]
                    if (aaVar == null) {
                        aaVar = aa(this, (Byte) 0)
                        this.j[iC] = aaVar
                    }
                    jVar3.c(aaVar.a(zVarD.i()).b())
                    i3++
                    z2 = z3
                } catch (ah e2) {
                    e2.a("...while merging exception to block " + com.gmail.heagoo.a.c.a.v(iC))
                    throw e2
                }
            }
            if (z && !z2) {
                jVar3.c(a(-6))
                this.k = true
                for (Int i4 = (size - iS) - 1; i4 < size; i4++) {
                    com.a.b.f.b.i iVar = (com.a.b.f.b.i) arrayListM.get(i4)
                    if (iVar.k()) {
                        arrayListM.set(i4, iVar.a(com.a.b.f.d.c.n))
                    }
                }
            }
            if (iB2 >= 0) {
                jVar3.c(iB2)
            }
            jVar3.b_()
            jVar2 = jVar3
        } else {
            jVar2 = jVarA
        }
        Int iF = jVar2.f(iB2)
        Int i5 = iB2
        com.a.b.h.j jVarF = jVar2
        Int i6 = iS
        Int i7 = size
        while (i6 > 0) {
            Int i8 = i7 - 1
            com.a.b.f.b.i iVar2 = (com.a.b.f.b.i) arrayListM.get(i8)
            Boolean z4 = iVar2.f().d() == 1
            com.a.b.f.b.l lVar = new com.a.b.f.b.l(z4 ? 2 : 1)
            lVar.a(0, iVar2)
            if (z4) {
                lVar.a(1, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.s, iVar2.g(), (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a))
                jVarA2 = com.a.b.h.j.a(i5)
            } else {
                jVarA2 = jVarF
            }
            lVar.b_()
            Int iC2 = c()
            a(new com.a.b.f.b.a(iC2, lVar, jVarA2, i5), nVarA.e())
            jVarF = jVarF.f()
            jVarF.b(iF, iC2)
            jVarF.b_()
            i5 = iC2
            i6--
            i7 = i8
        }
        com.a.b.f.b.i iVar3 = i7 == 0 ? null : (com.a.b.f.b.i) arrayListM.get(i7 - 1)
        if (iVar3 == null || iVar3.f().d() == 1) {
            arrayListM.add(new com.a.b.f.b.q(com.a.b.f.b.y.s, iVar3 == null ? com.a.b.f.b.z.f530a : iVar3.g(), (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a))
            i7++
        }
        com.a.b.f.b.l lVar2 = new com.a.b.f.b.l(i7)
        for (Int i9 = 0; i9 < i7; i9++) {
            lVar2.a(i9, (com.a.b.f.b.i) arrayListM.get(i9))
        }
        lVar2.b_()
        b(new com.a.b.f.b.a(cVar.a(), lVar2, jVarF, i5), nVarA.e())
    }

    private fun a(com.a.b.f.b.a aVar, com.a.b.f.b.b bVar, BitSet bitSet) {
        Int iB
        bVar.a(aVar)
        bitSet.set(aVar.a())
        com.a.b.h.j jVarC = aVar.c()
        Int iB2 = jVarC.b()
        for (Int i = 0; i < iB2; i++) {
            Int iB3 = jVarC.b(i)
            if (!bitSet.get(iB3) && ((!a(aVar) || i <= 0) && (iB = b(iB3)) >= 0)) {
                a((com.a.b.f.b.a) this.h.get(iB), bVar, bitSet)
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(com.a.b.f.b.a aVar, com.a.b.h.j jVar) {
        if (aVar == null) {
            throw NullPointerException("block == null")
        }
        this.h.add(aVar)
        jVar.m()
        this.i.add(jVar)
    }

    static /* synthetic */ Boolean a(x xVar, com.a.b.f.b.a aVar, com.a.b.h.j jVar) {
        Boolean z
        if (aVar == null) {
            throw NullPointerException("block == null")
        }
        Int iB = xVar.b(aVar.a())
        if (iB < 0) {
            z = false
        } else {
            xVar.h.remove(iB)
            xVar.i.remove(iB)
            z = true
        }
        xVar.h.add(aVar)
        jVar.m()
        xVar.i.add(jVar)
        return z
    }

    static /* synthetic */ Boolean a(x xVar, Boolean z) {
        xVar.m = true
        return true
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a(com.a.b.f.b.a aVar) {
        Int iB
        com.a.b.h.j jVarC = aVar.c()
        return jVarC.b() >= 2 && (iB = jVarC.b(1)) < this.l.length && this.l[iB] != null
    }

    private fun b() {
        return this.d + this.f225a.l().d_() + 7
    }

    private fun b(Int i) {
        Int size = this.h.size()
        for (Int i2 = 0; i2 < size; i2++) {
            if (((com.a.b.f.b.a) this.h.get(i2)).a() == i) {
                return i2
            }
        }
        return -1
    }

    static /* synthetic */ ae b(x xVar, Int i) {
        for (Int length = xVar.l.length - 1; length >= 0; length--) {
            if (xVar.l[length] != null) {
                ae aeVar = xVar.l[length]
                if (aeVar.f184b.get(i)) {
                    return aeVar
                }
            }
        }
        return null
    }

    private fun b(com.a.b.f.b.a aVar, com.a.b.h.j jVar) {
        Boolean z
        Int iB = b(aVar.a())
        if (iB < 0) {
            z = false
        } else {
            d(iB)
            z = true
        }
        this.h.add(aVar)
        jVar.m()
        this.i.add(jVar)
        return z
    }

    private fun c() {
        Int iB = b()
        Iterator it = this.h.iterator()
        while (true) {
            Int i = iB
            if (!it.hasNext()) {
                return i
            }
            Int iA = ((com.a.b.f.b.a) it.next()).a()
            iB = iA >= i ? iA + 1 : i
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.a.b.f.b.a c(Int i) {
        Int iB = b(i)
        if (iB < 0) {
            throw IllegalArgumentException("no such label " + com.gmail.heagoo.a.c.a.v(i))
        }
        return (com.a.b.f.b.a) this.h.get(iB)
    }

    private fun d(Int i) {
        Int iB = b()
        com.a.b.h.j jVarC = ((com.a.b.f.b.a) this.h.get(i)).c()
        Int iB2 = jVarC.b()
        this.h.remove(i)
        this.i.remove(i)
        for (Int i2 = 0; i2 < iB2; i2++) {
            Int iB3 = jVarC.b(i2)
            if (iB3 >= iB) {
                Int iB4 = b(iB3)
                if (iB4 < 0) {
                    throw RuntimeException("Invalid label " + com.gmail.heagoo.a.c.a.v(iB3))
                }
                d(iB4)
            }
        }
    }

    private fun d() {
        return (this.f225a.d() & 32) != 0
    }

    private fun e() {
        return (this.f225a.d() & 8) != 0
    }

    private fun f() {
        return this.c + this.f225a.i()
    }

    private com.a.b.f.b.r g() {
        Int iF = f()
        if (iF <= 0) {
            iF = 1
        }
        return com.a.b.f.b.r.a(iF, com.a.b.f.d.c.n)
    }

    private com.a.b.f.b.x h() {
        Int size = this.h.size()
        com.a.b.f.b.c cVar = new com.a.b.f.b.c(size)
        for (Int i = 0; i < size; i++) {
            cVar.a(i, (com.a.b.f.b.a) this.h.get(i))
        }
        cVar.b_()
        return new com.a.b.f.b.x(cVar, a(-1))
    }

    private fun i() {
        Int length = this.j.length
        for (Int i = 0; i < length; i++) {
            aa aaVar = this.j[i]
            if (aaVar != null) {
                for (ab abVar : aaVar.a()) {
                    com.a.b.f.b.z zVarG = c(i).f().g()
                    com.a.b.f.b.l lVar = new com.a.b.f.b.l(2)
                    lVar.a(0, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.c(abVar.a()), zVarG, com.a.b.f.b.r.a(this.c, abVar.a()), com.a.b.f.b.t.f519a))
                    lVar.a(1, (com.a.b.f.b.i) new com.a.b.f.b.q(com.a.b.f.b.y.s, zVarG, (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a))
                    lVar.b_()
                    a(new com.a.b.f.b.a(abVar.b(), lVar, com.a.b.h.j.a(i), i), this.g[i].e())
                }
            }
        }
    }

    private fun j() {
        com.a.b.h.j jVar = new com.a.b.h.j(4)
        a(0, y(this, jVar))
        Int iC = c()
        ArrayList arrayList = ArrayList(iC)
        for (Int i = 0; i < iC; i++) {
            arrayList.add(null)
        }
        for (Int i2 = 0; i2 < this.h.size(); i2++) {
            com.a.b.f.b.a aVar = (com.a.b.f.b.a) this.h.get(i2)
            if (aVar != null) {
                arrayList.set(aVar.a(), (com.a.b.h.j) this.i.get(i2))
            }
        }
        Int iB = jVar.b()
        for (Int i3 = 0; i3 < iB; i3++) {
            af(this, ad(c()), arrayList).a(c(jVar.b(i3)))
        }
        k()
    }

    private fun k() {
        com.a.b.h.j jVar = new com.a.b.h.j(this.h.size())
        this.i.clear()
        a(a(-1), z(this, jVar))
        jVar.g()
        for (Int size = this.h.size() - 1; size >= 0; size--) {
            if (jVar.f(((com.a.b.f.b.a) this.h.get(size)).a()) < 0) {
                this.h.remove(size)
            }
        }
    }

    final Int a() {
        Int iF = f()
        return d() ? iF + 1 : iF
    }
}

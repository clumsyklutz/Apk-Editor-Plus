package com.a.b.g

import java.util.ArrayList
import java.util.BitSet
import java.util.Collections
import java.util.List

class ai {

    /* renamed from: b, reason: collision with root package name */
    private BitSet f604b
    private BitSet c
    private Int f
    private an g
    private Int h
    private com.a.b.h.k m
    private com.a.b.h.k n
    private Int e = -1
    private Int j = 0
    private Int k = 0
    private Int l = -1

    /* renamed from: a, reason: collision with root package name */
    private ArrayList f603a = ArrayList()
    private com.a.b.h.j d = new com.a.b.h.j()
    private val i = ArrayList()

    static {
        aj()
    }

    constructor(Int i, Int i2, an anVar) {
        this.g = anVar
        this.h = i
        this.f = i2
        this.f604b = BitSet(anVar.j().size())
        this.c = BitSet(anVar.j().size())
    }

    fun a(com.a.b.f.b.x xVar, Int i, an anVar) {
        com.a.b.f.b.c cVarA = xVar.a()
        com.a.b.f.b.a aVarA = cVarA.a(i)
        ai aiVar = ai(i, aVarA.a(), anVar)
        com.a.b.f.b.l lVarB = aVarA.b()
        aiVar.f603a.ensureCapacity(lVarB.d_())
        Int iD_ = lVarB.d_()
        for (Int i2 = 0; i2 < iD_; i2++) {
            aiVar.f603a.add(z(lVarB.a(i2), aiVar))
        }
        aiVar.f604b = an.a(cVarA, xVar.a(aVarA.a()))
        aiVar.c = an.a(cVarA, aVarA.c())
        com.a.b.h.j jVarC = aVarA.c()
        com.a.b.h.j jVar = new com.a.b.h.j(jVarC.b())
        Int iB = jVarC.b()
        for (Int i3 = 0; i3 < iB; i3++) {
            jVar.c(cVarA.c(jVarC.b(i3)))
        }
        aiVar.d = jVar
        if (aiVar.d.b() != 0) {
            Int iD = aVarA.d()
            aiVar.e = iD < 0 ? -1 : cVarA.c(iD)
        }
        return aiVar
    }

    private fun a(BitSet bitSet, com.a.b.f.b.r rVar) {
        bitSet.set(rVar.g())
        if (rVar.k() > 1) {
            bitSet.set(rVar.g() + 1)
        }
    }

    private fun a(List list) {
        Int size
        al alVar
        Int i
        BitSet bitSet = BitSet(this.g.g())
        BitSet bitSet2 = BitSet(this.g.g())
        Int size2 = list.size()
        Int i2 = 0
        while (i2 < size2) {
            for (Int i3 = i2; i3 < size2; i3++) {
                a(bitSet, ((al) list.get(i3)).a().b(0))
                a(bitSet2, ((al) list.get(i3)).n())
            }
            Int i4 = i2
            Int i5 = i2
            while (i4 < size2) {
                if (b(bitSet, ((al) list.get(i4)).n())) {
                    i = i5
                } else {
                    i = i5 + 1
                    Collections.swap(list, i4, i5)
                }
                i4++
                i5 = i
            }
            if (i2 == i5) {
                Int i6 = i5
                while (true) {
                    if (i6 >= size2) {
                        alVar = null
                        break
                    }
                    alVar = (al) list.get(i6)
                    if (b(bitSet, alVar.n()) && b(bitSet2, alVar.a().b(0))) {
                        Collections.swap(list, i5, i6)
                        break
                    }
                    i6++
                }
                com.a.b.f.b.r rVarN = alVar.n()
                com.a.b.f.b.r rVarA = rVarN.a(this.g.b(rVarN.k()))
                z zVar = z(new com.a.b.f.b.q(com.a.b.f.b.y.a(rVarN.a()), com.a.b.f.b.z.f530a, rVarA, alVar.a()), this)
                Int i7 = i5 + 1
                list.add(i5, zVar)
                list.set(i7, z(new com.a.b.f.b.q(com.a.b.f.b.y.a(rVarN.a()), com.a.b.f.b.z.f530a, rVarN, com.a.b.f.b.t.a(rVarA)), this))
                size = list.size()
                i5 = i7
            } else {
                size = size2
            }
            bitSet.clear()
            bitSet2.clear()
            i2 = i5
            size2 = size
        }
    }

    private fun b(BitSet bitSet, com.a.b.f.b.r rVar) {
        Int iG = rVar.g()
        return bitSet.get(iG) || (rVar.k() == 2 && bitSet.get(iG + 1))
    }

    private fun t() {
        Int size = this.f603a.size()
        Int i = 0
        while (i < size && (((al) this.f603a.get(i)) instanceof ac)) {
            i++
        }
        return i
    }

    public final ArrayList a() {
        return this.i
    }

    public final Unit a(Int i) {
        this.f603a.add(0, ac(i, this))
    }

    public final Unit a(Int i, Int i2) {
        if (i == i2) {
            return
        }
        this.c.set(i2)
        if (this.e == i) {
            this.e = i2
        }
        for (Int iB = this.d.b() - 1; iB >= 0; iB--) {
            if (this.d.b(iB) == i) {
                this.d.b(iB, i2)
            }
        }
        this.c.clear(i)
        ((ai) this.g.j().get(i2)).f604b.set(this.h)
        ((ai) this.g.j().get(i)).f604b.clear(this.h)
    }

    public final Unit a(com.a.b.f.b.i iVar) {
        al alVarA = al.a(iVar, this)
        this.f603a.add(t(), alVarA)
        this.g.a(alVarA)
    }

    public final Unit a(com.a.b.f.b.r rVar) {
        this.f603a.add(0, ac(rVar, this))
    }

    public final Unit a(com.a.b.f.b.r rVar, com.a.b.f.b.r rVar2) {
        if (rVar.g() == rVar2.g()) {
            return
        }
        z zVar = (z) this.f603a.get(this.f603a.size() - 1)
        if (zVar.n() == null && zVar.a().d_() <= 0) {
            this.f603a.add(this.f603a.size() - 1, z(new com.a.b.f.b.q(com.a.b.f.b.y.a(rVar.a()), com.a.b.f.b.z.f530a, rVar, com.a.b.f.b.t.a(rVar2)), this))
            this.j++
            return
        }
        Int iNextSetBit = this.c.nextSetBit(0)
        while (iNextSetBit >= 0) {
            ai aiVar = (ai) this.g.j().get(iNextSetBit)
            if (rVar.g() != rVar2.g()) {
                aiVar.f603a.add(aiVar.t(), z(new com.a.b.f.b.q(com.a.b.f.b.y.a(rVar.a()), com.a.b.f.b.z.f530a, rVar, com.a.b.f.b.t.a(rVar2)), aiVar))
                aiVar.k++
            }
            iNextSetBit = this.c.nextSetBit(iNextSetBit + 1)
        }
    }

    public final Unit a(ae aeVar) {
        Int size = this.f603a.size()
        for (Int i = 0; i < size; i++) {
            al alVar = (al) this.f603a.get(i)
            if (!(alVar is ac)) {
                return
            }
            aeVar.a((ac) alVar)
        }
    }

    public final Unit a(ai aiVar) {
        this.i.add(aiVar)
    }

    public final Unit a(am amVar) {
        Int size = this.f603a.size()
        for (Int i = 0; i < size; i++) {
            ((al) this.f603a.get(i)).a(amVar)
        }
    }

    public final ai b(ai aiVar) {
        ai aiVarB = this.g.b()
        if (!this.c.get(aiVar.h)) {
            throw RuntimeException("Block " + com.gmail.heagoo.a.c.a.v(aiVar.f) + " not successor of " + com.gmail.heagoo.a.c.a.v(this.f))
        }
        aiVarB.f604b.set(this.h)
        aiVarB.c.set(aiVar.h)
        aiVarB.d.c(aiVar.h)
        aiVarB.e = aiVar.h
        for (Int iB = this.d.b() - 1; iB >= 0; iB--) {
            if (this.d.b(iB) == aiVar.h) {
                this.d.b(iB, aiVarB.h)
            }
        }
        if (this.e == aiVar.h) {
            this.e = aiVarB.h
        }
        this.c.clear(aiVar.h)
        this.c.set(aiVarB.h)
        aiVar.f604b.set(aiVarB.h)
        aiVar.f604b.set(this.h, this.c.get(aiVar.h))
        return aiVarB
    }

    public final Unit b() {
        this.f603a.subList(0, t()).clear()
    }

    public final Unit b(Int i) {
        Int i2 = 0
        for (Int iB = this.d.b() - 1; iB >= 0; iB--) {
            if (this.d.b(iB) == i) {
                i2 = iB
            } else {
                this.e = this.d.b(iB)
            }
        }
        this.d.d(i2)
        this.c.clear(i)
        ((ai) this.g.j().get(i)).f604b.clear(this.h)
    }

    public final Unit b(com.a.b.f.b.i iVar) {
        if (iVar.f().d() == 1) {
            throw IllegalArgumentException("last insn must branch")
        }
        al alVar = (al) this.f603a.get(this.f603a.size() - 1)
        al alVarA = al.a(iVar, this)
        this.f603a.set(this.f603a.size() - 1, alVarA)
        this.g.b(alVar)
        this.g.a(alVarA)
    }

    public final ArrayList c() {
        return this.f603a
    }

    public final Unit c(Int i) {
        if (this.n == null) {
            this.n = e.b(this.g.g())
        }
        this.n.a(i)
    }

    public final Unit c(ai aiVar) {
        if (this != aiVar && this.d.b() == 0) {
            this.c.set(aiVar.h)
            this.d.c(aiVar.h)
            this.e = aiVar.h
            aiVar.f604b.set(this.h)
        }
    }

    public final List d() {
        return this.f603a.subList(0, t())
    }

    public final Unit d(Int i) {
        if (this.m == null) {
            this.m = e.b(this.g.g())
        }
        this.m.a(i)
    }

    public final Int e() {
        return this.h
    }

    public final Unit e(Int i) {
        this.l = i
    }

    public final Int f() {
        return this.f
    }

    public final BitSet g() {
        return this.f604b
    }

    public final BitSet h() {
        return this.c
    }

    public final com.a.b.h.j i() {
        return this.d
    }

    public final Int j() {
        return this.e
    }

    public final Int k() {
        return this.g.a(this.e)
    }

    public final ai l() {
        if (this.e < 0) {
            return null
        }
        return (ai) this.g.j().get(this.e)
    }

    public final com.a.b.h.j m() {
        com.a.b.h.j jVar = new com.a.b.h.j(this.d.b())
        Int iB = this.d.b()
        for (Int i = 0; i < iB; i++) {
            jVar.c(this.g.a(this.d.b(i)))
        }
        return jVar
    }

    public final an n() {
        return this.g
    }

    public final ai o() {
        ai aiVarB = this.g.b()
        aiVarB.f604b = this.f604b
        aiVarB.c.set(this.h)
        aiVarB.d.c(this.h)
        aiVarB.e = this.h
        this.f604b = BitSet(this.g.j().size())
        this.f604b.set(aiVarB.h)
        for (Int iNextSetBit = aiVarB.f604b.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = aiVarB.f604b.nextSetBit(iNextSetBit + 1)) {
            ((ai) this.g.j().get(iNextSetBit)).a(this.h, aiVarB.h)
        }
        return aiVarB
    }

    public final com.a.b.h.k p() {
        if (this.n == null) {
            this.n = e.b(this.g.g())
        }
        return this.n
    }

    public final Boolean q() {
        return this.h == this.g.e()
    }

    public final Boolean r() {
        if (this.l == -1) {
            this.g.l()
        }
        return this.l == 1
    }

    public final Unit s() {
        if (this.k > 1) {
            a(this.f603a.subList(0, this.k))
            if (((al) this.f603a.get(this.k)).i()) {
                throw RuntimeException("Unexpected: moves from phis before move-exception")
            }
        }
        if (this.j > 1) {
            a(this.f603a.subList((this.f603a.size() - this.j) - 1, this.f603a.size() - 1))
        }
        this.g.i()
    }

    public final String toString() {
        return "{" + this.h + ":" + com.gmail.heagoo.a.c.a.v(this.f) + '}'
    }
}

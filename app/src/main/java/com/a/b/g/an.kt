package com.a.b.g

import java.util.ArrayList
import java.util.BitSet
import java.util.Collections
import java.util.Iterator
import java.util.List
import java.util.Set
import java.util.Stack

class an {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList f607a

    /* renamed from: b, reason: collision with root package name */
    private Int f608b
    private Int c
    private Int d
    private Int e
    private Int f
    private Int g
    private final Int h
    private Array<al> i
    private Array<ArrayList> j
    private Array<List> k
    private Boolean l = false

    private constructor(com.a.b.f.b.x xVar, Int i, Boolean z) {
        this.h = i
        this.g = xVar.a().j()
        this.d = xVar.a().e()
        this.e = this.d
    }

    fun a(com.a.b.f.b.x xVar, Int i, Boolean z) {
        an anVar = an(xVar, i, z)
        Int iD_ = xVar.a().d_()
        anVar.f607a = ArrayList(iD_ + 2)
        for (Int i2 = 0; i2 < iD_; i2++) {
            anVar.f607a.add(ai.a(xVar, i2, anVar))
        }
        anVar.f608b = ((ai) anVar.f607a.get(xVar.a().c(xVar.b()))).o().e()
        anVar.c = -1
        return anVar
    }

    static BitSet a(com.a.b.f.b.c cVar, com.a.b.h.j jVar) {
        BitSet bitSet = BitSet(cVar.d_())
        Int iB = jVar.b()
        for (Int i = 0; i < iB; i++) {
            bitSet.set(cVar.c(jVar.b(i)))
        }
        return bitSet
    }

    private fun b(al alVar, com.a.b.f.b.t tVar) {
        if (tVar == null) {
            return
        }
        Int iD_ = tVar.d_()
        for (Int i = 0; i < iD_; i++) {
            if (!this.j[tVar.b(i).g()].remove(alVar)) {
                throw RuntimeException("use not found")
            }
        }
    }

    private fun q() {
        if (this.l) {
            throw RuntimeException("No use list in back mode")
        }
        this.j = new ArrayList[this.d]
        for (Int i = 0; i < this.d; i++) {
            this.j[i] = ArrayList()
        }
        a(ap(this))
        this.k = new List[this.d]
        for (Int i2 = 0; i2 < this.d; i2++) {
            this.k[i2] = Collections.unmodifiableList(this.j[i2])
        }
    }

    public final Int a(Int i) {
        if (i < 0) {
            return -1
        }
        return ((ai) this.f607a.get(i)).f()
    }

    final Unit a() {
        if (this.c >= 0) {
            throw RuntimeException("must be called at most once")
        }
        this.c = this.f607a.size()
        Int i = this.c
        Int i2 = this.g
        this.g = i2 + 1
        ai aiVar = ai(i, i2, this)
        this.f607a.add(aiVar)
        Iterator it = this.f607a.iterator()
        while (it.hasNext()) {
            ((ai) it.next()).c(aiVar)
        }
        if (aiVar.g().cardinality() == 0) {
            this.f607a.remove(this.c)
            this.c = -1
            this.g--
        }
    }

    public final Unit a(ag agVar) {
        Iterator it = this.f607a.iterator()
        while (it.hasNext()) {
            Iterator it2 = ((ai) it.next()).c().iterator()
            while (it2.hasNext()) {
                ((al) it2.next()).b(agVar)
            }
        }
        this.d = agVar.a()
        this.e = this.d
    }

    public final Unit a(ak akVar) {
        BitSet bitSet = BitSet(this.f607a.size())
        Stack stack = Stack()
        stack.add(d())
        while (stack.size() > 0) {
            ai aiVar = (ai) stack.pop()
            ArrayList arrayListA = aiVar.a()
            if (!bitSet.get(aiVar.e())) {
                for (Int size = arrayListA.size() - 1; size >= 0; size--) {
                    stack.add((ai) arrayListA.get(size))
                }
                bitSet.set(aiVar.e())
                akVar.a(aiVar, null)
            }
        }
    }

    final Unit a(al alVar) {
        a(alVar, (com.a.b.f.b.t) null)
        a(alVar, (com.a.b.f.b.r) null)
    }

    final Unit a(al alVar, com.a.b.f.b.r rVar) {
        if (this.i == null) {
            return
        }
        if (rVar != null) {
            this.i[rVar.g()] = null
        }
        com.a.b.f.b.r rVarN = alVar.n()
        if (rVarN != null) {
            if (this.i[rVarN.g()] != null) {
                throw RuntimeException("Duplicate add of insn")
            }
            this.i[rVarN.g()] = alVar
        }
    }

    final Unit a(al alVar, com.a.b.f.b.r rVar, com.a.b.f.b.r rVar2) {
        if (this.j == null) {
            return
        }
        if (rVar != null) {
            this.j[rVar.g()].remove(alVar)
        }
        Int iG = rVar2.g()
        if (this.j.length <= iG) {
            this.j = null
        } else {
            this.j[iG].add(alVar)
        }
    }

    final Unit a(al alVar, com.a.b.f.b.t tVar) {
        if (this.j == null) {
            return
        }
        if (tVar != null) {
            b(alVar, tVar)
        }
        com.a.b.f.b.t tVarA = alVar.a()
        Int iD_ = tVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            this.j[tVarA.b(i).g()].add(alVar)
        }
    }

    public final Unit a(am amVar) {
        Iterator it = this.f607a.iterator()
        while (it.hasNext()) {
            ((ai) it.next()).a(amVar)
        }
    }

    public final Unit a(Set set) {
        Iterator it = this.f607a.iterator()
        while (it.hasNext()) {
            ai aiVar = (ai) it.next()
            ArrayList arrayListC = aiVar.c()
            for (Int size = arrayListC.size() - 1; size >= 0; size--) {
                al alVar = (al) arrayListC.get(size)
                if (set.contains(alVar)) {
                    b(alVar)
                    arrayListC.remove(size)
                }
            }
            Int size2 = arrayListC.size()
            al alVar2 = size2 == 0 ? null : (al) arrayListC.get(size2 - 1)
            if (aiVar != f() && (size2 == 0 || alVar2.e() == null || alVar2.e().f().d() == 1)) {
                arrayListC.add(al.a(new com.a.b.f.b.q(com.a.b.f.b.y.s, com.a.b.f.b.z.f530a, (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a), aiVar))
                BitSet bitSetH = aiVar.h()
                for (Int iNextSetBit = bitSetH.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetH.nextSetBit(iNextSetBit + 1)) {
                    if (iNextSetBit != aiVar.j()) {
                        aiVar.b(iNextSetBit)
                    }
                }
            }
        }
    }

    public final Unit a(Boolean z, ak akVar) {
        BitSet bitSet = BitSet(this.f607a.size())
        Stack stack = Stack()
        ai aiVarF = z ? f() : d()
        if (aiVarF == null) {
            return
        }
        stack.add(null)
        stack.add(aiVarF)
        while (stack.size() > 0) {
            ai aiVar = (ai) stack.pop()
            ai aiVar2 = (ai) stack.pop()
            if (!bitSet.get(aiVar.e())) {
                BitSet bitSetG = z ? aiVar.g() : aiVar.h()
                for (Int iNextSetBit = bitSetG.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetG.nextSetBit(iNextSetBit + 1)) {
                    stack.add(aiVar)
                    stack.add(this.f607a.get(iNextSetBit))
                }
                bitSet.set(aiVar.e())
                akVar.a(aiVar, aiVar2)
            }
        }
    }

    public final Boolean a(com.a.b.f.b.r rVar) {
        al alVarC = c(rVar.g())
        if (alVarC == null) {
            return false
        }
        if (alVarC.f() != null) {
            return true
        }
        Iterator it = d(rVar.g()).iterator()
        while (it.hasNext()) {
            com.a.b.f.b.i iVarE = ((al) it.next()).e()
            if (iVarE != null && iVarE.f().a() == 54) {
                return true
            }
        }
        return false
    }

    public final Int b(Int i) {
        Int i2 = this.e + this.f
        this.f += i
        this.d = Math.max(this.d, i2 + i)
        return i2
    }

    public final ai b() {
        Int size = this.f607a.size()
        Int i = this.g
        this.g = i + 1
        ai aiVar = ai(size, i, this)
        aiVar.c().add(z(new com.a.b.f.b.q(com.a.b.f.b.y.s, com.a.b.f.b.z.f530a, (com.a.b.f.b.r) null, com.a.b.f.b.t.f519a), aiVar))
        this.f607a.add(aiVar)
        return aiVar
    }

    final Unit b(al alVar) {
        if (this.j != null) {
            b(alVar, alVar.a())
        }
        com.a.b.f.b.r rVarN = alVar.n()
        if (this.i == null || rVarN == null) {
            return
        }
        this.i[rVarN.g()] = null
    }

    public final Int c() {
        return this.f608b
    }

    public final al c(Int i) {
        if (this.l) {
            throw RuntimeException("No def list in back mode")
        }
        if (this.i != null) {
            return this.i[i]
        }
        this.i = new al[this.d]
        a(ao(this))
        return this.i[i]
    }

    public final ai d() {
        return (ai) this.f607a.get(this.f608b)
    }

    public final List d(Int i) {
        if (this.k == null) {
            q()
        }
        return this.k[i]
    }

    public final Int e() {
        return this.c
    }

    final Unit e(Int i) {
        this.d = i
        this.e = this.d
        m()
    }

    public final ai f() {
        if (this.c < 0) {
            return null
        }
        return (ai) this.f607a.get(this.c)
    }

    public final Int g() {
        return this.d
    }

    public final Int h() {
        return this.h
    }

    public final Unit i() {
        this.f = 0
    }

    public final ArrayList j() {
        return this.f607a
    }

    public final Int k() {
        Int i = 0
        Iterator it = this.f607a.iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                return i2
            }
            i = ((ai) it.next()).r() ? i2 + 1 : i2
        }
    }

    public final Unit l() {
        Iterator it = this.f607a.iterator()
        while (it.hasNext()) {
            ((ai) it.next()).e(0)
        }
        ArrayList arrayList = ArrayList()
        arrayList.add(d())
        while (!arrayList.isEmpty()) {
            ai aiVar = (ai) arrayList.remove(0)
            if (!aiVar.r()) {
                aiVar.e(1)
                BitSet bitSetH = aiVar.h()
                for (Int iNextSetBit = bitSetH.nextSetBit(0); iNextSetBit >= 0; iNextSetBit = bitSetH.nextSetBit(iNextSetBit + 1)) {
                    arrayList.add(this.f607a.get(iNextSetBit))
                }
            }
        }
    }

    public final Unit m() {
        this.i = null
        this.j = null
        this.k = null
    }

    public final Array<ArrayList> n() {
        if (this.j == null) {
            q()
        }
        Array<ArrayList> arrayListArr = new ArrayList[this.d]
        for (Int i = 0; i < this.d; i++) {
            arrayListArr[i] = ArrayList(this.j[i])
        }
        return arrayListArr
    }

    public final Int o() {
        Int i = this.d
        this.d = i + 1
        this.e = this.d
        m()
        return i
    }

    public final Unit p() {
        this.l = true
        this.j = null
        this.i = null
    }
}

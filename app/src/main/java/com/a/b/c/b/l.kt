package com.a.b.c.b

import java.util.BitSet

abstract class l {

    /* renamed from: a, reason: collision with root package name */
    private Int f326a

    /* renamed from: b, reason: collision with root package name */
    private final n f327b
    private final com.a.b.f.b.z c
    private final com.a.b.f.b.t d

    constructor(n nVar, com.a.b.f.b.z zVar, com.a.b.f.b.t tVar) {
        if (nVar == null) {
            throw NullPointerException("opcode == null")
        }
        if (zVar == null) {
            throw NullPointerException("position == null")
        }
        if (tVar == null) {
            throw NullPointerException("registers == null")
        }
        this.f326a = -1
        this.f327b = nVar
        this.c = zVar
        this.d = tVar
    }

    fun a(com.a.b.f.b.z zVar, com.a.b.f.b.r rVar, com.a.b.f.b.r rVar2) {
        Boolean z = rVar.k() == 1
        Boolean zN = rVar.a().n()
        Int iG = rVar.g()
        return ai((rVar2.g() | iG) < 16 ? zN ? o.i : z ? o.c : o.f : iG < 256 ? zN ? o.j : z ? o.d : o.g : zN ? o.k : z ? o.e : o.h, zVar, com.a.b.f.b.t.a(rVar, rVar2))
    }

    public abstract Int a()

    public final Int a(BitSet bitSet) {
        Boolean zD = this.f327b.d()
        Int iD_ = this.d.d_()
        Int iK = (!zD || bitSet.get(0)) ? 0 : this.d.b(0).k()
        Int iK2 = 0
        for (Int i = zD ? 1 : 0; i < iD_; i++) {
            if (!bitSet.get(i)) {
                iK2 += this.d.b(i).k()
            }
        }
        return Math.max(iK2, iK)
    }

    public abstract l a(n nVar)

    public abstract l a(com.a.b.f.b.t tVar)

    fun a(com.a.b.g.ag agVar) {
        return a(agVar.a(this.d))
    }

    public final String a(String str, Int i, Boolean z) {
        String strA = a(z)
        if (strA == null) {
            return null
        }
        String str2 = str + l() + ": "
        Int length = str2.length()
        return com.a.b.h.t.a(str2, length, "", strA, i == 0 ? strA.length() : i - length)
    }

    protected abstract String a(Boolean z)

    public abstract Unit a(com.a.b.h.r rVar)

    public final l b(BitSet bitSet) {
        com.a.b.f.b.t tVar = this.d
        Boolean z = bitSet.get(0)
        if (this.f327b.d()) {
            bitSet.set(0)
        }
        com.a.b.f.b.t tVarA = tVar.a(bitSet)
        if (this.f327b.d()) {
            bitSet.set(0, z)
        }
        if (tVarA.d_() == 0) {
            return null
        }
        return q(this.c, tVarA)
    }

    protected abstract String b()

    public final l c(BitSet bitSet) {
        if (!this.f327b.d() || bitSet.get(0)) {
            return null
        }
        com.a.b.f.b.r rVarB = this.d.b(0)
        return a(this.c, rVarB, rVarB.a(0))
    }

    public final Unit c(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("address < 0")
        }
        this.f326a = i
    }

    public abstract l d(Int i)

    public final l d(BitSet bitSet) {
        return a(this.d.a(0, this.f327b.d(), bitSet))
    }

    public final Boolean f() {
        return this.f326a >= 0
    }

    public final Int g() {
        if (this.f326a < 0) {
            throw RuntimeException("address not yet known")
        }
        return this.f326a
    }

    public final n h() {
        return this.f327b
    }

    public final com.a.b.f.b.z i() {
        return this.c
    }

    public final com.a.b.f.b.t j() {
        return this.d
    }

    public final l k() {
        return a(this.d.a(0, this.f327b.d(), (BitSet) null))
    }

    public final String l() {
        return this.f326a != -1 ? String.format("%04x", Integer.valueOf(this.f326a)) : com.gmail.heagoo.a.c.a.t(System.identityHashCode(this))
    }

    public final Int m() {
        return g() + a()
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append(l())
        stringBuffer.append(' ')
        stringBuffer.append(this.c)
        stringBuffer.append(": ")
        stringBuffer.append(this.f327b.e())
        Boolean z = false
        if (this.d.d_() != 0) {
            stringBuffer.append(this.d.b(" ", ", ", null))
            z = true
        }
        String strB = b()
        if (strB != null) {
            if (z) {
                stringBuffer.append(',')
            }
            stringBuffer.append(' ')
            stringBuffer.append(strB)
        }
        return stringBuffer.toString()
    }
}

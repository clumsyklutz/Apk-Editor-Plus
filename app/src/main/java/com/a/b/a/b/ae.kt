package com.a.b.a.b

import java.util.BitSet

final class ae {

    /* renamed from: a, reason: collision with root package name */
    private BitSet f183a

    /* renamed from: b, reason: collision with root package name */
    private BitSet f184b
    private Int c
    private /* synthetic */ x d

    ae(x xVar, Int i) {
        this.d = xVar
        this.c = i
        this.f184b = BitSet(xVar.d)
        this.f183a = BitSet(xVar.d)
        x.a(xVar, true)
    }

    ae(x xVar, Int i, Int i2) {
        this(xVar, i)
        a(i2)
    }

    final Int a() {
        return this.c
    }

    final Unit a(Int i) {
        this.f184b.set(i)
    }

    final Unit a(n nVar, Array<Int> iArr) {
        Int iNextSetBit = this.f183a.nextSetBit(0)
        while (iNextSetBit >= 0) {
            Int iB = this.d.c(iNextSetBit).c().b(0)
            n nVarA = nVar.a(this.c, iNextSetBit)
            if (nVarA != null) {
                this.d.a(iB, -1, null, nVarA, iArr)
            } else {
                com.gmail.heagoo.a.c.a.b(iArr, iNextSetBit)
            }
            iNextSetBit = this.f183a.nextSetBit(iNextSetBit + 1)
        }
    }

    final com.a.b.h.j b() {
        com.a.b.h.j jVar = new com.a.b.h.j(this.f183a.size())
        Int iNextSetBit = this.f183a.nextSetBit(0)
        while (iNextSetBit >= 0) {
            jVar.c(this.d.c(iNextSetBit).c().b(0))
            iNextSetBit = this.f183a.nextSetBit(iNextSetBit + 1)
        }
        jVar.b_()
        return jVar
    }

    final Unit b(Int i) {
        this.f183a.set(i)
    }
}

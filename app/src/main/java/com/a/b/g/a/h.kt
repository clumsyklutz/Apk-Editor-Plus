package com.a.b.g.a

import com.a.b.f.b.x
import java.util.BitSet

class h {

    /* renamed from: a, reason: collision with root package name */
    private final x f577a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.b.c f578b
    private final com.a.b.f.b.c c

    constructor(x xVar) {
        this.f577a = xVar
        this.f578b = this.f577a.a()
        this.c = this.f578b.h()
    }

    private fun a(Int i, com.a.b.h.j jVar) {
        Int iB = jVar.b()
        for (Int i2 = 0; i2 < iB; i2++) {
            Int iB2 = jVar.b(i2)
            com.a.b.h.j jVarA = this.f577a.a(this.f578b.b(iB2).a())
            Int iB3 = jVarA.b()
            for (Int i3 = 0; i3 < iB3; i3++) {
                com.a.b.f.b.a aVarB = this.c.b(jVarA.b(i3))
                com.a.b.h.j jVarF = aVarB.c().f()
                jVarF.b(jVarF.f(iB2), i)
                Int iD = aVarB.d()
                if (iD == iB2) {
                    iD = i
                }
                jVarF.b_()
                this.c.a(this.c.c(aVarB.a()), new com.a.b.f.b.a(aVarB.a(), aVarB.b(), jVarF, iD))
            }
        }
    }

    public final x a() {
        Int iD_ = this.f578b.d_()
        BitSet bitSet = BitSet(this.f578b.j())
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.b.a aVarA = this.f578b.a(i)
            if (!bitSet.get(aVarA.a())) {
                com.a.b.h.j jVarA = this.f577a.a(aVarA.a())
                Int iB = jVarA.b()
                for (Int i2 = 0; i2 < iB; i2++) {
                    Int iB2 = jVarA.b(i2)
                    com.a.b.f.b.a aVarB = this.f578b.b(iB2)
                    if (!bitSet.get(iB2) && aVarB.c().b() <= 1 && aVarB.f().f().a() != 55) {
                        com.a.b.h.j jVar = new com.a.b.h.j()
                        for (Int i3 = i2 + 1; i3 < iB; i3++) {
                            Int iB3 = jVarA.b(i3)
                            com.a.b.f.b.a aVarB2 = this.f578b.b(iB3)
                            if (aVarB2.c().b() == 1 && aVarB.b().a(aVarB2.b())) {
                                jVar.c(iB3)
                                bitSet.set(iB3)
                            }
                        }
                        a(iB2, jVar)
                    }
                }
            }
        }
        for (Int i4 = iD_ - 1; i4 >= 0; i4--) {
            if (bitSet.get(this.c.a(i4).a())) {
                this.c.a(i4, (com.a.b.f.b.a) null)
            }
        }
        this.c.i()
        this.c.b_()
        return x(this.c, this.f577a.b())
    }
}

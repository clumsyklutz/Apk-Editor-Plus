package com.a.b.c.b.a

import java.util.BitSet

class m extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f282a = m()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 2
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return tVarJ.b(0).m() + ", " + tVarJ.b(1).m() + ", " + a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c())
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c(), 16)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, b(tVarJ.b(0).g(), tVarJ.b(1).g())), (Short) ((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c()).j())
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if (!(lVar is com.a.b.c.b.i) || tVarJ.d_() != 2 || !a(tVarJ.b(0).g()) || !a(tVarJ.b(1).g())) {
            return false
        }
        com.a.b.f.c.a aVarC = ((com.a.b.c.b.i) lVar).c()
        if (!(aVarC is com.a.b.f.c.s)) {
            return false
        }
        com.a.b.f.c.s sVar = (com.a.b.f.c.s) aVarC
        return sVar.i() && d(sVar.j())
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(2)
        bitSet.set(0, a(tVarJ.b(0).g()))
        bitSet.set(1, a(tVarJ.b(1).g()))
        return bitSet
    }
}

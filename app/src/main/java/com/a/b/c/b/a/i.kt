package com.a.b.c.b.a

import java.util.BitSet

class i extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f278a = i()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 2
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return lVar.j().b(0).m() + ", " + a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c())
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c(), 16)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        a(rVar, a(lVar, lVar.j().b(0).g()), (Short) ((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c()).j())
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if (!(lVar is com.a.b.c.b.i) || tVarJ.d_() != 1 || !c(tVarJ.b(0).g())) {
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
        BitSet bitSet = BitSet(1)
        bitSet.set(0, c(tVarJ.b(0).g()))
        return bitSet
    }
}

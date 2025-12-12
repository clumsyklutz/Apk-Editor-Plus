package com.a.b.c.b.a

import java.util.BitSet

class x extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f293a = x()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 5
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return lVar.j().b(0).m() + ", " + a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c())
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c(), 64)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, tVarJ.b(0).g()), ((com.a.b.f.c.r) ((com.a.b.c.b.i) lVar).c()).k())
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if ((lVar is com.a.b.c.b.i) && tVarJ.d_() == 1 && c(tVarJ.b(0).g())) {
            return ((com.a.b.c.b.i) lVar).c() instanceof com.a.b.f.c.r
        }
        return false
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(1)
        bitSet.set(0, c(tVarJ.b(0).g()))
        return bitSet
    }
}

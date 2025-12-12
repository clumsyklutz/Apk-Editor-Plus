package com.a.b.c.b.a

import java.util.BitSet

class c extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f272a = c()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 1
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return lVar.j().b(0).m() + ", " + a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c())
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c(), 4)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        rVar.b(a(lVar, b(lVar.j().b(0).g(), ((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c()).j() & 15)))
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if (!(lVar is com.a.b.c.b.i) || tVarJ.d_() != 1 || !a(tVarJ.b(0).g())) {
            return false
        }
        com.a.b.f.c.a aVarC = ((com.a.b.c.b.i) lVar).c()
        if (!(aVarC is com.a.b.f.c.s)) {
            return false
        }
        com.a.b.f.c.s sVar = (com.a.b.f.c.s) aVarC
        if (sVar.i()) {
            Int iJ = sVar.j()
            if (iJ >= -8 && iJ <= 7) {
                return true
            }
        }
        return false
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(1)
        bitSet.set(0, a(tVarJ.b(0).g()))
        return bitSet
    }
}

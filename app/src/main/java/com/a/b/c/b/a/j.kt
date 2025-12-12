package com.a.b.c.b.a

import com.a.b.c.b.al
import java.util.BitSet

class j extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f279a = j()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 2
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return lVar.j().b(0).m() + ", " + d(lVar)
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return e(lVar)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        a(rVar, a(lVar, lVar.j().b(0).g()), (Short) ((al) lVar).e())
    }

    @Override // com.a.b.c.b.r
    public final Boolean a(al alVar) {
        Int iE = alVar.e()
        return iE != 0 && d(iE)
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if (!(lVar is al) || tVarJ.d_() != 1 || !c(tVarJ.b(0).g())) {
            return false
        }
        al alVar = (al) lVar
        if (alVar.n()) {
            return a(alVar)
        }
        return true
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(1)
        bitSet.set(0, c(tVarJ.b(0).g()))
        return bitSet
    }
}

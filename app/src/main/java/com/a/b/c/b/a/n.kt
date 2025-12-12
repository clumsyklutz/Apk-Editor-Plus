package com.a.b.c.b.a

import com.a.b.c.b.al
import java.util.BitSet

class n extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f283a = n()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 2
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return tVarJ.b(0).m() + ", " + tVarJ.b(1).m() + ", " + d(lVar)
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return e(lVar)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, b(tVarJ.b(0).g(), tVarJ.b(1).g())), (Short) ((al) lVar).e())
    }

    @Override // com.a.b.c.b.r
    public final Boolean a(al alVar) {
        Int iE = alVar.e()
        return iE != 0 && d(iE)
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if (!(lVar is al) || tVarJ.d_() != 2 || !a(tVarJ.b(0).g()) || !a(tVarJ.b(1).g())) {
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
        BitSet bitSet = BitSet(2)
        bitSet.set(0, a(tVarJ.b(0).g()))
        bitSet.set(1, a(tVarJ.b(1).g()))
        return bitSet
    }
}

package com.a.b.c.b.a

import com.a.b.c.b.al
import java.util.BitSet

class t extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f289a = t()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 3
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
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, tVarJ.b(0).g()), ((al) lVar).e())
    }

    @Override // com.a.b.c.b.r
    public final Boolean a(al alVar) {
        return true
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return (lVar is al) && tVarJ.d_() == 1 && c(tVarJ.b(0).g())
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(1)
        bitSet.set(0, c(tVarJ.b(0).g()))
        return bitSet
    }
}

package com.a.b.c.b.a

import com.a.b.c.b.ai
import java.util.BitSet

class u extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f290a = u()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 3
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return tVarJ.b(0).m() + ", " + tVarJ.b(1).m()
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, 0), (Short) tVarJ.b(0).g(), (Short) tVarJ.b(1).g())
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return (lVar is ai) && tVarJ.d_() == 2 && e(tVarJ.b(0).g()) && e(tVarJ.b(1).g())
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(2)
        bitSet.set(0, e(tVarJ.b(0).g()))
        bitSet.set(1, e(tVarJ.b(1).g()))
        return bitSet
    }
}

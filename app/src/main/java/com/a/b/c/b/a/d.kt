package com.a.b.c.b.a

import com.a.b.c.b.ai
import java.util.BitSet

class d extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f273a = d()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 1
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return lVar.j().b(0).m()
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        rVar.b(a(lVar, lVar.j().b(0).g()))
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return (lVar is ai) && tVarJ.d_() == 1 && c(tVarJ.b(0).g())
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(1)
        bitSet.set(0, c(tVarJ.b(0).g()))
        return bitSet
    }
}

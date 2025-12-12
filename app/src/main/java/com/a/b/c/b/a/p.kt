package com.a.b.c.b.a

import com.a.b.c.b.ai
import java.util.BitSet

class p extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f285a = p()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 2
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return tVarJ.b(0).m() + ", " + tVarJ.b(1).m() + ", " + tVarJ.b(2).m()
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, tVarJ.b(0).g()), a(tVarJ.b(1).g(), tVarJ.b(2).g()))
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return (lVar is ai) && tVarJ.d_() == 3 && c(tVarJ.b(0).g()) && c(tVarJ.b(1).g()) && c(tVarJ.b(2).g())
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(3)
        bitSet.set(0, c(tVarJ.b(0).g()))
        bitSet.set(1, c(tVarJ.b(1).g()))
        bitSet.set(2, c(tVarJ.b(2).g()))
        return bitSet
    }
}

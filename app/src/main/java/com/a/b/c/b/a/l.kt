package com.a.b.c.b.a

import com.a.b.f.c.z
import java.util.BitSet

class l extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f281a = l()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 2
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        return tVarJ.b(0).m() + ", " + tVarJ.b(1).m() + ", " + f(lVar)
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return z ? g(lVar) : ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, b(tVarJ.b(0).g(), tVarJ.b(1).g())), (Short) ((com.a.b.c.b.i) lVar).d())
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        if (!(lVar is com.a.b.c.b.i) || tVarJ.d_() != 2 || !a(tVarJ.b(0).g()) || !a(tVarJ.b(1).g())) {
            return false
        }
        com.a.b.c.b.i iVar = (com.a.b.c.b.i) lVar
        if (!e(iVar.d())) {
            return false
        }
        com.a.b.f.c.a aVarC = iVar.c()
        return (aVarC is z) || (aVarC is com.a.b.f.c.l)
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

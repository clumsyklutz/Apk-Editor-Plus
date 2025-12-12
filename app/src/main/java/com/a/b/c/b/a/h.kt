package com.a.b.c.b.a

import androidx.core.internal.view.SupportMenu
import java.util.BitSet

class h extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f277a = h()

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
        return a((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c(), lVar.j().b(0).k() == 1 ? 32 : 64)
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, tVarJ.b(0).g()), tVarJ.b(0).k() == 1 ? ((com.a.b.f.c.s) ((com.a.b.c.b.i) lVar).c()).j() >> 16 : (Short) (r0.k() >>> 48))
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
        return tVarJ.b(0).k() == 1 ? (sVar.j() & SupportMenu.USER_MASK) == 0 : (sVar.k() & 281474976710655L) == 0
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(1)
        bitSet.set(0, c(tVarJ.b(0).g()))
        return bitSet
    }
}

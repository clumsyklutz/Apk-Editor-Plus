package com.a.b.c.b.a

import com.a.b.f.c.z
import java.util.BitSet

class r extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f287a = r()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 3
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return lVar.j().b(0).m() + ", " + f(lVar)
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return z ? g(lVar) : ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, tVarJ.b(0).g()), ((com.a.b.c.b.i) lVar).d())
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.r rVarB
        if (!(lVar is com.a.b.c.b.i)) {
            return false
        }
        com.a.b.f.b.t tVarJ = lVar.j()
        switch (tVarJ.d_()) {
            case 1:
                rVarB = tVarJ.b(0)
                break
            case 2:
                rVarB = tVarJ.b(0)
                if (rVarB.g() != tVarJ.b(1).g()) {
                    return false
                }
                break
            default:
                return false
        }
        if (!c(rVarB.g())) {
            return false
        }
        com.a.b.f.c.a aVarC = ((com.a.b.c.b.i) lVar).c()
        return (aVarC is z) || (aVarC is com.a.b.f.c.l) || (aVarC is com.a.b.f.c.y)
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        Int iD_ = tVarJ.d_()
        BitSet bitSet = BitSet(iD_)
        Boolean zC = c(tVarJ.b(0).g())
        if (iD_ == 1) {
            bitSet.set(0, zC)
        } else if (tVarJ.b(0).g() == tVarJ.b(1).g()) {
            bitSet.set(0, zC)
            bitSet.set(1, zC)
        }
        return bitSet
    }
}

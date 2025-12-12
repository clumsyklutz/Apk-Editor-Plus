package com.a.b.c.b.a

import com.a.b.c.b.ai
import java.util.BitSet

class e extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f274a = e()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 1
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        Int iD_ = tVarJ.d_()
        return tVarJ.b(iD_ - 2).m() + ", " + tVarJ.b(iD_ - 1).m()
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        Int iD_ = tVarJ.d_()
        rVar.b(a(lVar, b(tVarJ.b(iD_ - 2).g(), tVarJ.b(iD_ - 1).g())))
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        com.a.b.f.b.r rVarB
        com.a.b.f.b.r rVarB2
        if (!(lVar is ai)) {
            return false
        }
        com.a.b.f.b.t tVarJ = lVar.j()
        switch (tVarJ.d_()) {
            case 2:
                rVarB = tVarJ.b(0)
                rVarB2 = tVarJ.b(1)
                break
            case 3:
                rVarB = tVarJ.b(1)
                rVarB2 = tVarJ.b(2)
                if (rVarB.g() != tVarJ.b(0).g()) {
                    return false
                }
                break
            default:
                return false
        }
        return a(rVarB.g()) && a(rVarB2.g())
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        BitSet bitSet = BitSet(2)
        Int iG = tVarJ.b(0).g()
        Int iG2 = tVarJ.b(1).g()
        switch (tVarJ.d_()) {
            case 2:
                bitSet.set(0, a(iG))
                bitSet.set(1, a(iG2))
                return bitSet
            case 3:
                if (iG != iG2) {
                    bitSet.set(0, false)
                    bitSet.set(1, false)
                } else {
                    Boolean zA = a(iG2)
                    bitSet.set(0, zA)
                    bitSet.set(1, zA)
                }
                bitSet.set(2, a(tVarJ.b(2).g()))
                return bitSet
            default:
                throw AssertionError()
        }
    }
}

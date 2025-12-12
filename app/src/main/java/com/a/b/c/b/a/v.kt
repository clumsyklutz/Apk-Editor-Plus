package com.a.b.c.b.a

import com.a.b.f.c.z
import java.util.BitSet

class v extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f291a = v()

    private constructor() {
    }

    private fun b(com.a.b.f.b.t tVar) {
        Int i = 0
        Int iD_ = tVar.d_()
        if (iD_ > 5) {
            return -1
        }
        Int i2 = 0
        while (i2 < iD_) {
            Int iK = tVar.b(i2).k() + i
            if (!a((r5.g() + r5.k()) - 1)) {
                return -1
            }
            i2++
            i = iK
        }
        if (i <= 5) {
            return i
        }
        return -1
    }

    private static com.a.b.f.b.t c(com.a.b.f.b.t tVar) {
        Int i = 0
        Int iB = b(tVar)
        Int iD_ = tVar.d_()
        if (iB == iD_) {
            return tVar
        }
        com.a.b.f.b.t tVar2 = new com.a.b.f.b.t(iB)
        for (Int i2 = 0; i2 < iD_; i2++) {
            com.a.b.f.b.r rVarB = tVar.b(i2)
            tVar2.a(i, rVarB)
            if (rVarB.k() == 2) {
                tVar2.a(i + 1, com.a.b.f.b.r.a(rVarB.g() + 1, com.a.b.f.d.c.i))
                i += 2
            } else {
                i++
            }
        }
        tVar2.b_()
        return tVar2
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 3
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return a(c(lVar.j())) + ", " + f(lVar)
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return z ? g(lVar) : ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        Int iD = ((com.a.b.c.b.i) lVar).d()
        com.a.b.f.b.t tVarC = c(lVar.j())
        Int iD_ = tVarC.d_()
        Int iG = iD_ > 0 ? tVarC.b(0).g() : 0
        Int iG2 = iD_ > 1 ? tVarC.b(1).g() : 0
        Int iG3 = iD_ > 2 ? tVarC.b(2).g() : 0
        Int iG4 = iD_ > 3 ? tVarC.b(3).g() : 0
        Short sA = a(lVar, b(iD_ > 4 ? tVarC.b(4).g() : 0, iD_))
        Short s = (Short) iD
        if ((iG & 15) != iG) {
            throw IllegalArgumentException("n0 out of range 0..15")
        }
        if ((iG2 & 15) != iG2) {
            throw IllegalArgumentException("n1 out of range 0..15")
        }
        if ((iG3 & 15) != iG3) {
            throw IllegalArgumentException("n2 out of range 0..15")
        }
        if ((iG4 & 15) != iG4) {
            throw IllegalArgumentException("n3 out of range 0..15")
        }
        a(rVar, sA, s, (Short) ((iG4 << 12) | (iG3 << 8) | (iG2 << 4) | iG))
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        if (!(lVar is com.a.b.c.b.i)) {
            return false
        }
        com.a.b.c.b.i iVar = (com.a.b.c.b.i) lVar
        if (!e(iVar.d())) {
            return false
        }
        com.a.b.f.c.a aVarC = iVar.c()
        return ((aVarC is com.a.b.f.c.v) || (aVarC is z)) && b(iVar.j()) >= 0
    }

    @Override // com.a.b.c.b.r
    public final BitSet c(com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        Int iD_ = tVarJ.d_()
        BitSet bitSet = BitSet(iD_)
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.b.r rVarB = tVarJ.b(i)
            bitSet.set(i, a((rVarB.k() + rVarB.g()) - 1))
        }
        return bitSet
    }
}

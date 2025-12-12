package com.a.b.c.b.a

import com.a.b.f.c.z

class w extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f292a = w()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 3
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        StringBuilder sb = StringBuilder()
        com.a.b.f.b.t tVarJ = lVar.j()
        Int iD_ = tVarJ.d_()
        StringBuilder sb2 = StringBuilder(30)
        sb2.append("{")
        switch (iD_) {
            case 0:
                break
            case 1:
                sb2.append(tVarJ.b(0).m())
                break
            default:
                com.a.b.f.b.r rVarB = tVarJ.b(iD_ - 1)
                if (rVarB.k() == 2) {
                    rVarB = rVarB.b(1)
                }
                sb2.append(tVarJ.b(0).m())
                sb2.append("..")
                sb2.append(rVarB.m())
                break
        }
        sb2.append("}")
        return sb.append(sb2.toString()).append(", ").append(f(lVar)).toString()
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return z ? g(lVar) : ""
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        com.a.b.f.b.t tVarJ = lVar.j()
        a(rVar, a(lVar, tVarJ.e()), (Short) ((com.a.b.c.b.i) lVar).d(), (Short) (tVarJ.d_() == 0 ? 0 : tVarJ.b(0).g()))
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        Boolean z
        if (!(lVar is com.a.b.c.b.i)) {
            return false
        }
        com.a.b.c.b.i iVar = (com.a.b.c.b.i) lVar
        Int iD = iVar.d()
        com.a.b.f.c.a aVarC = iVar.c()
        if (!e(iD)) {
            return false
        }
        if (!(aVarC is com.a.b.f.c.v) && !(aVarC is z)) {
            return false
        }
        com.a.b.f.b.t tVarJ = iVar.j()
        if (tVarJ.d_() != 0) {
            Int iD_ = tVarJ.d_()
            if (iD_ < 2) {
                z = true
                return z ? false : false
            }
            Int iG = tVarJ.b(0).g()
            for (Int i = 0; i < iD_; i++) {
                com.a.b.f.b.r rVarB = tVarJ.b(i)
                if (rVarB.g() != iG) {
                    z = false
                    break
                }
                iG += rVarB.k()
            }
            z = true
            if (z || !e(tVarJ.b(0).g()) || !c(tVarJ.e())) {
                return false
            }
        }
        return true
    }
}

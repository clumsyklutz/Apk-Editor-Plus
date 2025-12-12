package com.a.b.f.b

class f implements ad {

    /* renamed from: a, reason: collision with root package name */
    public static val f501a = f()

    /* renamed from: b, reason: collision with root package name */
    private val f502b = false

    static {
        f(true)
    }

    private constructor() {
    }

    private constructor(Boolean z) {
    }

    @Override // com.a.b.f.b.ad
    public final Boolean a(w wVar, r rVar, r rVar2) {
        if (rVar.a() != com.a.b.f.d.c.f) {
            return false
        }
        if (!(rVar2.h() instanceof com.a.b.f.c.n)) {
            if ((rVar.h() instanceof com.a.b.f.c.n) && wVar.a() == 15) {
                return ((com.a.b.f.c.n) rVar.h()).l()
            }
            return false
        }
        com.a.b.f.c.n nVar = (com.a.b.f.c.n) rVar2.h()
        switch (wVar.a()) {
            case 14:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
                return nVar.l()
            case 15:
                return com.a.b.f.c.n.a(-nVar.j()).l()
            case 19:
            default:
                return false
            case 23:
            case 24:
            case 25:
                if (nVar.i()) {
                    Int iJ = nVar.j()
                    if (((Byte) iJ) == iJ) {
                        return true
                    }
                }
                return false
        }
    }

    @Override // com.a.b.f.b.ad
    public final Boolean a(w wVar, t tVar) {
        if (this.f502b || !wVar.e()) {
            return false
        }
        Int iD_ = tVar.d_()
        Int iK = 0
        for (Int i = 0; i < iD_; i++) {
            iK += tVar.b(i).k()
        }
        return iK >= 6
    }
}

package com.a.b.c.b.a

import com.a.b.c.b.al

class a extends com.a.b.c.b.r {

    /* renamed from: a, reason: collision with root package name */
    public static final com.a.b.c.b.r f270a = a()

    private constructor() {
    }

    @Override // com.a.b.c.b.r
    public final Int a() {
        return 1
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar) {
        return d(lVar)
    }

    @Override // com.a.b.c.b.r
    public final String a(com.a.b.c.b.l lVar, Boolean z) {
        return e(lVar)
    }

    @Override // com.a.b.c.b.r
    public final Unit a(com.a.b.h.r rVar, com.a.b.c.b.l lVar) {
        rVar.b(a(lVar, ((al) lVar).e() & 255))
    }

    @Override // com.a.b.c.b.r
    public final Boolean a(al alVar) {
        Int iE = alVar.e()
        return iE != 0 && b(iE)
    }

    @Override // com.a.b.c.b.r
    public final Boolean b(com.a.b.c.b.l lVar) {
        if (!(lVar is al) || lVar.j().d_() != 0) {
            return false
        }
        al alVar = (al) lVar
        if (alVar.n()) {
            return a(alVar)
        }
        return true
    }
}

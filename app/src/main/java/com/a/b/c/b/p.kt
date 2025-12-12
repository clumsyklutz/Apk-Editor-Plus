package com.a.b.c.b

abstract class p extends l {
    constructor(n nVar, com.a.b.f.b.z zVar, com.a.b.f.b.t tVar) {
        super(nVar, zVar, tVar)
    }

    @Override // com.a.b.c.b.l
    public final Int a() {
        return h().c().a()
    }

    @Override // com.a.b.c.b.l
    protected final String a(Boolean z) {
        r rVarC = h().c()
        String strE = h().e()
        String strA = rVarC.a(this)
        String strA2 = rVarC.a(this, z)
        StringBuilder sb = StringBuilder(100)
        sb.append(strE)
        if (strA.length() != 0) {
            sb.append(' ')
            sb.append(strA)
        }
        if (strA2.length() != 0) {
            sb.append(" // ")
            sb.append(strA2)
        }
        return sb.toString()
    }

    @Override // com.a.b.c.b.l
    public final Unit a(com.a.b.h.r rVar) {
        h().c().a(rVar, this)
    }

    @Override // com.a.b.c.b.l
    public final l d(Int i) {
        return a(j().d(i))
    }
}

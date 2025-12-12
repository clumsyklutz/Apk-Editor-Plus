package com.a.b.c.b

class q extends am {

    /* renamed from: a, reason: collision with root package name */
    private Array<ai> f333a

    constructor(com.a.b.f.b.z zVar, com.a.b.f.b.t tVar) {
        super(zVar, tVar)
        if (tVar.d_() == 0) {
            throw IllegalArgumentException("registers.size() == 0")
        }
        this.f333a = null
    }

    private fun a(com.a.b.f.b.r rVar, Int i) {
        return l.a(com.a.b.f.b.z.f530a, com.a.b.f.b.r.a(i, rVar.a()), rVar)
    }

    private fun c() {
        Int iK = 0
        if (this.f333a != null) {
            return
        }
        com.a.b.f.b.t tVarJ = j()
        Int iD_ = tVarJ.d_()
        this.f333a = new ai[iD_]
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.b.r rVarB = tVarJ.b(i)
            this.f333a[i] = a(rVarB, iK)
            iK += rVarB.k()
        }
    }

    @Override // com.a.b.c.b.l
    public final Int a() {
        c()
        Int iA = 0
        for (ai aiVar : this.f333a) {
            iA += aiVar.a()
        }
        return iA
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        return q(i(), tVar)
    }

    @Override // com.a.b.c.b.l
    protected final String a(Boolean z) {
        Int iK = 0
        com.a.b.f.b.t tVarJ = j()
        Int iD_ = tVarJ.d_()
        StringBuffer stringBuffer = StringBuffer(100)
        for (Int i = 0; i < iD_; i++) {
            com.a.b.f.b.r rVarB = tVarJ.b(i)
            ai aiVarA = a(rVarB, iK)
            if (i != 0) {
                stringBuffer.append('\n')
            }
            stringBuffer.append(aiVarA.a(z))
            iK += rVarB.k()
        }
        return stringBuffer.toString()
    }

    @Override // com.a.b.c.b.l
    public final Unit a(com.a.b.h.r rVar) {
        c()
        for (ai aiVar : this.f333a) {
            aiVar.a(rVar)
        }
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        return null
    }
}

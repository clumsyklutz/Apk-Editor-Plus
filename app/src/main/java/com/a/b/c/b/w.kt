package com.a.b.c.b

class w extends an {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.b.v f341a

    constructor(com.a.b.f.b.z zVar, com.a.b.f.b.v vVar) {
        super(zVar)
        if (vVar == null) {
            throw NullPointerException("locals == null")
        }
        this.f341a = vVar
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.f.b.t tVar) {
        return w(i(), this.f341a)
    }

    @Override // com.a.b.c.b.l
    public final l a(com.a.b.g.ag agVar) {
        return w(i(), agVar.a(this.f341a))
    }

    @Override // com.a.b.c.b.l
    protected final String a(Boolean z) {
        Int iD = this.f341a.d()
        Int iB = this.f341a.b()
        StringBuffer stringBuffer = StringBuffer((iD * 40) + 100)
        stringBuffer.append("local-snapshot")
        for (Int i = 0; i < iB; i++) {
            com.a.b.f.b.r rVarA = this.f341a.a(i)
            if (rVarA != null) {
                stringBuffer.append("\n  ")
                stringBuffer.append(x.a(rVarA))
            }
        }
        return stringBuffer.toString()
    }

    @Override // com.a.b.c.b.l
    protected final String b() {
        return this.f341a.toString()
    }

    public final com.a.b.f.b.v c() {
        return this.f341a
    }

    @Override // com.a.b.c.b.an, com.a.b.c.b.l
    public final l d(Int i) {
        return w(i(), this.f341a.b(i))
    }
}

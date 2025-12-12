package com.a.b.f.b

class ac extends i {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.e f497a

    constructor(w wVar, z zVar, t tVar, com.a.b.f.d.e eVar) {
        super(wVar, zVar, null, tVar)
        if (wVar.d() != 6) {
            throw IllegalArgumentException("bogus branchingness")
        }
        if (eVar == null) {
            throw NullPointerException("catches == null")
        }
        this.f497a = eVar
    }

    fun a(com.a.b.f.d.e eVar) {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append("catch")
        Int iD_ = eVar.d_()
        for (Int i = 0; i < iD_; i++) {
            stringBuffer.append(" ")
            stringBuffer.append(eVar.a(i).d())
        }
        return stringBuffer.toString()
    }

    @Override // com.a.b.f.b.i
    public final i a(r rVar, t tVar) {
        return ac(f(), g(), tVar, this.f497a)
    }

    @Override // com.a.b.f.b.i
    public final i a(com.a.b.f.d.c cVar) {
        return ac(f(), g(), j(), this.f497a.a(cVar))
    }

    @Override // com.a.b.f.b.i
    public final String a() {
        return a(this.f497a)
    }

    @Override // com.a.b.f.b.i
    public final Unit a(k kVar) {
        kVar.a(this)
    }

    @Override // com.a.b.f.b.i
    public final com.a.b.f.d.e b() {
        return this.f497a
    }
}

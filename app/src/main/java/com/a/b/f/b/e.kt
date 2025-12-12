package com.a.b.f.b

abstract class e extends i {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.a f500a

    constructor(w wVar, z zVar, r rVar, t tVar, com.a.b.f.c.a aVar) {
        super(wVar, zVar, rVar, tVar)
        if (aVar == null) {
            throw NullPointerException("cst == null")
        }
        this.f500a = aVar
    }

    @Override // com.a.b.f.b.i
    fun a() {
        return this.f500a.d()
    }

    @Override // com.a.b.f.b.i
    public final Boolean a(i iVar) {
        return super.a(iVar) && this.f500a.equals(((e) iVar).f500a)
    }

    public final com.a.b.f.c.a g_() {
        return this.f500a
    }
}

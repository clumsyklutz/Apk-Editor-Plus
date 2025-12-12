package com.a.b.c.c

import jadx.core.codegen.CodeWriter

class w implements com.a.b.h.s, Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.l f416a

    /* renamed from: b, reason: collision with root package name */
    private d f417b

    constructor(com.a.b.f.c.l lVar, d dVar) {
        if (lVar == null) {
            throw NullPointerException("field == null")
        }
        this.f416a = lVar
        this.f417b = dVar
    }

    public final Unit a(r rVar) {
        y yVarM = rVar.m()
        al alVarE = rVar.e()
        yVarM.a(this.f416a)
        this.f417b = (d) alVarE.b(this.f417b)
    }

    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        Int iB = rVar.m().b(this.f416a)
        Int iF = this.f417b.f()
        if (rVar2.b()) {
            rVar2.a(0, CodeWriter.INDENT + this.f416a.d())
            rVar2.a(4, "      field_idx:       " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(4, "      annotations_off: " + com.gmail.heagoo.a.c.a.t(iF))
        }
        rVar2.c(iB)
        rVar2.c(iF)
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        return this.f416a.compareTo(((w) obj).f416a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f416a.d() + ": " + this.f417b
    }

    public final Boolean equals(Object obj) {
        if (obj is w) {
            return this.f416a.equals(((w) obj).f416a)
        }
        return false
    }

    public final Int hashCode() {
        return this.f416a.hashCode()
    }
}

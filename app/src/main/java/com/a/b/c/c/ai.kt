package com.a.b.c.c

import jadx.core.codegen.CodeWriter

class ai implements com.a.b.h.s, Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.v f355a

    /* renamed from: b, reason: collision with root package name */
    private d f356b

    constructor(com.a.b.f.c.v vVar, d dVar) {
        if (vVar == null) {
            throw NullPointerException("method == null")
        }
        this.f355a = vVar
        this.f356b = dVar
    }

    public final com.a.b.f.c.v a() {
        return this.f355a
    }

    public final Unit a(r rVar) {
        ak akVarN = rVar.n()
        al alVarE = rVar.e()
        akVarN.a((com.a.b.f.c.f) this.f355a)
        this.f356b = (d) alVarE.b(this.f356b)
    }

    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        Int iB = rVar.n().b(this.f355a)
        Int iF = this.f356b.f()
        if (rVar2.b()) {
            rVar2.a(0, CodeWriter.INDENT + this.f355a.d())
            rVar2.a(4, "      method_idx:      " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(4, "      annotations_off: " + com.gmail.heagoo.a.c.a.t(iF))
        }
        rVar2.c(iB)
        rVar2.c(iF)
    }

    public final com.a.b.f.a.c b() {
        return this.f356b.c()
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        return this.f355a.compareTo(((ai) obj).f355a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f355a.d() + ": " + this.f356b
    }

    public final Boolean equals(Object obj) {
        if (obj is ai) {
            return this.f355a.equals(((ai) obj).f355a)
        }
        return false
    }

    public final Int hashCode() {
        return this.f355a.hashCode()
    }
}

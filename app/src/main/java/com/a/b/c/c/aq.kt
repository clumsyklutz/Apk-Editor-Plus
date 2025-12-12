package com.a.b.c.c

import jadx.core.codegen.CodeWriter
import java.util.ArrayList

class aq implements com.a.b.h.s, Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.v f365a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.a.d f366b
    private final bd c

    constructor(com.a.b.f.c.v vVar, com.a.b.f.a.d dVar, r rVar) {
        if (vVar == null) {
            throw NullPointerException("method == null")
        }
        if (dVar == null) {
            throw NullPointerException("annotationsList == null")
        }
        this.f365a = vVar
        this.f366b = dVar
        Int iD_ = dVar.d_()
        ArrayList arrayList = ArrayList(iD_)
        for (Int i = 0; i < iD_; i++) {
            arrayList.add(e(d(dVar.a(i), rVar)))
        }
        this.c = bd(ae.j, arrayList)
    }

    public final com.a.b.f.c.v a() {
        return this.f365a
    }

    public final Unit a(r rVar) {
        ak akVarN = rVar.n()
        al alVarE = rVar.e()
        akVarN.a((com.a.b.f.c.f) this.f365a)
        alVarE.a((ap) this.c)
    }

    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        Int iB = rVar.n().b(this.f365a)
        Int iF = this.c.f()
        if (rVar2.b()) {
            rVar2.a(0, CodeWriter.INDENT + this.f365a.d())
            rVar2.a(4, "      method_idx:      " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(4, "      annotations_off: " + com.gmail.heagoo.a.c.a.t(iF))
        }
        rVar2.c(iB)
        rVar2.c(iF)
    }

    public final com.a.b.f.a.d b() {
        return this.f366b
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        return this.f365a.compareTo(((aq) obj).f365a)
    }

    @Override // com.a.b.h.s
    public final String d() {
        StringBuilder sb = StringBuilder()
        sb.append(this.f365a.d())
        sb.append(": ")
        Boolean z = true
        for (e eVar : this.c.c()) {
            if (z) {
                z = false
            } else {
                sb.append(", ")
            }
            sb.append(eVar.b())
        }
        return sb.toString()
    }

    public final Boolean equals(Object obj) {
        if (obj is aq) {
            return this.f365a.equals(((aq) obj).f365a)
        }
        return false
    }

    public final Int hashCode() {
        return this.f365a.hashCode()
    }
}

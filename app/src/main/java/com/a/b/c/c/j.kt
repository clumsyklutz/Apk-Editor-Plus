package com.a.b.c.c

import jadx.core.codegen.CodeWriter
import java.util.ArrayList

class j extends ac {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.z f397a

    /* renamed from: b, reason: collision with root package name */
    private final Int f398b
    private final com.a.b.f.c.z c
    private bb d
    private final com.a.b.f.c.y e
    private final i f
    private s g
    private g h

    constructor(com.a.b.f.c.z zVar, Int i, com.a.b.f.c.z zVar2, com.a.b.f.d.e eVar, com.a.b.f.c.y yVar) {
        if (zVar == null) {
            throw NullPointerException("thisClass == null")
        }
        if (eVar == null) {
            throw NullPointerException("interfaces == null")
        }
        this.f397a = zVar
        this.f398b = i
        this.c = zVar2
        this.d = eVar.d_() == 0 ? null : bb(eVar)
        this.e = yVar
        this.f = i(zVar)
        this.g = null
        this.h = g()
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.g
    }

    public final com.a.b.f.a.c a(com.a.b.f.c.v vVar) {
        return this.h.a(vVar)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        ba baVarK = rVar.k()
        al alVarO = rVar.o()
        al alVarE = rVar.e()
        al alVarF = rVar.f()
        ay ayVarH = rVar.h()
        baVarK.a(this.f397a)
        if (!this.f.c()) {
            rVar.j().a((ap) this.f)
            com.a.b.f.c.d dVarE = this.f.e()
            if (dVarE != null) {
                this.g = (s) alVarO.b(s(dVarE))
            }
        }
        if (this.c != null) {
            baVarK.a(this.c)
        }
        if (this.d != null) {
            this.d = (bb) alVarF.b(this.d)
        }
        if (this.e != null) {
            ayVarH.a(this.e)
        }
        if (this.h.c()) {
            return
        }
        if (this.h.d()) {
            this.h = (g) alVarE.b(this.h)
        } else {
            alVarE.a((ap) this.h)
        }
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        Boolean zB = rVar2.b()
        ba baVarK = rVar.k()
        Int iB = baVarK.b(this.f397a)
        Int iB2 = this.c == null ? -1 : baVarK.b(this.c)
        Int iB3 = ap.b(this.d)
        Int iF = this.h.c() ? 0 : this.h.f()
        Int iB4 = this.e == null ? -1 : rVar.h().b(this.e)
        Int iF2 = this.f.c() ? 0 : this.f.f()
        Int iB5 = ap.b(this.g)
        if (zB) {
            rVar2.a(0, j() + ' ' + this.f397a.d())
            rVar2.a(4, "  class_idx:           " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(4, "  access_flags:        " + com.gmail.heagoo.a.c.a.g(this.f398b))
            rVar2.a(4, "  superclass_idx:      " + com.gmail.heagoo.a.c.a.t(iB2) + " // " + (this.c == null ? "<none>" : this.c.d()))
            rVar2.a(4, "  interfaces_off:      " + com.gmail.heagoo.a.c.a.t(iB3))
            if (iB3 != 0) {
                com.a.b.f.d.e eVarC = this.d.c()
                Int iD_ = eVarC.d_()
                for (Int i = 0; i < iD_; i++) {
                    rVar2.a(0, CodeWriter.INDENT + eVarC.a(i).d())
                }
            }
            rVar2.a(4, "  source_file_idx:     " + com.gmail.heagoo.a.c.a.t(iB4) + " // " + (this.e == null ? "<none>" : this.e.d()))
            rVar2.a(4, "  annotations_off:     " + com.gmail.heagoo.a.c.a.t(iF))
            rVar2.a(4, "  class_data_off:      " + com.gmail.heagoo.a.c.a.t(iF2))
            rVar2.a(4, "  static_values_off:   " + com.gmail.heagoo.a.c.a.t(iB5))
        }
        rVar2.c(iB)
        rVar2.c(this.f398b)
        rVar2.c(iB2)
        rVar2.c(iB3)
        rVar2.c(iB4)
        rVar2.c(iF)
        rVar2.c(iF2)
        rVar2.c(iB5)
    }

    public final Unit a(t tVar) {
        this.f.a(tVar)
    }

    public final Unit a(t tVar, com.a.b.f.c.a aVar) {
        this.f.a(tVar, aVar)
    }

    public final Unit a(v vVar) {
        this.f.a(vVar)
    }

    public final Unit a(com.a.b.f.a.c cVar, r rVar) {
        this.h.a(cVar, rVar)
    }

    public final Unit a(com.a.b.f.c.l lVar, com.a.b.f.a.c cVar, r rVar) {
        this.h.a(lVar, cVar, rVar)
    }

    public final Unit a(com.a.b.f.c.v vVar, com.a.b.f.a.c cVar, r rVar) {
        this.h.a(vVar, cVar, rVar)
    }

    public final Unit a(com.a.b.f.c.v vVar, com.a.b.f.a.d dVar, r rVar) {
        this.h.a(vVar, dVar, rVar)
    }

    public final com.a.b.f.a.d b(com.a.b.f.c.v vVar) {
        return this.h.b(vVar)
    }

    public final Unit b(v vVar) {
        this.f.b(vVar)
    }

    public final com.a.b.f.c.z c() {
        return this.f397a
    }

    public final com.a.b.f.c.z d() {
        return this.c
    }

    public final com.a.b.f.d.e e() {
        return this.d == null ? com.a.b.f.d.b.f565a : this.d.c()
    }

    @Override // com.a.b.c.c.ad
    public final Int e_() {
        return 32
    }

    public final com.a.b.f.c.y f() {
        return this.e
    }

    public final ArrayList g() {
        return this.f.d()
    }
}

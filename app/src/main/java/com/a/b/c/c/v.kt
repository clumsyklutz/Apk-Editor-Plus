package com.a.b.c.c

import java.io.PrintWriter

class v extends u implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.v f414a

    /* renamed from: b, reason: collision with root package name */
    private final l f415b

    constructor(com.a.b.f.c.v vVar, Int i, com.a.b.c.b.j jVar, com.a.b.f.d.e eVar) {
        super(i)
        this.f414a = vVar
        if (jVar == null) {
            this.f415b = null
        } else {
            this.f415b = l(vVar, jVar, (i & 8) != 0, eVar)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    fun compareTo(v vVar) {
        return this.f414a.compareTo(vVar.f414a)
    }

    @Override // com.a.b.c.c.u
    public final Int a(r rVar, com.a.b.h.r rVar2, Int i, Int i2) {
        Int iB = rVar.n().b(this.f414a)
        Int i3 = iB - i
        Int iB2 = b()
        Int iB3 = ap.b(this.f415b)
        if ((iB3 != 0) != ((iB2 & 1280) == 0)) {
            throw UnsupportedOperationException("code vs. access_flags mismatch")
        }
        if (rVar2.b()) {
            rVar2.a(0, String.format("  [%x] %s", Integer.valueOf(i2), this.f414a.d()))
            rVar2.a(com.gmail.heagoo.a.c.a.d(i3), "    method_idx:   " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(com.gmail.heagoo.a.c.a.d(iB2), "    access_flags: " + com.gmail.heagoo.a.c.a.j(iB2))
            rVar2.a(com.gmail.heagoo.a.c.a.d(iB3), "    code_off:     " + com.gmail.heagoo.a.c.a.t(iB3))
        }
        rVar2.e(i3)
        rVar2.e(iB2)
        rVar2.e(iB3)
        return iB
    }

    public final com.a.b.f.c.y a() {
        return this.f414a.l().a()
    }

    public final Unit a(r rVar) {
        ak akVarN = rVar.n()
        al alVarE = rVar.e()
        akVarN.a((com.a.b.f.c.f) this.f414a)
        if (this.f415b != null) {
            alVarE.a((ap) this.f415b)
        }
    }

    public final Unit a(PrintWriter printWriter, Boolean z) {
        if (this.f415b == null) {
            printWriter.println(this.f414a.d() + ": abstract or native")
        } else {
            this.f415b.a(printWriter, "  ", z)
        }
    }

    public final com.a.b.f.c.v c() {
        return this.f414a
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f414a.d()
    }

    public final Boolean equals(Object obj) {
        return (obj is v) && compareTo((v) obj) == 0
    }

    public final String toString() {
        StringBuffer stringBuffer = StringBuffer(100)
        stringBuffer.append(getClass().getName())
        stringBuffer.append('{')
        stringBuffer.append(com.gmail.heagoo.a.c.a.v(b()))
        stringBuffer.append(' ')
        stringBuffer.append(this.f414a)
        if (this.f415b != null) {
            stringBuffer.append(' ')
            stringBuffer.append(this.f415b)
        }
        stringBuffer.append('}')
        return stringBuffer.toString()
    }
}

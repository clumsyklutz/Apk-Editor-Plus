package com.a.b.c.c

import jadx.core.codegen.CodeWriter
import java.util.Iterator

class d extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.a.c f386a

    /* renamed from: b, reason: collision with root package name */
    private final Array<a> f387b

    constructor(com.a.b.f.a.c cVar, r rVar) {
        super(4, a(cVar))
        this.f386a = cVar
        this.f387b = new a[cVar.b()]
        Int i = 0
        Iterator it = cVar.d().iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                return
            }
            this.f387b[i2] = a((com.a.b.f.a.a) it.next(), rVar)
            i = i2 + 1
        }
    }

    private fun a(com.a.b.f.a.c cVar) {
        try {
            return (cVar.b() << 2) + 4
        } catch (NullPointerException e) {
            throw NullPointerException("list == null")
        }
    }

    @Override // com.a.b.c.c.ap
    protected final Int a(ap apVar) {
        return this.f386a.compareTo(((d) apVar).f386a)
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.k
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        a.a(this.f387b)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        al alVarO = rVar.o()
        Int length = this.f387b.length
        for (Int i = 0; i < length; i++) {
            this.f387b[i] = (a) alVarO.b(this.f387b[i])
        }
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Boolean zB = rVar2.b()
        Int length = this.f387b.length
        if (zB) {
            rVar2.a(0, h() + " annotation set")
            rVar2.a(4, "  size: " + com.gmail.heagoo.a.c.a.t(length))
        }
        rVar2.c(length)
        for (Int i = 0; i < length; i++) {
            Int iF = this.f387b[i].f()
            if (zB) {
                rVar2.a(4, "  entries[" + Integer.toHexString(i) + "]: " + com.gmail.heagoo.a.c.a.t(iF))
                this.f387b[i].a(rVar2, CodeWriter.INDENT)
            }
            rVar2.c(iF)
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return this.f386a.toString()
    }

    public final com.a.b.f.a.c c() {
        return this.f386a
    }

    public final Int hashCode() {
        return this.f386a.hashCode()
    }
}

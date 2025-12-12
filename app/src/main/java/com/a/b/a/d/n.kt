package com.a.b.a.d

import com.a.b.f.c.w
import com.a.b.f.c.y
import com.a.b.f.c.z

abstract class n {

    /* renamed from: a, reason: collision with root package name */
    private final k f245a

    /* renamed from: b, reason: collision with root package name */
    private final z f246b
    private final Int c
    private final b d
    private Int e
    private com.a.b.a.e.j f

    constructor(k kVar, z zVar, Int i, b bVar) {
        if (kVar == null) {
            throw NullPointerException("cf == null")
        }
        if (i < 0) {
            throw IllegalArgumentException("offset < 0")
        }
        if (bVar == null) {
            throw NullPointerException("attributeFactory == null")
        }
        this.f245a = kVar
        this.f246b = zVar
        this.c = i
        this.d = bVar
        this.e = -1
    }

    protected abstract com.a.b.a.e.f a(Int i, Int i2, w wVar, com.a.b.a.e.b bVar)

    protected abstract String a(Int i)

    public final Unit a(com.a.b.a.e.j jVar) {
        this.f = jVar
    }

    protected abstract String b()

    protected abstract Int c()

    public final Int d() {
        e()
        return this.e
    }

    protected final Unit e() {
        if (this.e < 0) {
            Int iC = c()
            Int iF = f()
            Int i = this.c + 2
            com.a.b.h.c cVarB = this.f245a.b()
            com.a.b.f.c.b bVarG = this.f245a.g()
            if (this.f != null) {
                StringBuilder().append(b()).append("s_count: ").append(com.gmail.heagoo.a.c.a.v(iF))
            }
            Int iA = i
            for (Int i2 = 0; i2 < iF; i2++) {
                try {
                    Int iF2 = cVarB.f(iA)
                    Int iF3 = cVarB.f(iA + 2)
                    Int iF4 = cVarB.f(iA + 4)
                    y yVar = (y) bVarG.a(iF3)
                    y yVar2 = (y) bVarG.a(iF4)
                    if (this.f != null) {
                        yVar.j()
                        yVar2.j()
                        StringBuilder("\n").append(b()).append("s[").append(i2).append("]:\n")
                        StringBuilder("access_flags: ").append(a(iF2))
                        StringBuilder("name: ").append(yVar.d())
                        StringBuilder("descriptor: ").append(yVar2.d())
                    }
                    c cVar = c(this.f245a, iC, iA + 6, this.d)
                    cVar.a(this.f)
                    iA = cVar.a()
                    com.a.b.a.e.k kVarB = cVar.b()
                    kVarB.b_()
                    a(i2, iF2, w(yVar, yVar2), kVarB)
                    if (this.f != null) {
                        StringBuilder("end ").append(b()).append("s[").append(i2).append("]\n")
                        yVar.j()
                        yVar2.j()
                    }
                } catch (com.a.b.a.e.i e) {
                    e.a("...while parsing " + b() + "s[" + i2 + "]")
                    throw e
                } catch (RuntimeException e2) {
                    com.a.b.a.e.i iVar = new com.a.b.a.e.i(e2)
                    iVar.a("...while parsing " + b() + "s[" + i2 + "]")
                    throw iVar
                }
            }
            this.e = iA
        }
    }

    protected final Int f() {
        return this.f245a.b().f(this.c)
    }

    protected final z g() {
        return this.f246b
    }
}

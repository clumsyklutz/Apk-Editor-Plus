package com.a.b.c.c

import jadx.core.codegen.CodeWriter
import java.io.PrintWriter
import java.util.Iterator

class l extends ap {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.v f401a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.c.b.j f402b
    private h c
    private final Boolean d
    private final com.a.b.f.d.e e
    private q f

    constructor(com.a.b.f.c.v vVar, com.a.b.c.b.j jVar, Boolean z, com.a.b.f.d.e eVar) {
        super(4, -1)
        if (vVar == null) {
            throw NullPointerException("ref == null")
        }
        if (jVar == null) {
            throw NullPointerException("code == null")
        }
        if (eVar == null) {
            throw NullPointerException("throwsList == null")
        }
        this.f401a = vVar
        this.f402b = jVar
        this.d = z
        this.e = eVar
        this.c = null
        this.f = null
    }

    private fun c() {
        return this.f401a.b(this.d)
    }

    private fun d() {
        return this.f402b.f().g()
    }

    private fun e() {
        return this.f402b.f().f()
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.m
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        Int iB
        r rVarE = atVar.e()
        this.f402b.a(m(this, rVarE))
        if (this.c != null) {
            this.c.a(rVarE)
            iB = this.c.b()
        } else {
            iB = 0
        }
        Int iE = this.f402b.f().e()
        if ((iE & 1) != 0) {
            iE++
        }
        a(iB + (iE << 1) + 16)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        al alVarO = rVar.o()
        ba baVarK = rVar.k()
        if (this.f402b.a() || this.f402b.b()) {
            this.f = q(this.f402b, this.d, this.f401a)
            alVarO.a((ap) this.f)
        }
        if (this.f402b.c()) {
            Iterator it = this.f402b.d().iterator()
            while (it.hasNext()) {
                baVarK.a((com.a.b.f.d.c) it.next())
            }
            this.c = h(this.f402b)
        }
        Iterator it2 = this.f402b.e().iterator()
        while (it2.hasNext()) {
            rVar.a((com.a.b.f.c.a) it2.next())
        }
    }

    public final Unit a(PrintWriter printWriter, String str, Boolean z) {
        printWriter.println(this.f401a.d() + ":")
        com.a.b.c.b.m mVarF = this.f402b.f()
        printWriter.println("regs: " + com.gmail.heagoo.a.c.a.v(e()) + "; ins: " + com.gmail.heagoo.a.c.a.v(c()) + "; outs: " + com.gmail.heagoo.a.c.a.v(d()))
        mVarF.a(printWriter, str, z)
        String str2 = str + "  "
        if (this.c != null) {
            printWriter.print(str)
            printWriter.println("catches")
            this.c.a(printWriter, str2)
        }
        if (this.f != null) {
            printWriter.print(str)
            printWriter.println("debug info")
            this.f.a(printWriter, str2)
        }
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Boolean zB = rVar2.b()
        Int iE = e()
        Int iD = d()
        Int iC = c()
        Int iE2 = this.f402b.f().e()
        Boolean z = (iE2 & 1) != 0
        Int iA = this.c == null ? 0 : this.c.a()
        Int iF = this.f == null ? 0 : this.f.f()
        if (zB) {
            rVar2.a(0, h() + ' ' + this.f401a.d())
            rVar2.a(2, "  registers_size: " + com.gmail.heagoo.a.c.a.v(iE))
            rVar2.a(2, "  ins_size:       " + com.gmail.heagoo.a.c.a.v(iC))
            rVar2.a(2, "  outs_size:      " + com.gmail.heagoo.a.c.a.v(iD))
            rVar2.a(2, "  tries_size:     " + com.gmail.heagoo.a.c.a.v(iA))
            rVar2.a(4, "  debug_off:      " + com.gmail.heagoo.a.c.a.t(iF))
            rVar2.a(4, "  insns_size:     " + com.gmail.heagoo.a.c.a.t(iE2))
            if (this.e.d_() != 0) {
                rVar2.a(0, "  throws " + com.a.b.f.d.b.a(this.e))
            }
        }
        rVar2.b(iE)
        rVar2.b(iC)
        rVar2.b(iD)
        rVar2.b(iA)
        rVar2.c(iF)
        rVar2.c(iE2)
        try {
            this.f402b.f().a(rVar2)
            if (this.c != null) {
                if (z) {
                    if (zB) {
                        rVar2.a(2, "  padding: 0")
                    }
                    rVar2.b(0)
                }
                this.c.a(rVar2)
            }
            if (!zB || this.f == null) {
                return
            }
            rVar2.a(0, "  debug info")
            this.f.a(rVar, rVar2, CodeWriter.INDENT)
        } catch (RuntimeException e) {
            throw com.a.a.a.d.a(e, "...while writing instructions for " + this.f401a.d())
        }
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        return this.f401a.d()
    }

    public final String toString() {
        return "CodeItem{" + b() + "}"
    }
}

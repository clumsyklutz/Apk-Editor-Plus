package com.a.b.c.c

import java.io.PrintWriter
import java.util.Map
import java.util.TreeMap

class h {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.c.b.j f393a

    /* renamed from: b, reason: collision with root package name */
    private com.a.b.c.b.f f394b = null
    private Array<Byte> c = null
    private Int d = 0
    private TreeMap e = null

    constructor(com.a.b.c.b.j jVar) {
        this.f393a = jVar
    }

    private fun a(com.a.b.c.b.d dVar, Int i, Int i2, String str, PrintWriter printWriter, com.a.b.h.r rVar) {
        String strA = dVar.a(str, com.gmail.heagoo.a.c.a.v(i) + ": ")
        if (printWriter != null) {
            printWriter.println(strA)
        }
        rVar.a(i2, strA)
    }

    private fun a(String str, PrintWriter printWriter, com.a.b.h.r rVar) {
        Int i = 0
        c()
        Boolean z = rVar != null
        Int i2 = z ? 6 : 0
        Int i3 = z ? 2 : 0
        Int iD_ = this.f394b.d_()
        String str2 = str + "  "
        if (z) {
            rVar.a(0, str + "tries:")
        } else {
            printWriter.println(str + "tries:")
        }
        for (Int i4 = 0; i4 < iD_; i4++) {
            com.a.b.c.b.g gVarA = this.f394b.a(i4)
            com.a.b.c.b.d dVarC = gVarA.c()
            String str3 = str2 + "try " + com.gmail.heagoo.a.c.a.w(gVarA.a()) + ".." + com.gmail.heagoo.a.c.a.w(gVarA.b())
            String strA = dVarC.a(str2, "")
            if (z) {
                rVar.a(i2, str3)
                rVar.a(i3, strA)
            } else {
                printWriter.println(str3)
                printWriter.println(strA)
            }
        }
        if (z) {
            rVar.a(0, str + "handlers:")
            rVar.a(this.d, str2 + "size: " + com.gmail.heagoo.a.c.a.v(this.e.size()))
            com.a.b.c.b.d dVar = null
            for (Map.Entry entry : this.e.entrySet()) {
                com.a.b.c.b.d dVar2 = (com.a.b.c.b.d) entry.getKey()
                Int iIntValue = ((Integer) entry.getValue()).intValue()
                if (dVar != null) {
                    a(dVar, i, iIntValue - i, str2, printWriter, rVar)
                }
                dVar = dVar2
                i = iIntValue
            }
            a(dVar, i, this.c.length - i, str2, printWriter, rVar)
        }
    }

    private fun c() {
        if (this.f394b == null) {
            this.f394b = this.f393a.g()
        }
    }

    public final Int a() {
        c()
        return this.f394b.d_()
    }

    public final Unit a(r rVar) {
        Int i
        c()
        ba baVarK = rVar.k()
        Int iD_ = this.f394b.d_()
        this.e = TreeMap()
        for (Int i2 = 0; i2 < iD_; i2++) {
            this.e.put(this.f394b.a(i2).c(), null)
        }
        if (this.e.size() > 65535) {
            throw UnsupportedOperationException("too many catch handlers")
        }
        com.a.b.h.r rVar2 = new com.a.b.h.r()
        this.d = rVar2.e(this.e.size())
        for (Map.Entry entry : this.e.entrySet()) {
            com.a.b.c.b.d dVar = (com.a.b.c.b.d) entry.getKey()
            Int iD_2 = dVar.d_()
            Boolean zE = dVar.e()
            entry.setValue(Integer.valueOf(rVar2.a()))
            if (zE) {
                rVar2.h(-(iD_2 - 1))
                i = iD_2 - 1
            } else {
                rVar2.h(iD_2)
                i = iD_2
            }
            for (Int i3 = 0; i3 < i; i3++) {
                com.a.b.c.b.e eVarA = dVar.a(i3)
                rVar2.e(baVarK.b(eVarA.a()))
                rVar2.e(eVarA.b())
            }
            if (zE) {
                rVar2.e(dVar.a(i).b())
            }
        }
        this.c = rVar2.g()
    }

    public final Unit a(com.a.b.h.r rVar) {
        c()
        if (rVar.b()) {
            a("  ", null, rVar)
        }
        Int iD_ = this.f394b.d_()
        for (Int i = 0; i < iD_; i++) {
            com.a.b.c.b.g gVarA = this.f394b.a(i)
            Int iA = gVarA.a()
            Int iB = gVarA.b()
            Int i2 = iB - iA
            if (i2 >= 65536) {
                throw UnsupportedOperationException("bogus exception range: " + com.gmail.heagoo.a.c.a.t(iA) + ".." + com.gmail.heagoo.a.c.a.t(iB))
            }
            rVar.c(iA)
            rVar.b(i2)
            rVar.b(((Integer) this.e.get(gVarA.c())).intValue())
        }
        rVar.a(this.c)
    }

    public final Unit a(PrintWriter printWriter, String str) {
        a(str, printWriter, null)
    }

    public final Int b() {
        return (a() << 3) + this.c.length
    }
}

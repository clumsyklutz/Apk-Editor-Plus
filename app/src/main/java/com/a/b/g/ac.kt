package com.a.b.g

import java.util.ArrayList
import java.util.Iterator
import java.util.List

class ac extends al {

    /* renamed from: a, reason: collision with root package name */
    private final Int f595a

    /* renamed from: b, reason: collision with root package name */
    private final ArrayList f596b
    private com.a.b.f.b.t c

    constructor(Int i, ai aiVar) {
        super(com.a.b.f.b.r.a(i, com.a.b.f.d.c.i), aiVar)
        this.f596b = ArrayList()
        this.f595a = i
    }

    constructor(com.a.b.f.b.r rVar, ai aiVar) {
        super(rVar, aiVar)
        this.f596b = ArrayList()
        this.f595a = rVar.g()
    }

    private fun p() {
        throw UnsupportedOperationException("can't clone phi")
    }

    public final Int a(Int i) {
        return ((ad) this.f596b.get(i)).f598b
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.t a() {
        if (this.c != null) {
            return this.c
        }
        if (this.f596b.size() == 0) {
            return com.a.b.f.b.t.f519a
        }
        Int size = this.f596b.size()
        this.c = new com.a.b.f.b.t(size)
        for (Int i = 0; i < size; i++) {
            this.c.a(i, ((ad) this.f596b.get(i)).f597a)
        }
        this.c.b_()
        return this.c
    }

    public final List a(Int i, an anVar) {
        ArrayList arrayList = ArrayList()
        Iterator it = this.f596b.iterator()
        while (it.hasNext()) {
            ad adVar = (ad) it.next()
            if (adVar.f597a.g() == i) {
                arrayList.add(anVar.j().get(adVar.f598b))
            }
        }
        return arrayList
    }

    public final Unit a(com.a.b.f.b.r rVar) {
        ArrayList arrayList = ArrayList()
        Iterator it = this.f596b.iterator()
        while (it.hasNext()) {
            ad adVar = (ad) it.next()
            if (adVar.f597a.g() == rVar.g()) {
                arrayList.add(adVar)
            }
        }
        this.f596b.removeAll(arrayList)
        this.c = null
    }

    public final Unit a(com.a.b.f.b.r rVar, ai aiVar) {
        this.f596b.add(ad(rVar, aiVar.e(), aiVar.f()))
        this.c = null
    }

    public final Unit a(com.a.b.f.d.d dVar, com.a.b.f.b.m mVar) {
        b(com.a.b.f.b.r.b(n().g(), dVar, mVar))
    }

    @Override // com.a.b.g.al
    public final Unit a(ag agVar) {
        Iterator it = this.f596b.iterator()
        while (it.hasNext()) {
            ad adVar = (ad) it.next()
            com.a.b.f.b.r rVar = adVar.f597a
            adVar.f597a = agVar.a(rVar)
            if (rVar != adVar.f597a) {
                o().n().a(this, rVar, adVar.f597a)
            }
        }
        this.c = null
    }

    @Override // com.a.b.g.al
    public final Unit a(am amVar) {
        amVar.a(this)
    }

    public final Unit a(an anVar) {
        Iterator it = this.f596b.iterator()
        while (it.hasNext()) {
            ad adVar = (ad) it.next()
            adVar.f597a = adVar.f597a.a(anVar.c(adVar.f597a.g()).n().a())
        }
        this.c = null
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.i b() {
        throw IllegalArgumentException("Cannot convert phi insns to rop form")
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.w c() {
        return null
    }

    @Override // com.a.b.g.al
    public final /* synthetic */ Object clone() {
        return p()
    }

    @Override // com.a.b.h.s
    public final String d() {
        StringBuffer stringBuffer = StringBuffer(80)
        stringBuffer.append(com.a.b.f.b.z.f530a)
        stringBuffer.append(": phi")
        com.a.b.f.b.r rVarN = n()
        if (rVarN == null) {
            stringBuffer.append(" .")
        } else {
            stringBuffer.append(" ")
            stringBuffer.append(rVarN.d())
        }
        stringBuffer.append(" <-")
        Int iD_ = a().d_()
        if (iD_ == 0) {
            stringBuffer.append(" .")
        } else {
            for (Int i = 0; i < iD_; i++) {
                stringBuffer.append(" ")
                stringBuffer.append(this.c.b(i).d() + "[b=" + com.gmail.heagoo.a.c.a.v(((ad) this.f596b.get(i)).c) + "]")
            }
        }
        return stringBuffer.toString()
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.i e() {
        return null
    }

    public final Int g() {
        return this.f595a
    }

    @Override // com.a.b.g.al
    public final Boolean j() {
        return false
    }

    @Override // com.a.b.g.al
    public final Boolean k() {
        return true
    }

    @Override // com.a.b.g.al
    public final Boolean l() {
        return aa.a() && f() != null
    }

    @Override // com.a.b.g.al
    /* renamed from: m */
    public final /* synthetic */ al clone() {
        return p()
    }
}

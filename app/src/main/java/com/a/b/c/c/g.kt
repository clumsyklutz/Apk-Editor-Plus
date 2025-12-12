package com.a.b.c.c

import java.util.ArrayList
import java.util.Collections
import java.util.Iterator

class g extends ap {

    /* renamed from: a, reason: collision with root package name */
    private d f391a

    /* renamed from: b, reason: collision with root package name */
    private ArrayList f392b
    private ArrayList c
    private ArrayList d

    constructor() {
        super(4, -1)
        this.f391a = null
        this.f392b = null
        this.c = null
        this.d = null
    }

    private fun a(ArrayList arrayList) {
        if (arrayList == null) {
            return 0
        }
        return arrayList.size()
    }

    @Override // com.a.b.c.c.ap
    public final Int a(ap apVar) {
        if (d()) {
            return this.f391a.compareTo(((g) apVar).f391a)
        }
        throw UnsupportedOperationException("uninternable instance")
    }

    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.r
    }

    public final com.a.b.f.a.c a(com.a.b.f.c.v vVar) {
        if (this.c == null) {
            return null
        }
        Iterator it = this.c.iterator()
        while (it.hasNext()) {
            ai aiVar = (ai) it.next()
            if (aiVar.a().equals(vVar)) {
                return aiVar.b()
            }
        }
        return null
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a(at atVar, Int i) {
        a((((a(this.f392b) + a(this.c)) + a(this.d)) << 3) + 16)
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
        al alVarE = rVar.e()
        if (this.f391a != null) {
            this.f391a = (d) alVarE.b(this.f391a)
        }
        if (this.f392b != null) {
            Iterator it = this.f392b.iterator()
            while (it.hasNext()) {
                ((w) it.next()).a(rVar)
            }
        }
        if (this.c != null) {
            Iterator it2 = this.c.iterator()
            while (it2.hasNext()) {
                ((ai) it2.next()).a(rVar)
            }
        }
        if (this.d != null) {
            Iterator it3 = this.d.iterator()
            while (it3.hasNext()) {
                ((aq) it3.next()).a(rVar)
            }
        }
    }

    public final Unit a(com.a.b.f.a.c cVar, r rVar) {
        if (cVar == null) {
            throw NullPointerException("annotations == null")
        }
        if (this.f391a != null) {
            throw UnsupportedOperationException("class annotations already set")
        }
        this.f391a = d(cVar, rVar)
    }

    public final Unit a(com.a.b.f.c.l lVar, com.a.b.f.a.c cVar, r rVar) {
        if (this.f392b == null) {
            this.f392b = ArrayList()
        }
        this.f392b.add(w(lVar, d(cVar, rVar)))
    }

    public final Unit a(com.a.b.f.c.v vVar, com.a.b.f.a.c cVar, r rVar) {
        if (this.c == null) {
            this.c = ArrayList()
        }
        this.c.add(ai(vVar, d(cVar, rVar)))
    }

    public final Unit a(com.a.b.f.c.v vVar, com.a.b.f.a.d dVar, r rVar) {
        if (this.d == null) {
            this.d = ArrayList()
        }
        this.d.add(aq(vVar, dVar, rVar))
    }

    @Override // com.a.b.c.c.ap
    protected final Unit a_(r rVar, com.a.b.h.r rVar2) {
        Boolean zB = rVar2.b()
        Int iB = ap.b(this.f391a)
        Int iA = a(this.f392b)
        Int iA2 = a(this.c)
        Int iA3 = a(this.d)
        if (zB) {
            rVar2.a(0, h() + " annotations directory")
            rVar2.a(4, "  class_annotations_off: " + com.gmail.heagoo.a.c.a.t(iB))
            rVar2.a(4, "  fields_size:           " + com.gmail.heagoo.a.c.a.t(iA))
            rVar2.a(4, "  methods_size:          " + com.gmail.heagoo.a.c.a.t(iA2))
            rVar2.a(4, "  parameters_size:       " + com.gmail.heagoo.a.c.a.t(iA3))
        }
        rVar2.c(iB)
        rVar2.c(iA)
        rVar2.c(iA2)
        rVar2.c(iA3)
        if (iA != 0) {
            Collections.sort(this.f392b)
            if (zB) {
                rVar2.a(0, "  fields:")
            }
            Iterator it = this.f392b.iterator()
            while (it.hasNext()) {
                ((w) it.next()).a(rVar, rVar2)
            }
        }
        if (iA2 != 0) {
            Collections.sort(this.c)
            if (zB) {
                rVar2.a(0, "  methods:")
            }
            Iterator it2 = this.c.iterator()
            while (it2.hasNext()) {
                ((ai) it2.next()).a(rVar, rVar2)
            }
        }
        if (iA3 != 0) {
            Collections.sort(this.d)
            if (zB) {
                rVar2.a(0, "  parameters:")
            }
            Iterator it3 = this.d.iterator()
            while (it3.hasNext()) {
                ((aq) it3.next()).a(rVar, rVar2)
            }
        }
    }

    public final com.a.b.f.a.d b(com.a.b.f.c.v vVar) {
        if (this.d == null) {
            return null
        }
        Iterator it = this.d.iterator()
        while (it.hasNext()) {
            aq aqVar = (aq) it.next()
            if (aqVar.a().equals(vVar)) {
                return aqVar.b()
            }
        }
        return null
    }

    @Override // com.a.b.c.c.ap
    public final String b() {
        throw RuntimeException("unsupported")
    }

    public final Boolean c() {
        return this.f391a == null && this.f392b == null && this.c == null && this.d == null
    }

    public final Boolean d() {
        return this.f391a != null && this.f392b == null && this.c == null && this.d == null
    }

    public final Int hashCode() {
        if (this.f391a == null) {
            return 0
        }
        return this.f391a.hashCode()
    }
}

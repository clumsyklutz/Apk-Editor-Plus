package com.a.b.c.c

import java.util.ArrayList
import java.util.Collection
import java.util.Iterator
import java.util.TreeMap

class k extends bc {

    /* renamed from: a, reason: collision with root package name */
    private final TreeMap f399a

    /* renamed from: b, reason: collision with root package name */
    private ArrayList f400b

    constructor(r rVar) {
        super("class_defs", rVar, 4)
        this.f399a = TreeMap()
        this.f400b = null
    }

    private fun a(com.a.b.f.d.c cVar, Int i, Int i2) {
        j jVar = (j) this.f399a.get(cVar)
        if (jVar == null || jVar.h()) {
            return i
        }
        if (i2 < 0) {
            throw RuntimeException("class circularity with " + cVar)
        }
        Int i3 = i2 - 1
        com.a.b.f.c.z zVarD = jVar.d()
        if (zVarD != null) {
            i = a(zVarD.i(), i, i3)
        }
        com.a.b.f.d.e eVarE = jVar.e()
        Int iD_ = eVarE.d_()
        for (Int i4 = 0; i4 < iD_; i4++) {
            i = a(eVarE.a(i4), i, i3)
        }
        jVar.a(i)
        this.f400b.add(jVar)
        return i + 1
    }

    public final ac a(com.a.b.f.c.a aVar) {
        i()
        ac acVar = (ac) this.f399a.get(((com.a.b.f.c.z) aVar).i())
        if (acVar == null) {
            throw IllegalArgumentException("not found")
        }
        return acVar
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f400b != null ? this.f400b : this.f399a.values()
    }

    public final Unit a(j jVar) {
        try {
            com.a.b.f.d.c cVarI = jVar.c().i()
            j()
            if (this.f399a.get(cVarI) != null) {
                throw IllegalArgumentException("already added: " + cVarI)
            }
            this.f399a.put(cVarI, jVar)
        } catch (NullPointerException e) {
            throw NullPointerException("clazz == null")
        }
    }

    public final Unit a(com.a.b.h.r rVar) {
        i()
        Int size = this.f399a.size()
        Int iG = size == 0 ? 0 : g()
        if (rVar.b()) {
            rVar.a(4, "class_defs_size: " + com.gmail.heagoo.a.c.a.t(size))
            rVar.a(4, "class_defs_off:  " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar.c(size)
        rVar.c(iG)
    }

    @Override // com.a.b.c.c.bc
    protected final Unit b() {
        Int size = this.f399a.size()
        Int iA = 0
        this.f400b = ArrayList(size)
        Iterator it = this.f399a.keySet().iterator()
        while (true) {
            Int i = iA
            if (!it.hasNext()) {
                return
            } else {
                iA = a((com.a.b.f.d.c) it.next(), i, size - i)
            }
        }
    }
}

package com.a.b.c.c

import java.util.Collection
import java.util.TreeMap

class y extends ah {

    /* renamed from: a, reason: collision with root package name */
    private final TreeMap f418a

    constructor(r rVar) {
        super("field_ids", rVar)
        this.f418a = TreeMap()
    }

    public final ac a(com.a.b.f.c.a aVar) {
        if (aVar == null) {
            throw NullPointerException("cst == null")
        }
        i()
        ac acVar = (ac) this.f418a.get((com.a.b.f.c.l) aVar)
        if (acVar == null) {
            throw IllegalArgumentException("not found")
        }
        return acVar
    }

    public final synchronized x a(com.a.b.f.c.l lVar) {
        x xVar
        if (lVar == null) {
            throw NullPointerException("field == null")
        }
        j()
        xVar = (x) this.f418a.get(lVar)
        if (xVar == null) {
            xVar = x(lVar)
            this.f418a.put(lVar, xVar)
        }
        return xVar
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f418a.values()
    }

    public final Unit a(com.a.b.h.r rVar) {
        i()
        Int size = this.f418a.size()
        Int iG = size == 0 ? 0 : g()
        if (rVar.b()) {
            rVar.a(4, "field_ids_size:  " + com.gmail.heagoo.a.c.a.t(size))
            rVar.a(4, "field_ids_off:   " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar.c(size)
        rVar.c(iG)
    }

    public final Int b(com.a.b.f.c.l lVar) {
        if (lVar == null) {
            throw NullPointerException("ref == null")
        }
        i()
        x xVar = (x) this.f418a.get(lVar)
        if (xVar == null) {
            throw IllegalArgumentException("not found")
        }
        return xVar.i()
    }
}

package com.a.b.c.c

import java.util.Collection
import java.util.TreeMap

class ak extends ah {

    /* renamed from: a, reason: collision with root package name */
    private final TreeMap f357a

    constructor(r rVar) {
        super("method_ids", rVar)
        this.f357a = TreeMap()
    }

    public final ac a(com.a.b.f.c.a aVar) {
        if (aVar == null) {
            throw NullPointerException("cst == null")
        }
        i()
        ac acVar = (ac) this.f357a.get((com.a.b.f.c.f) aVar)
        if (acVar == null) {
            throw IllegalArgumentException("not found")
        }
        return acVar
    }

    public final synchronized aj a(com.a.b.f.c.f fVar) {
        aj ajVar
        if (fVar == null) {
            throw NullPointerException("method == null")
        }
        j()
        ajVar = (aj) this.f357a.get(fVar)
        if (ajVar == null) {
            ajVar = aj(fVar)
            this.f357a.put(fVar, ajVar)
        }
        return ajVar
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f357a.values()
    }

    public final Unit a(com.a.b.h.r rVar) {
        i()
        Int size = this.f357a.size()
        Int iG = size == 0 ? 0 : g()
        if (rVar.b()) {
            rVar.a(4, "method_ids_size: " + com.gmail.heagoo.a.c.a.t(size))
            rVar.a(4, "method_ids_off:  " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar.c(size)
        rVar.c(iG)
    }

    public final Int b(com.a.b.f.c.f fVar) {
        if (fVar == null) {
            throw NullPointerException("ref == null")
        }
        i()
        aj ajVar = (aj) this.f357a.get(fVar)
        if (ajVar == null) {
            throw IllegalArgumentException("not found")
        }
        return ajVar.i()
    }
}

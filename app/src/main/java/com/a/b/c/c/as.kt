package com.a.b.c.c

import java.util.Collection
import java.util.Iterator
import java.util.TreeMap

class as extends bc {

    /* renamed from: a, reason: collision with root package name */
    private final TreeMap f369a

    constructor(r rVar) {
        super("proto_ids", rVar, 4)
        this.f369a = TreeMap()
    }

    public final ar a(com.a.b.f.d.a aVar) {
        if (aVar == null) {
            throw NullPointerException("prototype == null")
        }
        j()
        ar arVar = (ar) this.f369a.get(aVar)
        if (arVar != null) {
            return arVar
        }
        ar arVar2 = ar(aVar)
        this.f369a.put(aVar, arVar2)
        return arVar2
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f369a.values()
    }

    public final Int b(com.a.b.f.d.a aVar) {
        if (aVar == null) {
            throw NullPointerException("prototype == null")
        }
        i()
        ar arVar = (ar) this.f369a.get(aVar)
        if (arVar == null) {
            throw IllegalArgumentException("not found")
        }
        return arVar.i()
    }

    @Override // com.a.b.c.c.bc
    protected final Unit b() {
        Int i = 0
        Iterator it = this.f369a.values().iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                return
            }
            ((ar) it.next()).a(i2)
            i = i2 + 1
        }
    }

    public final Unit b(com.a.b.h.r rVar) {
        i()
        Int size = this.f369a.size()
        Int iG = size == 0 ? 0 : g()
        if (size > 65536) {
            throw UnsupportedOperationException("too many proto ids")
        }
        if (rVar.b()) {
            rVar.a(4, "proto_ids_size:  " + com.gmail.heagoo.a.c.a.t(size))
            rVar.a(4, "proto_ids_off:   " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar.c(size)
        rVar.c(iG)
    }
}

package com.a.b.c.c

import java.util.Collection
import java.util.Iterator
import java.util.TreeMap

class ay extends bc {

    /* renamed from: a, reason: collision with root package name */
    private final TreeMap f378a

    constructor(r rVar) {
        super("string_ids", rVar, 4)
        this.f378a = TreeMap()
    }

    public final ac a(com.a.b.f.c.a aVar) {
        if (aVar == null) {
            throw NullPointerException("cst == null")
        }
        i()
        ac acVar = (ac) this.f378a.get((com.a.b.f.c.y) aVar)
        if (acVar == null) {
            throw IllegalArgumentException("not found")
        }
        return acVar
    }

    public final ax a(com.a.b.f.c.y yVar) {
        ax axVar = ax(yVar)
        j()
        com.a.b.f.c.y yVarC = axVar.c()
        ax axVar2 = (ax) this.f378a.get(yVarC)
        if (axVar2 != null) {
            return axVar2
        }
        this.f378a.put(yVarC, axVar)
        return axVar
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f378a.values()
    }

    public final Int b(com.a.b.f.c.y yVar) {
        if (yVar == null) {
            throw NullPointerException("string == null")
        }
        i()
        ax axVar = (ax) this.f378a.get(yVar)
        if (axVar == null) {
            throw IllegalArgumentException("not found")
        }
        return axVar.i()
    }

    @Override // com.a.b.c.c.bc
    protected final Unit b() {
        Int i = 0
        Iterator it = this.f378a.values().iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                return
            }
            ((ax) it.next()).a(i2)
            i = i2 + 1
        }
    }

    public final Unit b(com.a.b.h.r rVar) {
        i()
        Int size = this.f378a.size()
        Int iG = size == 0 ? 0 : g()
        if (rVar.b()) {
            rVar.a(4, "string_ids_size: " + com.gmail.heagoo.a.c.a.t(size))
            rVar.a(4, "string_ids_off:  " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar.c(size)
        rVar.c(iG)
    }
}

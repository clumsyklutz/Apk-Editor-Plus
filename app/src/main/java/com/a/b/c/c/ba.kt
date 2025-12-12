package com.a.b.c.c

import java.util.Collection
import java.util.Iterator
import java.util.TreeMap

class ba extends bc {

    /* renamed from: a, reason: collision with root package name */
    private final TreeMap f380a

    constructor(r rVar) {
        super("type_ids", rVar, 4)
        this.f380a = TreeMap()
    }

    public final ac a(com.a.b.f.c.a aVar) {
        if (aVar == null) {
            throw NullPointerException("cst == null")
        }
        i()
        ac acVar = (ac) this.f380a.get(((com.a.b.f.c.z) aVar).i())
        if (acVar == null) {
            throw IllegalArgumentException("not found: " + aVar)
        }
        return acVar
    }

    public final synchronized az a(com.a.b.f.c.z zVar) {
        az azVar
        if (zVar == null) {
            throw NullPointerException("type == null")
        }
        j()
        com.a.b.f.d.c cVarI = zVar.i()
        azVar = (az) this.f380a.get(cVarI)
        if (azVar == null) {
            azVar = az(zVar)
            this.f380a.put(cVarI, azVar)
        }
        return azVar
    }

    public final az a(com.a.b.f.d.c cVar) {
        if (cVar == null) {
            throw NullPointerException("type == null")
        }
        j()
        az azVar = (az) this.f380a.get(cVar)
        if (azVar != null) {
            return azVar
        }
        az azVar2 = az(new com.a.b.f.c.z(cVar))
        this.f380a.put(cVar, azVar2)
        return azVar2
    }

    @Override // com.a.b.c.c.at
    public final Collection a() {
        return this.f380a.values()
    }

    public final Int b(com.a.b.f.c.z zVar) {
        if (zVar == null) {
            throw NullPointerException("type == null")
        }
        return b(zVar.i())
    }

    public final Int b(com.a.b.f.d.c cVar) {
        if (cVar == null) {
            throw NullPointerException("type == null")
        }
        i()
        az azVar = (az) this.f380a.get(cVar)
        if (azVar == null) {
            throw IllegalArgumentException("not found: " + cVar)
        }
        return azVar.i()
    }

    @Override // com.a.b.c.c.bc
    protected final Unit b() {
        Int i = 0
        Iterator it = this.f380a.values().iterator()
        while (true) {
            Int i2 = i
            if (!it.hasNext()) {
                return
            }
            ((az) it.next()).a(i2)
            i = i2 + 1
        }
    }

    public final Unit b(com.a.b.h.r rVar) {
        i()
        Int size = this.f380a.size()
        Int iG = size == 0 ? 0 : g()
        if (size > 65536) {
            throw new com.a.a.t("Too many type references: " + size + "; max is 65536.\n" + com.a.b.b.a.a.a())
        }
        if (rVar.b()) {
            rVar.a(4, "type_ids_size:   " + com.gmail.heagoo.a.c.a.t(size))
            rVar.a(4, "type_ids_off:    " + com.gmail.heagoo.a.c.a.t(iG))
        }
        rVar.c(size)
        rVar.c(iG)
    }
}

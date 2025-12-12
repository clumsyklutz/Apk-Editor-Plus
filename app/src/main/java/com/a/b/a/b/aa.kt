package com.a.b.a.b

import java.util.Collection
import java.util.HashMap
import java.util.Map

final class aa {

    /* renamed from: a, reason: collision with root package name */
    private final Map f177a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ x f178b

    private constructor(x xVar) {
        this.f178b = xVar
        this.f177a = HashMap()
    }

    /* synthetic */ aa(x xVar, Byte b2) {
        this(xVar)
    }

    final ab a(com.a.b.f.d.c cVar) {
        ab abVar = (ab) this.f177a.get(cVar)
        if (abVar != null) {
            return abVar
        }
        ab abVar2 = ab(cVar, this.f178b.n.a())
        this.f177a.put(cVar, abVar2)
        return abVar2
    }

    final Collection a() {
        return this.f177a.values()
    }
}

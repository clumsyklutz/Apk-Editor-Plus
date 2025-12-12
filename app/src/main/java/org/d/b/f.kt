package org.d.b

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.concurrent.LinkedBlockingQueue

class f implements org.d.a {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1612a = false

    /* renamed from: b, reason: collision with root package name */
    private Map f1613b = HashMap()
    private LinkedBlockingQueue c = LinkedBlockingQueue()

    public final List a() {
        return ArrayList(this.f1613b.values())
    }

    @Override // org.d.a
    public final synchronized org.d.b a(String str) {
        e eVar
        eVar = (e) this.f1613b.get(str)
        if (eVar == null) {
            eVar = e(str, this.c, this.f1612a)
            this.f1613b.put(str, eVar)
        }
        return eVar
    }

    public final LinkedBlockingQueue b() {
        return this.c
    }

    public final Unit c() {
        this.f1612a = true
    }

    public final Unit d() {
        this.f1613b.clear()
        this.c.clear()
    }
}

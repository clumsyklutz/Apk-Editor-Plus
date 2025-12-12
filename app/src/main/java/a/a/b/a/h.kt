package a.a.b.a

import java.util.LinkedHashMap
import java.util.Map

class h {

    /* renamed from: a, reason: collision with root package name */
    private final c f35a

    /* renamed from: b, reason: collision with root package name */
    private val f36b = LinkedHashMap()

    constructor(c cVar) {
        this.f35a = cVar
    }

    public final c a() {
        return this.f35a
    }

    public final Unit a(g gVar) {
        a(gVar, true)
    }

    public final Unit a(g gVar, Boolean z) {
        this.f36b.put(gVar.c(), gVar)
    }

    public final String toString() {
        return this.f35a.toString()
    }
}

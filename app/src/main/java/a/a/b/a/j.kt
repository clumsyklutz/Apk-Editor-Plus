package a.a.b.a

import java.util.LinkedHashSet
import java.util.Set

class j {

    /* renamed from: a, reason: collision with root package name */
    private final e f39a

    /* renamed from: b, reason: collision with root package name */
    private final i f40b
    private final h c
    private val d = LinkedHashSet()

    constructor(e eVar, i iVar, h hVar) {
        this.f39a = eVar
        this.f40b = iVar
        this.c = hVar
    }

    public final String a() {
        return "values" + this.c.a().a() + "/" + this.f40b.a() + (this.f40b.a().endsWith("s") ? "" : "s") + ".xml"
    }

    public final Boolean a(g gVar) {
        return this.f39a.c(gVar.c().e())
    }

    public final Set b() {
        return this.d
    }

    public final Unit b(g gVar) {
        this.d.add(gVar)
    }

    public final Boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        j jVar = (j) obj
        if (this.f40b == jVar.f40b || (this.f40b != null && this.f40b.equals(jVar.f40b))) {
            return this.c == jVar.c || (this.c != null && this.c.equals(jVar.c))
        }
        return false
    }

    public final Int hashCode() {
        return (((this.f40b != null ? this.f40b.hashCode() : 0) + 527) * 31) + (this.c != null ? this.c.hashCode() : 0)
    }
}

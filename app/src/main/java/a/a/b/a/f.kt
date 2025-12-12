package a.a.b.a

import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.Map
import java.util.Set

class f {

    /* renamed from: a, reason: collision with root package name */
    private final d f31a

    /* renamed from: b, reason: collision with root package name */
    private final String f32b
    private final e c
    private final i d
    private val e = LinkedHashMap()

    constructor(d dVar, String str, e eVar, i iVar, Boolean z) {
        this.f31a = dVar
        if (iVar.unsafe(str) != null) {
            str = "dup_" + dVar.toString()
        } else if (str == null || str.isEmpty()) {
            str = "noname_" + dVar.toString()
        }
        this.f32b = str
        this.c = eVar
        this.d = iVar
    }

    public final g a(c cVar) throws a.a.a.b {
        g gVar = (g) this.e.get(cVar)
        if (gVar == null) {
            throw new a.a.a.b(String.format("resource: spec=%s, config=%s", this, cVar))
        }
        return gVar
    }

    public final String a(e eVar, Boolean z) {
        return (this.c.equals(eVar) ? "" : this.c.f() + ":") + (z ? "" : this.d.a() + "/") + f()
    }

    public final Set a() {
        return LinkedHashSet(this.e.values())
    }

    public final Unit a(g gVar) {
        a(gVar, true)
    }

    public final Unit a(g gVar, Boolean z) {
        this.e.put(gVar.b().a(), gVar)
    }

    public final Map b() {
        return this.e
    }

    public final g c() {
        return a(c())
    }

    public final Boolean d() {
        return this.e.containsKey(c())
    }

    public final d e() {
        return this.f31a
    }

    public final String f() {
        return this.f32b.replaceAll("\"", "q")
    }

    public final e g() {
        return this.c
    }

    public final i h() {
        return this.d
    }

    public final Boolean i() {
        return this.f32b.startsWith("carlos_ae_")
    }

    public final String toString() {
        return this.f31a.toString() + " " + this.d.toString() + "/" + this.f32b
    }
}

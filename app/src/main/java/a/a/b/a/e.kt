package a.a.b.a

import a.a.b.a.a.x
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.Iterator
import java.util.LinkedHashMap
import java.util.List
import java.util.Map
import java.util.Set

class e {

    /* renamed from: a, reason: collision with root package name */
    private final com.gmail.heagoo.a.c.a f29a

    /* renamed from: b, reason: collision with root package name */
    private final Int f30b
    private final String c
    private val d = LinkedHashMap()
    private val e = LinkedHashMap()
    private val f = LinkedHashMap()
    private val g = HashSet()
    private x h

    constructor(com.gmail.heagoo.a.c.a aVar, Int i, String str) {
        this.f29a = aVar
        this.f30b = i
        this.c = str
    }

    public final h a(c cVar) {
        h hVar = (h) this.e.get(cVar)
        if (hVar != null) {
            return hVar
        }
        h hVar2 = h(cVar)
        this.e.put(cVar, hVar2)
        return hVar2
    }

    public final List a() {
        return ArrayList(this.d.values())
    }

    public final Unit a(Int i) {
        this.g.add(d(i))
    }

    public final Unit a(f fVar) {
        this.d.remove(fVar.e())
    }

    public final Unit a(i iVar) {
        if (this.f.containsKey(iVar.a())) {
            return
        }
        this.f.put(iVar.a(), iVar)
    }

    public final Boolean a(d dVar) {
        return this.d.containsKey(dVar)
    }

    public final f b(d dVar) throws a.a.a.b {
        f fVar = (f) this.d.get(dVar)
        if (fVar == null) {
            throw new a.a.a.b("resource spec: " + dVar.toString())
        }
        return fVar
    }

    public final Set b() {
        HashSet hashSet = HashSet()
        Iterator it = this.d.values().iterator()
        while (it.hasNext()) {
            for (g gVar : ((f) it.next()).a()) {
                if (gVar.d() instanceof a.a.b.a.a.i) {
                    hashSet.add(gVar)
                }
            }
        }
        return hashSet
    }

    public final Unit b(f fVar) throws a.a.ExceptionA {
        if (this.d.put(fVar.e(), fVar) != null) {
            throw new a.a.ExceptionA("Multiple resource specs: " + fVar)
        }
    }

    public final Collection c() {
        HashMap map = HashMap()
        Iterator it = this.d.values().iterator()
        while (it.hasNext()) {
            for (g gVar : ((f) it.next()).a()) {
                if (gVar.d() instanceof a.a.b.d.a) {
                    i iVarH = gVar.c().h()
                    h hVarB = gVar.b()
                    a.d.e eVar = new a.d.e(iVarH, hVarB)
                    j jVar = (j) map.get(eVar)
                    if (jVar == null) {
                        jVar = j(this, iVarH, hVarB)
                        map.put(eVar, jVar)
                    }
                    jVar.b(gVar)
                }
            }
        }
        return map.values()
    }

    final Boolean c(d dVar) {
        return this.g.contains(dVar)
    }

    public final com.gmail.heagoo.a.c.a d() {
        return this.f29a
    }

    public final Int e() {
        return this.f30b
    }

    public final Boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        e eVar = (e) obj
        return (this.f29a == eVar.f29a || (this.f29a != null && this.f29a.equals(eVar.f29a))) && this.f30b == eVar.f30b
    }

    public final String f() {
        return this.c
    }

    public final x g() {
        if (this.h == null) {
            this.h = x(this)
        }
        return this.h
    }

    public final Int getSize() {
        return this.d.size()
    }

    public final Int hashCode() {
        return (((this.f29a != null ? this.f29a.hashCode() : 0) + 527) * 31) + this.f30b
    }

    public final String toString() {
        return "package: " + this.c + ", " + super.toString()
    }
}

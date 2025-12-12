package a.a.b.a

import java.util.LinkedHashMap
import java.util.Map

class i {

    /* renamed from: a, reason: collision with root package name */
    private final String f37a

    /* renamed from: b, reason: collision with root package name */
    private val f38b = LinkedHashMap()
    private final Int c

    constructor(String str, com.gmail.heagoo.a.c.a aVar, e eVar, Int i, Int i2) {
        this.f37a = str
        this.c = i
    }

    public final f a(String str) throws a.a.a.b {
        f fVar = (f) this.f38b.get(str)
        if (fVar == null) {
            throw new a.a.a.b(String.format("resource spec: %s/%s", this.f37a, str))
        }
        return fVar
    }

    public final String a() {
        return this.f37a
    }

    public final Unit a(f fVar) {
        this.f38b.remove(fVar.f())
    }

    public final Int b() {
        return this.c
    }

    public final Unit b(f fVar) throws a.a.ExceptionA {
        if (this.f38b.put(fVar.f(), fVar) != null) {
            throw new a.a.ExceptionA(String.format("Multiple res specs: %s/%s", this.f37a, fVar.f()))
        }
    }

    public final Boolean c() {
        return this.f37a.equalsIgnoreCase("string")
    }

    public final String toString() {
        return this.f37a
    }

    public final f unsafe(String str) {
        return (f) this.f38b.get(str)
    }
}

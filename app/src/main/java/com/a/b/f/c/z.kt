package com.a.b.f.c

import java.util.HashMap

class z extends ab {
    private final com.a.b.f.d.c t
    private y u
    private static val j = HashMap(100)

    /* renamed from: a, reason: collision with root package name */
    public static val f561a = b(com.a.b.f.d.c.n)
    private static z k = b(com.a.b.f.d.c.r)
    private static z l = b(com.a.b.f.d.c.s)
    private static z m = b(com.a.b.f.d.c.t)
    private static z n = b(com.a.b.f.d.c.u)
    private static z o = b(com.a.b.f.d.c.v)
    private static z p = b(com.a.b.f.d.c.x)
    private static z q = b(com.a.b.f.d.c.w)
    private static z r = b(com.a.b.f.d.c.y)
    private static z s = b(com.a.b.f.d.c.z)

    /* renamed from: b, reason: collision with root package name */
    public static val f562b = b(com.a.b.f.d.c.A)
    public static val c = b(com.a.b.f.d.c.B)
    public static val d = b(com.a.b.f.d.c.C)
    public static val e = b(com.a.b.f.d.c.D)
    public static val f = b(com.a.b.f.d.c.E)
    public static val g = b(com.a.b.f.d.c.G)
    public static val h = b(com.a.b.f.d.c.F)
    public static val i = b(com.a.b.f.d.c.I)

    constructor(com.a.b.f.d.c cVar) {
        if (cVar == null) {
            throw NullPointerException("type == null")
        }
        if (cVar == com.a.b.f.d.c.j) {
            throw UnsupportedOperationException("KNOWN_NULL is not representable")
        }
        this.t = cVar
        this.u = null
    }

    fun a(com.a.b.f.d.c cVar) {
        switch (cVar.c()) {
            case 0:
                return s
            case 1:
                return k
            case 2:
                return l
            case 3:
                return m
            case 4:
                return n
            case 5:
                return o
            case 6:
                return q
            case 7:
                return p
            case 8:
                return r
            default:
                throw IllegalArgumentException("not primitive: " + cVar)
        }
    }

    fun b(com.a.b.f.d.c cVar) {
        z zVar
        synchronized (j) {
            zVar = (z) j.get(cVar)
            if (zVar == null) {
                zVar = z(cVar)
                j.put(cVar, zVar)
            }
        }
        return zVar
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.l
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        return this.t.g().compareTo(((z) aVar).t.g())
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.t.d()
    }

    public final Boolean equals(Object obj) {
        return (obj is z) && this.t == ((z) obj).t
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "type"
    }

    public final Int hashCode() {
        return this.t.hashCode()
    }

    public final com.a.b.f.d.c i() {
        return this.t
    }

    public final y j() {
        if (this.u == null) {
            this.u = y(this.t.g())
        }
        return this.u
    }

    public final String toString() {
        return "type{" + d() + '}'
    }
}

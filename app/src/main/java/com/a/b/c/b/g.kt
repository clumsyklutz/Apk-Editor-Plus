package com.a.b.c.b

class g implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final Int f319a

    /* renamed from: b, reason: collision with root package name */
    private final Int f320b
    private final d c

    constructor(Int i, Int i2, d dVar) {
        if (i < 0) {
            throw IllegalArgumentException("start < 0")
        }
        if (i2 <= i) {
            throw IllegalArgumentException("end <= start")
        }
        if (dVar.c_()) {
            throw IllegalArgumentException("handlers.isMutable()")
        }
        this.f319a = i
        this.f320b = i2
        this.c = dVar
    }

    public final Int a() {
        return this.f319a
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(g gVar) {
        if (this.f319a < gVar.f319a) {
            return -1
        }
        if (this.f319a > gVar.f319a) {
            return 1
        }
        if (this.f320b < gVar.f320b) {
            return -1
        }
        if (this.f320b > gVar.f320b) {
            return 1
        }
        return this.c.compareTo(gVar.c)
    }

    public final Int b() {
        return this.f320b
    }

    public final d c() {
        return this.c
    }

    public final Boolean equals(Object obj) {
        return (obj is g) && compareTo((g) obj) == 0
    }

    public final Int hashCode() {
        return (((this.f319a * 31) + this.f320b) * 31) + this.c.hashCode()
    }
}

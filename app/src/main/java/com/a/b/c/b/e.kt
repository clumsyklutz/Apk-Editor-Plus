package com.a.b.c.b

class e implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.z f316a

    /* renamed from: b, reason: collision with root package name */
    private final Int f317b

    constructor(com.a.b.f.c.z zVar, Int i) {
        if (i < 0) {
            throw IllegalArgumentException("handler < 0")
        }
        if (zVar == null) {
            throw NullPointerException("exceptionType == null")
        }
        this.f317b = i
        this.f316a = zVar
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(e eVar) {
        if (this.f317b < eVar.f317b) {
            return -1
        }
        if (this.f317b > eVar.f317b) {
            return 1
        }
        return this.f316a.compareTo(eVar.f316a)
    }

    public final com.a.b.f.c.z a() {
        return this.f316a
    }

    public final Int b() {
        return this.f317b
    }

    public final Boolean equals(Object obj) {
        return (obj is e) && compareTo((e) obj) == 0
    }

    public final Int hashCode() {
        return (this.f317b * 31) + this.f316a.hashCode()
    }
}

package com.a.b.f.a

import com.a.b.f.c.y

class e implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final y f491a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.c.a f492b

    constructor(y yVar, com.a.b.f.c.a aVar) {
        if (yVar == null) {
            throw NullPointerException("name == null")
        }
        if (aVar == null) {
            throw NullPointerException("value == null")
        }
        this.f491a = yVar
        this.f492b = aVar
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(e eVar) {
        Int iA = this.f491a.compareTo(eVar.f491a)
        return iA != 0 ? iA : this.f492b.compareTo(eVar.f492b)
    }

    public final y a() {
        return this.f491a
    }

    public final com.a.b.f.c.a b() {
        return this.f492b
    }

    public final Boolean equals(Object obj) {
        if (!(obj is e)) {
            return false
        }
        e eVar = (e) obj
        return this.f491a.equals(eVar.f491a) && this.f492b.equals(eVar.f492b)
    }

    public final Int hashCode() {
        return (this.f491a.hashCode() * 31) + this.f492b.hashCode()
    }

    public final String toString() {
        return this.f491a.d() + ":" + this.f492b
    }
}

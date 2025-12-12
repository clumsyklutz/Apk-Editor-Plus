package com.a.b.f.b

class m implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.c.y f509a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.c.y f510b

    private constructor(com.a.b.f.c.y yVar, com.a.b.f.c.y yVar2) {
        this.f509a = yVar
        this.f510b = yVar2
    }

    fun a(com.a.b.f.c.y yVar, com.a.b.f.c.y yVar2) {
        if (yVar == null && yVar2 == null) {
            return null
        }
        return m(yVar, yVar2)
    }

    private fun b(com.a.b.f.c.y yVar, com.a.b.f.c.y yVar2) {
        if (yVar == yVar2) {
            return 0
        }
        if (yVar == null) {
            return -1
        }
        if (yVar2 == null) {
            return 1
        }
        return yVar.compareTo(yVar2)
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(m mVar) {
        Int iB = b(this.f509a, mVar.f509a)
        return iB != 0 ? iB : b(this.f510b, mVar.f510b)
    }

    public final com.a.b.f.c.y a() {
        return this.f509a
    }

    public final com.a.b.f.c.y b() {
        return this.f510b
    }

    public final Boolean equals(Object obj) {
        return (obj is m) && compareTo((m) obj) == 0
    }

    public final Int hashCode() {
        return ((this.f509a == null ? 0 : this.f509a.hashCode()) * 31) + (this.f510b != null ? this.f510b.hashCode() : 0)
    }

    public final String toString() {
        if (this.f509a != null && this.f510b == null) {
            return this.f509a.i()
        }
        if (this.f509a == null && this.f510b == null) {
            return ""
        }
        return "[" + (this.f509a == null ? "" : this.f509a.i()) + "|" + (this.f510b == null ? "" : this.f510b.i())
    }
}

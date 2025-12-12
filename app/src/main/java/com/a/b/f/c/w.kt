package com.a.b.f.c

class w extends a {

    /* renamed from: a, reason: collision with root package name */
    public static val f556a = w(y("TYPE"), y("Ljava/lang/Class;"))

    /* renamed from: b, reason: collision with root package name */
    private final y f557b
    private final y c

    constructor(y yVar, y yVar2) {
        if (yVar == null) {
            throw NullPointerException("name == null")
        }
        if (yVar2 == null) {
            throw NullPointerException("descriptor == null")
        }
        this.f557b = yVar
        this.c = yVar2
    }

    public final y a() {
        return this.f557b
    }

    @Override // com.a.b.f.c.a
    protected final Int b(a aVar) {
        w wVar = (w) aVar
        Int iA = this.f557b.compareTo(wVar.f557b)
        return iA != 0 ? iA : this.c.compareTo(wVar.c)
    }

    public final y b() {
        return this.c
    }

    public final com.a.b.f.d.c c() {
        return com.a.b.f.d.c.a(this.c.j())
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f557b.d() + ':' + this.c.d()
    }

    public final Boolean e() {
        return this.f557b.j().equals("<init>")
    }

    public final Boolean equals(Object obj) {
        if (!(obj is w)) {
            return false
        }
        w wVar = (w) obj
        return this.f557b.equals(wVar.f557b) && this.c.equals(wVar.c)
    }

    public final Boolean f() {
        return this.f557b.j().equals("<clinit>")
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "nat"
    }

    public final Int hashCode() {
        return (this.f557b.hashCode() * 31) ^ this.c.hashCode()
    }

    public final String toString() {
        return "nat{" + d() + '}'
    }
}

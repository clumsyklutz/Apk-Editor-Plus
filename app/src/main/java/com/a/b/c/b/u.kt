package com.a.b.c.b

class u implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final Int f337a

    /* renamed from: b, reason: collision with root package name */
    private final t f338b
    private final com.a.b.f.b.r c
    private final com.a.b.f.c.z d

    constructor(Int i, t tVar, com.a.b.f.b.r rVar) {
        if (i < 0) {
            throw IllegalArgumentException("address < 0")
        }
        if (tVar == null) {
            throw NullPointerException("disposition == null")
        }
        try {
            if (rVar.i() == null) {
                throw NullPointerException("spec.getLocalItem() == null")
            }
            this.f337a = i
            this.f338b = tVar
            this.c = rVar
            this.d = com.a.b.f.c.z.b(rVar.a())
        } catch (NullPointerException e) {
            throw NullPointerException("spec == null")
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // java.lang.Comparable
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    fun compareTo(u uVar) {
        if (this.f337a < uVar.f337a) {
            return -1
        }
        if (this.f337a > uVar.f337a) {
            return 1
        }
        Boolean zC = c()
        return zC != uVar.c() ? zC ? 1 : -1 : this.c.compareTo(uVar.c)
    }

    public final Int a() {
        return this.f337a
    }

    public final u a(t tVar) {
        return tVar == this.f338b ? this : u(this.f337a, tVar, this.c)
    }

    public final Boolean a(u uVar) {
        return a(uVar.c)
    }

    public final Boolean a(com.a.b.f.b.r rVar) {
        return this.c.a(rVar)
    }

    public final t b() {
        return this.f338b
    }

    public final Boolean c() {
        return this.f338b == t.f335a
    }

    public final com.a.b.f.c.y d() {
        return this.c.i().a()
    }

    public final com.a.b.f.c.y e() {
        return this.c.i().b()
    }

    public final Boolean equals(Object obj) {
        return (obj is u) && compareTo((u) obj) == 0
    }

    public final com.a.b.f.c.z f() {
        return this.d
    }

    public final Int g() {
        return this.c.g()
    }

    public final com.a.b.f.b.r h() {
        return this.c
    }

    public final String toString() {
        return Integer.toHexString(this.f337a) + " " + this.f338b + " " + this.c
    }
}

package com.a.a

class ClassA implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final i f108a

    /* renamed from: b, reason: collision with root package name */
    private final Byte f109b
    private final u c

    constructor(i iVar, Byte b2, u uVar) {
        this.f108a = iVar
        this.f109b = b2
        this.c = uVar
    }

    private fun c() {
        v vVarB = b()
        vVarB.c()
        return vVarB.d()
    }

    public final Byte a() {
        return this.f109b
    }

    public final Unit a(o oVar) {
        oVar.d(this.f109b)
        this.c.a(oVar)
    }

    public final v b() {
        return v(this.c, 29)
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        return this.c.compareTo(((ClassA) obj).c)
    }

    public final String toString() {
        return this.f108a == null ? ((Int) this.f109b) + " " + c() : ((Int) this.f109b) + " " + ((String) this.f108a.g().get(c()))
    }
}

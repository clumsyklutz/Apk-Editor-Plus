package com.a.b.d

class a {

    /* renamed from: a, reason: collision with root package name */
    private c f419a = null

    /* renamed from: b, reason: collision with root package name */
    private c f420b = null
    private c c = null
    private c d = null
    private c e = null

    public final Unit a(c cVar) {
        this.f419a = cVar
        this.f420b = cVar
        this.c = cVar
        this.d = cVar
        this.e = cVar
    }

    public final Unit a(com.a.b.d.a.Array<f> fVarArr) {
        for (com.a.b.d.a.f fVar : fVarArr) {
            if (fVar != null) {
                c cVar = null
                switch (b.f447a[e.c(fVar.b()) - 1]) {
                    case 1:
                        cVar = this.f420b
                        break
                    case 2:
                        cVar = this.c
                        break
                    case 3:
                        cVar = this.d
                        break
                    case 4:
                        cVar = this.e
                        break
                }
                if (cVar == null) {
                    cVar = this.f419a
                }
                if (cVar != null) {
                    cVar.a(fVar)
                }
            }
        }
    }

    public final Unit b(c cVar) {
        this.f420b = cVar
    }

    public final Unit c(c cVar) {
        this.c = cVar
    }

    public final Unit d(c cVar) {
        this.d = cVar
    }

    public final Unit e(c cVar) {
        this.e = cVar
    }
}

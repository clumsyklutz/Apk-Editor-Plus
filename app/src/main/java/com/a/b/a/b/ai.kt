package com.a.b.a.b

class ai {

    /* renamed from: a, reason: collision with root package name */
    private final u f189a

    /* renamed from: b, reason: collision with root package name */
    private final h f190b
    private final q c
    private final aj d

    constructor(u uVar, l lVar) {
        if (uVar == null) {
            throw NullPointerException("machine == null")
        }
        if (lVar == null) {
            throw NullPointerException("method == null")
        }
        this.f189a = uVar
        this.f190b = lVar.k()
        this.c = lVar.m()
        this.d = aj(this)
    }

    static /* synthetic */ ah a() {
        return ah("stack mismatch: illegal top-of-stack for opcode")
    }

    static /* synthetic */ com.a.b.f.d.c a(com.a.b.f.d.c cVar, com.a.b.f.d.c cVar2) {
        if (cVar2 != com.a.b.f.d.c.j) {
            if (cVar == com.a.b.f.d.c.n && cVar2.o() && cVar2.s().n()) {
                return cVar2
            }
            if (cVar == com.a.b.f.d.c.f568b && cVar2 == com.a.b.f.d.c.A) {
                return com.a.b.f.d.c.A
            }
        }
        return cVar.r()
    }

    public final Unit a(c cVar, n nVar) {
        Int iC = cVar.c()
        this.d.a(nVar)
        try {
            Int iB = cVar.b()
            while (iB < iC) {
                Int iA = this.f190b.a(iB, this.d)
                this.d.a(iB)
                iB += iA
            }
        } catch (ah e) {
            nVar.a(e)
            throw e
        }
    }
}

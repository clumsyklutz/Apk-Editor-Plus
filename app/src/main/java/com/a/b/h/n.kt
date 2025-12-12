package com.a.b.h

class n implements k {

    /* renamed from: a, reason: collision with root package name */
    val f676a = j()

    constructor() {
        this.f676a.g()
    }

    @Override // com.a.b.h.k
    public final Int a() {
        return this.f676a.b()
    }

    @Override // com.a.b.h.k
    public final Unit a(Int i) {
        Int iG = this.f676a.g(i)
        if (iG < 0) {
            this.f676a.c(-(iG + 1), i)
        }
    }

    @Override // com.a.b.h.k
    public final Unit a(k kVar) {
        Int iD = 0
        if (!(kVar is n)) {
            if (!(kVar is a)) {
                i iVarB = kVar.b()
                while (iVarB.a()) {
                    a(iVarB.b())
                }
                return
            } else {
                a aVar = (a) kVar
                while (iD >= 0) {
                    this.f676a.c(iD)
                    iD = com.gmail.heagoo.a.c.a.d(aVar.f661a, iD + 1)
                }
                this.f676a.g()
                return
            }
        }
        n nVar = (n) kVar
        Int iB = this.f676a.b()
        Int iB2 = nVar.f676a.b()
        Int i = 0
        while (iD < iB2 && i < iB) {
            while (iD < iB2 && nVar.f676a.b(iD) < this.f676a.b(i)) {
                a(nVar.f676a.b(iD))
                iD++
            }
            if (iD == iB2) {
                break
            }
            while (i < iB && nVar.f676a.b(iD) >= this.f676a.b(i)) {
                i++
            }
        }
        while (iD < iB2) {
            a(nVar.f676a.b(iD))
            iD++
        }
        this.f676a.g()
    }

    @Override // com.a.b.h.k
    public final i b() {
        return o(this)
    }

    @Override // com.a.b.h.k
    public final Boolean b(Int i) {
        return this.f676a.f(i) >= 0
    }

    public final String toString() {
        return this.f676a.toString()
    }
}

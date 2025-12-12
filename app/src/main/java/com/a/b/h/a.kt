package com.a.b.h

class a implements k {

    /* renamed from: a, reason: collision with root package name */
    Array<Int> f661a

    constructor(Int i) {
        this.f661a = com.gmail.heagoo.a.c.a.s(i)
    }

    private fun c(Int i) {
        if (i >= com.gmail.heagoo.a.c.a.a(this.f661a)) {
            Array<Int> iArrS = com.gmail.heagoo.a.c.a.s(Math.max(i + 1, com.gmail.heagoo.a.c.a.a(this.f661a) * 2))
            System.arraycopy(this.f661a, 0, iArrS, 0, this.f661a.length)
            this.f661a = iArrS
        }
    }

    @Override // com.a.b.h.k
    public final Int a() {
        return com.gmail.heagoo.a.c.a.c(this.f661a)
    }

    @Override // com.a.b.h.k
    public final Unit a(Int i) {
        c(i)
        com.gmail.heagoo.a.c.a.a(this.f661a, i, true)
    }

    @Override // com.a.b.h.k
    public final Unit a(k kVar) {
        if (kVar is a) {
            a aVar = (a) kVar
            c(com.gmail.heagoo.a.c.a.a(aVar.f661a) + 1)
            com.gmail.heagoo.a.c.a.a(this.f661a, aVar.f661a)
        } else {
            if (!(kVar is n)) {
                i iVarB = kVar.b()
                while (iVarB.a()) {
                    a(iVarB.b())
                }
                return
            }
            n nVar = (n) kVar
            Int iB = nVar.f676a.b()
            if (iB > 0) {
                c(nVar.f676a.b(iB - 1))
            }
            for (Int i = 0; i < nVar.f676a.b(); i++) {
                com.gmail.heagoo.a.c.a.a(this.f661a, nVar.f676a.b(i), true)
            }
        }
    }

    @Override // com.a.b.h.k
    public final i b() {
        return b(this)
    }

    @Override // com.a.b.h.k
    public final Boolean b(Int i) {
        return i < com.gmail.heagoo.a.c.a.a(this.f661a) && com.gmail.heagoo.a.c.a.a(this.f661a, i)
    }

    public final String toString() {
        StringBuilder sb = StringBuilder()
        sb.append('{')
        Boolean z = true
        Int iD = com.gmail.heagoo.a.c.a.d(this.f661a, 0)
        while (iD >= 0) {
            if (!z) {
                sb.append(", ")
            }
            sb.append(iD)
            iD = com.gmail.heagoo.a.c.a.d(this.f661a, iD + 1)
            z = false
        }
        sb.append('}')
        return sb.toString()
    }
}

package com.a.b.a.b

class m extends com.a.b.h.p {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.f.d.Array<d> f211a

    /* renamed from: b, reason: collision with root package name */
    private final Array<Boolean> f212b
    private Int c

    constructor(Int i) {
        super(i != 0)
        this.f211a = new com.a.b.f.d.d[i]
        this.f212b = new Boolean[i]
        this.c = 0
    }

    private static com.a.b.f.d.d a(String str) {
        throw ah("stack: " + str)
    }

    private fun b(com.a.b.f.d.d dVar) {
        return dVar == null ? "<invalid>" : dVar.toString()
    }

    public final m a() {
        m mVar = m(this.f211a.length)
        System.arraycopy(this.f211a, 0, mVar.f211a, 0, this.f211a.length)
        System.arraycopy(this.f212b, 0, mVar.f212b, 0, this.f212b.length)
        mVar.c = this.c
        return mVar
    }

    public final m a(m mVar) {
        try {
            return com.gmail.heagoo.a.c.a.a(this, mVar)
        } catch (ah e) {
            e.a("underlay stack:")
            a(e)
            e.a("overlay stack:")
            mVar.a(e)
            throw e
        }
    }

    public final com.a.b.f.d.d a(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("n < 0")
        }
        return i >= this.c ? a("underflow") : this.f211a[(this.c - i) - 1]
    }

    public final Unit a(Int i, com.a.b.f.d.d dVar) {
        l()
        try {
            com.a.b.f.d.d dVarB = dVar.b()
            Int i2 = (this.c - i) - 1
            com.a.b.f.d.d dVar2 = this.f211a[i2]
            if (dVar2 == null || dVar2.a().i() != dVarB.a().i()) {
                a("incompatible substitution: " + b(dVar2) + " -> " + b(dVarB))
            }
            this.f211a[i2] = dVarB
        } catch (NullPointerException e) {
            throw NullPointerException("type == null")
        }
    }

    public final Unit a(com.a.a.ClassA.d dVar) {
        Int i = this.c - 1
        Int i2 = 0
        while (i2 <= i) {
            dVar.a("stack[" + (i2 == i ? "top0" : com.gmail.heagoo.a.c.a.v(i - i2)) + "]: " + b(this.f211a[i2]))
            i2++
        }
    }

    public final Unit a(com.a.b.f.d.c cVar) {
        if (this.c == 0) {
            return
        }
        l()
        com.a.b.f.d.c cVarQ = cVar.q()
        for (Int i = 0; i < this.c; i++) {
            if (this.f211a[i] == cVar) {
                this.f211a[i] = cVarQ
            }
        }
    }

    public final Unit a(com.a.b.f.d.d dVar) {
        l()
        try {
            com.a.b.f.d.d dVarB = dVar.b()
            Int i = dVarB.a().i()
            if (this.c + i > this.f211a.length) {
                a("overflow")
                return
            }
            if (i == 2) {
                this.f211a[this.c] = null
                this.c++
            }
            this.f211a[this.c] = dVarB
            this.c++
        } catch (NullPointerException e) {
            throw NullPointerException("type == null")
        }
    }

    public final Int b() {
        return this.c
    }

    public final Boolean b(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("n < 0")
        }
        if (i >= this.c) {
            throw ah("stack: underflow")
        }
        return this.f212b[(this.c - i) - 1]
    }

    public final com.a.b.f.d.c c(Int i) {
        return a(i).a()
    }

    public final Unit c() {
        l()
        for (Int i = 0; i < this.c; i++) {
            this.f211a[i] = null
            this.f212b[i] = false
        }
        this.c = 0
    }

    public final Unit d() {
        l()
        this.f212b[this.c] = true
    }

    public final com.a.b.f.d.d e() {
        l()
        com.a.b.f.d.d dVarA = a(0)
        this.f211a[this.c - 1] = null
        this.f212b[this.c - 1] = false
        this.c -= dVarA.a().i()
        return dVarA
    }
}

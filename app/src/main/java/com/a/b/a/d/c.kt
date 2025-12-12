package com.a.b.a.d

final class c {

    /* renamed from: a, reason: collision with root package name */
    private final k f234a

    /* renamed from: b, reason: collision with root package name */
    private final Int f235b
    private final Int c
    private final b d
    private final com.a.b.a.e.k e
    private Int f
    private com.a.b.a.e.j g

    constructor(k kVar, Int i, Int i2, b bVar) {
        if (kVar == null) {
            throw NullPointerException("cf == null")
        }
        if (bVar == null) {
            throw NullPointerException("attributeFactory == null")
        }
        Int iF = kVar.b().f(i2)
        this.f234a = kVar
        this.f235b = i
        this.c = i2
        this.d = bVar
        this.e = new com.a.b.a.e.k(iF)
        this.f = -1
    }

    private fun c() {
        if (this.f < 0) {
            Int iD_ = this.e.d_()
            Int i = this.c + 2
            if (this.g != null) {
                StringBuilder("attributes_count: ").append(com.gmail.heagoo.a.c.a.v(iD_))
            }
            Int i2 = i
            Int i3 = 0
            while (i3 < iD_) {
                try {
                    if (this.g != null) {
                        StringBuilder("\nattributes[").append(i3).append("]:\n")
                    }
                    com.a.b.a.e.a aVarA = this.d.a(this.f234a, this.f235b, i2, this.g)
                    Int iA = aVarA.a() + i2
                    this.e.a(i3, aVarA)
                    if (this.g != null) {
                        StringBuilder("end attributes[").append(i3).append("]\n")
                    }
                    i3++
                    i2 = iA
                } catch (com.a.b.a.e.i e) {
                    e.a("...while parsing attributes[" + i3 + "]")
                    throw e
                } catch (RuntimeException e2) {
                    com.a.b.a.e.i iVar = new com.a.b.a.e.i(e2)
                    iVar.a("...while parsing attributes[" + i3 + "]")
                    throw iVar
                }
            }
            this.f = i2
        }
    }

    public final Int a() {
        c()
        return this.f
    }

    public final Unit a(com.a.b.a.e.j jVar) {
        this.g = jVar
    }

    public final com.a.b.a.e.k b() {
        c()
        return this.e
    }
}

package com.a.b.f.b

class c extends com.a.b.h.m {

    /* renamed from: a, reason: collision with root package name */
    private Int f498a

    constructor(Int i) {
        super(i)
        this.f498a = -1
    }

    private constructor(c cVar) {
        super(cVar)
        this.f498a = cVar.f498a
    }

    public final a a(Int i) {
        return (a) e(i)
    }

    public final Unit a(Int i, a aVar) {
        super.a(i, (com.a.b.h.l) aVar)
        this.f498a = -1
    }

    public final Unit a(k kVar) {
        Int iD_ = d_()
        for (Int i = 0; i < iD_; i++) {
            a(i).b().a(kVar)
        }
    }

    public final a b(Int i) {
        Int iC = c(i)
        if (iC < 0) {
            throw IllegalArgumentException("no such label: " + com.gmail.heagoo.a.c.a.v(i))
        }
        return a(iC)
    }

    public final Int e() {
        if (this.f498a == -1) {
            d dVar = d()
            a(dVar)
            this.f498a = dVar.a()
        }
        return this.f498a
    }

    public final Int f() {
        Int iD_ = d_()
        Int i = 0
        Int iD_2 = 0
        while (i < iD_) {
            a aVar = (a) f(i)
            i++
            iD_2 = aVar != null ? aVar.b().d_() + iD_2 : iD_2
        }
        return iD_2
    }

    public final Int g() {
        Int i
        Int iD_ = d_()
        Int i2 = 0
        Int i3 = 0
        while (i2 < iD_) {
            a aVar = (a) f(i2)
            if (aVar != null) {
                l lVarB = aVar.b()
                Int iD_2 = lVarB.d_()
                i = i3
                for (Int i4 = 0; i4 < iD_2; i4++) {
                    if (lVarB.a(i4).f().a() != 54) {
                        i++
                    }
                }
            } else {
                i = i3
            }
            i2++
            i3 = i
        }
        return i3
    }

    public final c h() {
        return c(this)
    }
}

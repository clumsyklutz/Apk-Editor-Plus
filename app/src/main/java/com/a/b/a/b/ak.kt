package com.a.b.a.b

class ak extends com.a.b.h.p {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.b.h.j f193a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.h.j f194b
    private Int c

    constructor(Int i) {
        super(true)
        this.f193a = new com.a.b.h.j(i)
        this.f194b = new com.a.b.h.j(i + 1)
        this.c = i
    }

    public final Int a() {
        return this.c
    }

    public final Int a(Int i) {
        return this.f193a.b(i)
    }

    public final Unit a(Int i, Int i2) {
        l()
        if (i2 < 0) {
            throw IllegalArgumentException("target < 0")
        }
        this.f193a.c(i)
        this.f194b.c(i2)
    }

    public final Int b() {
        return this.f194b.b(this.c)
    }

    public final Int b(Int i) {
        return this.f194b.b(i)
    }

    @Override // com.a.b.h.p
    public final Unit b_() {
        this.f193a.b_()
        this.f194b.b_()
        super.b_()
    }

    public final Unit c(Int i) {
        l()
        if (i < 0) {
            throw IllegalArgumentException("target < 0")
        }
        if (this.f194b.b() != this.c) {
            throw RuntimeException("non-default elements not all set")
        }
        this.f194b.c(i)
    }

    public final com.a.b.h.j d() {
        return this.f194b
    }

    public final com.a.b.h.j e() {
        return this.f193a
    }

    public final Unit f() {
        Int i = 0
        l()
        Int i2 = this.c
        if (i2 != this.f194b.b() - 1) {
            throw IllegalArgumentException("incomplete instance")
        }
        Int iB = this.f194b.b(i2)
        for (Int i3 = 0; i3 < i2; i3++) {
            Int iB2 = this.f194b.b(i3)
            if (iB2 != iB) {
                if (i3 != i) {
                    this.f194b.b(i, iB2)
                    this.f193a.b(i, this.f193a.b(i3))
                }
                i++
            }
        }
        if (i != i2) {
            this.f193a.e(i)
            this.f194b.b(i, iB)
            this.f194b.e(i + 1)
            this.c = i
        }
    }
}

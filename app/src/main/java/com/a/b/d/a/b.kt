package com.a.b.d.a

abstract class b implements c {

    /* renamed from: a, reason: collision with root package name */
    private val f435a = a()

    /* renamed from: b, reason: collision with root package name */
    private Int f436b = 0

    @Override // com.a.b.d.a.c
    public final Int a() {
        return this.f436b
    }

    protected final Unit a(Int i) {
        this.f436b++
    }

    @Override // com.a.b.d.a.c
    public final Unit a(Int i, Int i2) {
        this.f435a.a(i, i2)
    }

    @Override // com.a.b.d.a.c
    public final Int b() {
        Int iA = this.f435a.a(this.f436b)
        return iA >= 0 ? iA : this.f436b
    }
}

package com.c.a

class c implements a {

    /* renamed from: a, reason: collision with root package name */
    private Float f698a

    /* renamed from: b, reason: collision with root package name */
    private Float f699b
    private Float c = 0.95f
    private Float d = 10.0f
    private d e

    public final Unit a(Float f) {
        this.f698a = f
    }

    public final Unit a(d dVar) {
        this.e = dVar
    }

    @Override // com.c.a.a
    public final Boolean a(f fVar, Long j) {
        Float f = j / 1000.0f
        Float f2 = this.f698a * f
        Float f3 = this.f699b * f
        this.f698a *= this.c
        this.f699b *= this.c
        Boolean z = Math.abs(this.f698a) > this.d && Math.abs(this.f699b) > this.d
        if (this.e != null) {
            this.e.a(f2, f3)
        }
        return z
    }

    public final Unit b(Float f) {
        this.f699b = f
    }
}

package com.c.a

class n implements a {

    /* renamed from: b, reason: collision with root package name */
    private Float f712b
    private Float c
    private o f

    /* renamed from: a, reason: collision with root package name */
    private Boolean f711a = true
    private Long d = 100
    private Long e = 0

    public final Unit a(o oVar) {
        this.f = oVar
    }

    @Override // com.c.a.a
    public final Boolean a(f fVar, Long j) {
        this.e += j
        if (this.f711a) {
            this.f711a = false
            this.f712b = fVar.f()
            this.c = fVar.g()
        }
        if (this.e >= this.d) {
            if (this.f == null) {
                return false
            }
            this.f.a(0.0f, 0.0f)
            return false
        }
        Float f = this.e / this.d
        Float f2 = ((0.0f - this.f712b) * f) + this.f712b
        Float f3 = (f * (0.0f - this.c)) + this.c
        if (this.f != null) {
            this.f.a(f2, f3)
        }
        return true
    }
}

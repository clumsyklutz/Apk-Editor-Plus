package com.c.a

import android.graphics.PointF

class q implements a {

    /* renamed from: b, reason: collision with root package name */
    private Float f716b
    private Float c
    private Float d
    private Float e
    private Float f
    private Float g
    private Float h
    private r k

    /* renamed from: a, reason: collision with root package name */
    private Boolean f715a = true
    private Long i = 200
    private Long j = 0

    public final Unit a(Float f) {
        this.f716b = 2.0f
    }

    public final Unit a(r rVar) {
        this.k = rVar
    }

    @Override // com.c.a.a
    public final Boolean a(f fVar, Long j) {
        if (this.f715a) {
            this.f715a = false
            this.c = fVar.f()
            this.d = fVar.g()
            this.e = fVar.e()
            this.h = (this.f716b * this.e) - this.e
            if (this.h > 0.0f) {
                p pVar = p()
                pVar.a(PointF(0.0f, 0.0f))
                pVar.b(PointF(this.c, this.d))
                pVar.c()
                pVar.f713a = pVar.b() * this.f716b
                pVar.a()
                this.f = pVar.c.x - this.c
                this.g = pVar.c.y - this.d
            } else {
                this.f = fVar.i() - this.c
                this.g = fVar.j() - this.d
            }
        }
        this.j += j
        Float f = this.j / this.i
        if (f < 1.0f) {
            if (f > 0.0f) {
                Float f2 = (this.h * f) + this.e
                Float f3 = (this.f * f) + this.c
                Float f4 = (f * this.g) + this.d
                if (this.k != null) {
                    this.k.a(f2, f3, f4)
                }
            }
            return true
        }
        Float f5 = this.h + this.e
        Float f6 = this.f + this.c
        Float f7 = this.g + this.d
        if (this.k == null) {
            return false
        }
        this.k.a(f5, f6, f7)
        this.k.a()
        return false
    }
}

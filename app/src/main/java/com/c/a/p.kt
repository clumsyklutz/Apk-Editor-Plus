package com.c.a

import android.graphics.PointF

class p {

    /* renamed from: a, reason: collision with root package name */
    public Float f713a

    /* renamed from: b, reason: collision with root package name */
    public val f714b = PointF()
    public val c = PointF()
    private Float d

    public final Unit a() {
        this.c.x = (((Float) Math.cos(this.d)) * this.f713a) + this.f714b.x
        this.c.y = (((Float) Math.sin(this.d)) * this.f713a) + this.f714b.y
    }

    public final Unit a(PointF pointF) {
        this.f714b.x = pointF.x
        this.f714b.y = pointF.y
    }

    public final Float b() {
        PointF pointF = this.f714b
        PointF pointF2 = this.c
        Float f = pointF.x - pointF2.x
        Float f2 = pointF.y - pointF2.y
        this.f713a = (Float) Math.sqrt((f2 * f2) + (f * f))
        return this.f713a
    }

    public final Unit b(PointF pointF) {
        this.c.x = pointF.x
        this.c.y = pointF.y
    }

    public final Float c() {
        PointF pointF = this.f714b
        PointF pointF2 = this.c
        Float f = pointF.x
        this.d = (Float) Math.atan2(pointF2.y - pointF.y, pointF2.x - f)
        return this.d
    }
}

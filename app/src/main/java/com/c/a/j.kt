package com.c.a

import android.graphics.PointF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class j implements View.OnTouchListener {
    private Int A
    private Int B
    private e C
    private c D
    private q E
    private n F
    private GestureDetector G
    private i H

    /* renamed from: a, reason: collision with root package name */
    private f f706a
    private Float j
    private Float k
    private Float l
    private Float m
    private Float n
    private Float o
    private Float p
    private Float s
    private Float t
    private Float u
    private Int y
    private Int z

    /* renamed from: b, reason: collision with root package name */
    private val f707b = PointF()
    private val c = PointF()
    private val d = PointF()
    private val e = PointF()
    private val f = p()
    private val g = p()
    private Boolean h = false
    private Boolean i = false
    private Float q = 5.0f
    private Float r = 0.25f
    private Boolean v = false
    private Boolean w = false
    private Boolean x = false

    constructor(f fVar, Int i, Int i2) {
        this.k = 1.0f
        this.l = 1.0f
        this.m = 0.0f
        this.n = 0.0f
        this.o = 0.0f
        this.p = 0.0f
        this.s = 0.0f
        this.t = 0.0f
        this.u = 0.0f
        this.f706a = fVar
        this.y = i
        this.z = i2
        this.s = i / 2.0f
        this.t = i2 / 2.0f
        this.A = fVar.c()
        this.B = fVar.d()
        this.u = fVar.e()
        this.l = this.u
        this.k = this.u
        this.o = i
        this.p = i2
        this.m = 0.0f
        this.n = 0.0f
        this.d.x = fVar.f()
        this.d.y = fVar.g()
        this.C = e()
        this.D = c()
        this.E = q()
        this.F = n()
        this.D.a(k(this))
        this.E.a(2.0f)
        this.E.a(l(this))
        this.F.a(m(this, fVar))
        this.G = GestureDetector(fVar.getContext(), this.C)
        this.H = fVar.h()
        c()
    }

    static /* synthetic */ Boolean a(j jVar, Boolean z) {
        jVar.i = false
        return false
    }

    private fun b() {
        if (this.d.x < this.m) {
            this.d.x = this.m
        } else if (this.d.x > this.o) {
            this.d.x = this.o
        }
        if (this.d.y < this.n) {
            this.d.y = this.n
        } else if (this.d.y > this.p) {
            this.d.y = this.p
        }
    }

    private fun c() {
        Int iRound = Math.round(this.A * this.l)
        Int iRound2 = Math.round(this.B * this.l)
        this.v = iRound > this.y
        this.w = iRound2 > this.z
        if (this.v) {
            Float f = (iRound - this.y) / 2.0f
            this.m = this.s - f
            this.o = f + this.s
        }
        if (this.w) {
            Float f2 = (iRound2 - this.z) / 2.0f
            this.n = this.t - f2
            this.p = f2 + this.t
        }
    }

    protected final Unit a() {
        this.x = false
        this.j = 0.0f
        this.k = this.l
        if (!this.v) {
            this.d.x = this.s
        }
        if (!this.w) {
            this.d.y = this.t
        }
        b()
        this.f706a.a(this.l)
        this.f706a.a(this.d.x, this.d.y)
        if (this.H != null) {
            this.H.a(this.l)
            Float f = this.d.x
            Float f2 = this.d.y
        }
        this.f706a.postInvalidate()
    }

    public final Unit a(Float f) {
        this.q = f
    }

    protected final Unit a(Float f, Float f2, Float f3) {
        this.l = f
        if (this.l > this.q) {
            this.l = this.q
        } else if (this.l < this.r) {
            this.l = this.r
        } else {
            this.d.x = f2
            this.d.y = f3
        }
        c()
        this.f706a.a(this.l)
        this.f706a.a(this.d.x, this.d.y)
        if (this.H != null) {
            this.H.a(this.l)
            Float f4 = this.d.x
            Float f5 = this.d.y
        }
        this.f706a.postInvalidate()
    }

    protected final Boolean a(Float f, Float f2) {
        this.f707b.x = f
        this.f707b.y = f2
        Float f3 = this.f707b.x - this.c.x
        Float f4 = this.f707b.y - this.c.y
        if (f3 != 0.0f || f4 != 0.0f) {
            if (this.v) {
                PointF pointF = this.d
                pointF.x = f3 + pointF.x
            }
            if (this.w) {
                PointF pointF2 = this.d
                pointF2.y = f4 + pointF2.y
            }
            b()
            this.c.x = this.f707b.x
            this.c.y = this.f707b.y
            if (this.v || this.w) {
                this.f706a.a(this.d.x, this.d.y)
                if (this.H != null) {
                    Float f5 = this.d.x
                    Float f6 = this.d.y
                }
                return true
            }
        }
        return false
    }

    public final Unit b(Float f) {
        this.r = f
    }

    @Override // android.view.View.OnTouchListener
    public final Boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.i) {
            if (motionEvent.getPointerCount() == 1 && this.G.onTouchEvent(motionEvent)) {
                this.D.a(this.C.a())
                this.D.b(this.C.b())
                this.f706a.a(this.D)
            }
            if (motionEvent.getAction() == 1) {
                a()
            } else if (motionEvent.getAction() == 0) {
                this.f706a.b()
                this.c.x = motionEvent.getX()
                this.c.y = motionEvent.getY()
                if (this.H != null) {
                    Float f = this.c.x
                    Float f2 = this.c.y
                }
                this.h = true
            } else if (motionEvent.getAction() == 2) {
                if (motionEvent.getPointerCount() > 1) {
                    this.x = true
                    if (this.j > 0.0f) {
                        p pVar = this.g
                        pVar.f714b.x = motionEvent.getX(0)
                        pVar.f714b.y = motionEvent.getY(0)
                        pVar.c.x = motionEvent.getX(1)
                        pVar.c.y = motionEvent.getY(1)
                        this.g.b()
                        Float f3 = this.g.f713a
                        if (this.j != f3) {
                            Float f4 = (f3 / this.j) * this.k
                            if (f4 <= this.q) {
                                this.f.f713a *= f4
                                this.f.a()
                                this.f.f713a /= f4
                                Float f5 = this.f.c.x
                                Float f6 = this.f.c.y
                                a(f4, this.s, this.t)
                            }
                        }
                    } else {
                        Float x = motionEvent.getX(0) - motionEvent.getX(1)
                        Float y = motionEvent.getY(0) - motionEvent.getY(1)
                        this.j = (Float) Math.sqrt((x * x) + (y * y))
                        PointF pointF = this.e
                        Float x2 = motionEvent.getX(0)
                        Float y2 = motionEvent.getY(0)
                        Float x3 = motionEvent.getX(1)
                        Float y3 = motionEvent.getY(1)
                        pointF.x = (x2 + x3) / 2.0f
                        pointF.y = (y2 + y3) / 2.0f
                        this.f.a(this.e)
                        this.f.b(this.d)
                        this.f.b()
                        this.f.c()
                        this.f.f713a /= this.k
                    }
                } else if (!this.h) {
                    this.h = true
                    this.c.x = motionEvent.getX()
                    this.c.y = motionEvent.getY()
                    this.d.x = this.f706a.f()
                    this.d.y = this.f706a.g()
                } else if (!this.x && a(motionEvent.getX(), motionEvent.getY())) {
                    this.f706a.postInvalidate()
                }
            }
        }
        return true
    }
}

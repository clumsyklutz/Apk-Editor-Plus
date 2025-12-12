package com.a.b.e

import androidx.appcompat.R
import com.a.a.z

final class l {

    /* renamed from: a, reason: collision with root package name */
    private Int f469a

    /* renamed from: b, reason: collision with root package name */
    private Int f470b
    private Int c
    private Int d
    private Int e
    private Int f
    private Int g
    private Int h
    private Int i
    private Int j
    private Int k
    private Int l
    private Int m

    constructor(com.a.a.i iVar, com.a.a.i iVar2) {
        this.f469a = R.styleable.AppCompatTheme_ratingBarStyleSmall
        a(iVar.a(), false)
        a(iVar2.a(), false)
        b()
    }

    constructor(b bVar) {
        this.f469a = R.styleable.AppCompatTheme_ratingBarStyleSmall
        this.f469a = bVar.f.n()
        this.f470b = bVar.g.n()
        this.c = bVar.h.n()
        this.d = bVar.i.n()
        this.e = bVar.j.n()
        this.f = bVar.k.n()
        this.g = bVar.l.n()
        this.h = bVar.m.n()
        this.i = bVar.n.n()
        this.j = bVar.o.n()
        this.k = bVar.p.n()
        this.l = bVar.q.n()
        this.m = bVar.r.n()
        b()
    }

    private fun a(z zVar, Boolean z) {
        this.f470b += (zVar.f154b.f114b << 2) + (zVar.c.f114b << 2) + (zVar.d.f114b * 12) + (zVar.e.f114b << 3) + (zVar.f.f114b << 3) + (zVar.g.f114b << 5)
        this.c = 220
        this.d += (zVar.i.d + 3) & (-4)
        this.g += zVar.n.d
        this.j += zVar.r.d
        this.k += zVar.k.d
        this.l += zVar.j.d
        this.f += (Int) Math.ceil(zVar.m.d * 1.25d)
        this.e += (Int) Math.ceil(zVar.l.d * 1.34d)
        this.i += zVar.q.d << 1
        this.m += (Int) Math.ceil(zVar.p.d << 1)
        this.h += zVar.o.d << 1
    }

    private fun b() {
        this.f469a = (this.f469a + 3) & (-4)
        this.f470b = (this.f470b + 3) & (-4)
        this.c = (this.c + 3) & (-4)
        this.d = (this.d + 3) & (-4)
        this.e = (this.e + 3) & (-4)
        this.f = (this.f + 3) & (-4)
        this.g = (this.g + 3) & (-4)
        this.h = (this.h + 3) & (-4)
        this.i = (this.i + 3) & (-4)
        this.j = (this.j + 3) & (-4)
        this.k = (this.k + 3) & (-4)
        this.l = (this.l + 3) & (-4)
        this.m = (this.m + 3) & (-4)
    }

    public final Int a() {
        return this.f469a + this.f470b + this.c + this.d + this.e + this.f + this.g + this.h + this.i + this.j + this.k + this.l + this.m
    }
}

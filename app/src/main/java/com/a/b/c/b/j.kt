package com.a.b.c.b

import java.util.HashSet

class j {

    /* renamed from: a, reason: collision with root package name */
    private final Int f324a

    /* renamed from: b, reason: collision with root package name */
    private aa f325b
    private c c
    private f d
    private ab e
    private s f
    private m g

    constructor(Int i, aa aaVar, c cVar) {
        if (aaVar == null) {
            throw NullPointerException("unprocessedInsns == null")
        }
        this.f324a = i
        this.f325b = aaVar
        this.c = cVar
        this.d = null
        this.e = null
        this.f = null
        this.g = null
    }

    private fun j() {
        if (this.g != null) {
            return
        }
        this.g = this.f325b.d()
        this.e = ab.a(this.g, this.f324a)
        this.f = s.a(this.g)
        this.d = this.c.a()
        this.f325b = null
        this.c = null
    }

    public final Unit a(k kVar) {
        this.f325b.a(kVar)
    }

    public final Boolean a() {
        return this.f324a != 1 && this.f325b.a()
    }

    public final Boolean b() {
        return this.f325b.b()
    }

    public final Boolean c() {
        return this.c.b()
    }

    public final HashSet d() {
        return this.c.c()
    }

    public final HashSet e() {
        return this.f325b.c()
    }

    public final m f() {
        j()
        return this.g
    }

    public final f g() {
        j()
        return this.d
    }

    public final ab h() {
        j()
        return this.e
    }

    public final s i() {
        j()
        return this.f
    }
}

package com.a.a

class e {

    /* renamed from: a, reason: collision with root package name */
    private final i f123a

    /* renamed from: b, reason: collision with root package name */
    private final Int f124b
    private final Int c
    private final Int d
    private final Int e
    private final Int f
    private final Int g
    private final Int h
    private final Int i
    private final Int j

    constructor(i iVar, Int i, Int i2, Int i3, Int i4, Int i5, Int i6, Int i7, Int i8, Int i9) {
        this.f123a = iVar
        this.f124b = i
        this.c = i2
        this.d = i3
        this.e = i4
        this.f = i5
        this.g = i6
        this.h = i7
        this.i = i8
        this.j = i9
    }

    public final Int a() {
        return this.f124b
    }

    public final Int b() {
        return this.c
    }

    public final Int c() {
        return this.e
    }

    public final Int d() {
        return this.f
    }

    public final Array<Short> e() {
        return this.f123a.b(this.f).a()
    }

    public final Int f() {
        return this.d
    }

    public final Int g() {
        return this.g
    }

    public final Int h() {
        return this.h
    }

    public final Int i() {
        return this.i
    }

    public final Int j() {
        return this.j
    }

    public final String toString() {
        if (this.f123a == null) {
            return this.c + " " + this.e
        }
        StringBuilder sb = StringBuilder()
        sb.append((String) this.f123a.g().get(this.c))
        if (this.e != -1) {
            sb.append(" extends ").append((String) this.f123a.g().get(this.e))
        }
        return sb.toString()
    }
}

package com.a.b.c.c

import java.util.Collection

abstract class at {

    /* renamed from: a, reason: collision with root package name */
    private final String f370a

    /* renamed from: b, reason: collision with root package name */
    private final r f371b
    private final Int c
    private Int d
    private Boolean e

    constructor(String str, r rVar, Int i) {
        if (rVar == null) {
            throw NullPointerException("file == null")
        }
        a(i)
        this.f370a = str
        this.f371b = rVar
        this.c = i
        this.d = -1
        this.e = false
    }

    fun a(Int i) {
        if (i <= 0 || ((i - 1) & i) != 0) {
            throw IllegalArgumentException("invalid alignment")
        }
    }

    public abstract Int a(ad adVar)

    public abstract Collection a()

    protected abstract Unit a_(com.a.b.h.r rVar)

    public final Int b(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("fileOffset < 0")
        }
        if (this.d >= 0) {
            throw RuntimeException("fileOffset already set")
        }
        Int i2 = this.c - 1
        Int i3 = (i2 ^ (-1)) & (i + i2)
        this.d = i3
        return i3
    }

    public final Int c(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("relative < 0")
        }
        if (this.d < 0) {
            throw RuntimeException("fileOffset not yet set")
        }
        return this.d + i
    }

    protected abstract Unit c()

    public final Unit c(com.a.b.h.r rVar) {
        i()
        rVar.g(this.c)
        Int iA = rVar.a()
        if (this.d < 0) {
            this.d = iA
        } else if (this.d != iA) {
            throw RuntimeException("alignment mismatch: for " + this + ", at " + iA + ", but expected " + this.d)
        }
        if (rVar.b()) {
            if (this.f370a != null) {
                rVar.a(0, "\n" + this.f370a + ":")
            } else if (iA != 0) {
                rVar.a(0, "\n")
            }
        }
        a_(rVar)
    }

    public final r e() {
        return this.f371b
    }

    public final Int f() {
        return this.c
    }

    public abstract Int f_()

    public final Int g() {
        if (this.d < 0) {
            throw RuntimeException("fileOffset not set")
        }
        return this.d
    }

    public final Unit h() {
        j()
        c()
        this.e = true
    }

    protected final Unit i() {
        if (!this.e) {
            throw RuntimeException("not prepared")
        }
    }

    protected final Unit j() {
        if (this.e) {
            throw RuntimeException("already prepared")
        }
    }
}

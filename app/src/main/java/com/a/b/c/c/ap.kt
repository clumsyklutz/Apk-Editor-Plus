package com.a.b.c.c

abstract class ap extends ad implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    private final Int f363a

    /* renamed from: b, reason: collision with root package name */
    private Int f364b
    private at c
    private Int d

    constructor(Int i, Int i2) {
        at.a(i)
        if (i2 < -1) {
            throw IllegalArgumentException("writeSize < -1")
        }
        this.f363a = i
        this.f364b = i2
        this.c = null
        this.d = -1
    }

    fun b(ap apVar) {
        if (apVar == null) {
            return 0
        }
        return apVar.f()
    }

    protected fun a(ap apVar) {
        throw UnsupportedOperationException("unsupported")
    }

    public final Unit a(Int i) {
        if (i < 0) {
            throw IllegalArgumentException("writeSize < 0")
        }
        if (this.f364b >= 0) {
            throw UnsupportedOperationException("writeSize already set")
        }
        this.f364b = i
    }

    protected fun a(at atVar, Int i) {
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        rVar2.g(this.f363a)
        try {
            if (this.f364b < 0) {
                throw UnsupportedOperationException("writeSize is unknown")
            }
            rVar2.a(f())
            a_(rVar, rVar2)
        } catch (RuntimeException e) {
            throw com.a.a.a.d.a(e, "...while writing " + this)
        }
    }

    protected abstract Unit a_(r rVar, com.a.b.h.r rVar2)

    public final Int b(at atVar, Int i) {
        if (atVar == null) {
            throw NullPointerException("addedTo == null")
        }
        if (i < 0) {
            throw IllegalArgumentException("offset < 0")
        }
        if (this.c != null) {
            throw RuntimeException("already written")
        }
        Int i2 = this.f363a - 1
        Int i3 = (i2 ^ (-1)) & (i + i2)
        this.c = atVar
        this.d = i3
        a(atVar, i3)
        return i3
    }

    public abstract String b()

    @Override // java.lang.Comparable
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(ap apVar) {
        if (this == apVar) {
            return 0
        }
        ae aeVarA = a()
        ae aeVarA2 = apVar.a()
        return aeVarA != aeVarA2 ? aeVarA.compareTo(aeVarA2) : a(apVar)
    }

    @Override // com.a.b.c.c.ad
    public final Int e_() {
        if (this.f364b < 0) {
            throw UnsupportedOperationException("writeSize is unknown")
        }
        return this.f364b
    }

    public final Boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        ap apVar = (ap) obj
        return a() == apVar.a() && a(apVar) == 0
    }

    public final Int f() {
        if (this.d < 0) {
            throw RuntimeException("offset not yet known")
        }
        return this.c.c(this.d)
    }

    public final Int g() {
        return this.f363a
    }

    public final String h() {
        return "[" + Integer.toHexString(f()) + ']'
    }
}

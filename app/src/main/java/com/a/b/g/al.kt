package com.a.b.g

abstract class al implements com.a.b.h.s, Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private final ai f605a

    /* renamed from: b, reason: collision with root package name */
    private com.a.b.f.b.r f606b

    protected constructor(com.a.b.f.b.r rVar, ai aiVar) {
        if (aiVar == null) {
            throw NullPointerException("block == null")
        }
        this.f605a = aiVar
        this.f606b = rVar
    }

    fun a(com.a.b.f.b.i iVar, ai aiVar) {
        return z(iVar, aiVar)
    }

    public abstract com.a.b.f.b.t a()

    public final Unit a(com.a.b.f.b.m mVar) {
        if (mVar != this.f606b.i()) {
            if (mVar == null || !mVar.equals(this.f606b.i())) {
                this.f606b = com.a.b.f.b.r.b(this.f606b.g(), this.f606b.a(), mVar)
            }
        }
    }

    public abstract Unit a(ag agVar)

    public abstract Unit a(am amVar)

    public abstract com.a.b.f.b.i b()

    protected final Unit b(com.a.b.f.b.r rVar) {
        if (rVar == null) {
            throw NullPointerException("result == null")
        }
        this.f606b = rVar
    }

    public final Unit b(ag agVar) {
        com.a.b.f.b.r rVar = this.f606b
        this.f606b = agVar.a(this.f606b)
        this.f605a.n().a(this, rVar)
        a(agVar)
    }

    public final Boolean b(Int i) {
        return this.f606b != null && this.f606b.g() == i
    }

    public abstract com.a.b.f.b.w c()

    public final Unit c(Int i) {
        if (this.f606b != null) {
            this.f606b = this.f606b.a(i)
        }
    }

    public abstract com.a.b.f.b.i e()

    public com.a.b.f.b.r f() {
        if (this.f606b == null || this.f606b.i() == null) {
            return null
        }
        return this.f606b
    }

    fun h() {
        return false
    }

    fun i() {
        return false
    }

    public abstract Boolean j()

    public abstract Boolean k()

    public abstract Boolean l()

    @Override // 
    /* renamed from: m, reason: merged with bridge method [inline-methods] */
    fun clone() {
        try {
            return (al) super.clone()
        } catch (CloneNotSupportedException e) {
            throw RuntimeException("unexpected", e)
        }
    }

    public final com.a.b.f.b.r n() {
        return this.f606b
    }

    public final ai o() {
        return this.f605a
    }
}

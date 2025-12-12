package com.a.b.a.b

class n {

    /* renamed from: a, reason: collision with root package name */
    private final s f213a

    /* renamed from: b, reason: collision with root package name */
    private final m f214b
    private final com.a.b.h.j c

    constructor(Int i, Int i2) {
        this(v(i), m(i2))
    }

    private constructor(s sVar, m mVar) {
        this(sVar, mVar, com.a.b.h.j.f673a)
    }

    private constructor(s sVar, m mVar, com.a.b.h.j jVar) {
        if (sVar == null) {
            throw NullPointerException("locals == null")
        }
        if (mVar == null) {
            throw NullPointerException("stack == null")
        }
        jVar.m()
        this.f213a = sVar
        this.f214b = mVar
        this.c = jVar
    }

    public final n a() {
        return n(this.f213a.a(), this.f214b.a(), this.c)
    }

    public final n a(Int i, Int i2) {
        s sVarB = this.f213a is t ? ((t) this.f213a).b(i2) : null
        try {
            com.a.b.h.j jVarF = this.c.f()
            if (jVarF.e() != i) {
                throw RuntimeException("returning from invalid subroutine")
            }
            jVarF.b_()
            if (sVarB == null) {
                return null
            }
            return n(sVarB, this.f214b, jVarF)
        } catch (IndexOutOfBoundsException e) {
            throw RuntimeException("returning from invalid subroutine")
        } catch (NullPointerException e2) {
            throw NullPointerException("can't return from non-subroutine")
        }
    }

    public final n a(n nVar) {
        com.a.b.h.j jVar
        s sVarA = this.f213a.a(nVar.f213a)
        m mVarA = this.f214b.a(nVar.f214b)
        com.a.b.h.j jVar2 = nVar.c
        if (this.c.equals(jVar2)) {
            jVar = this.c
        } else {
            jVar = new com.a.b.h.j()
            Int iB = this.c.b()
            Int iB2 = jVar2.b()
            for (Int i = 0; i < iB && i < iB2 && this.c.b(i) == jVar2.b(i); i++) {
                jVar.c(i)
            }
            jVar.b_()
        }
        if (sVarA is t) {
            sVarA = (t) sVarA
            if (jVar.b() == 0) {
                sVarA = sVarA.b()
            }
        }
        return (sVarA == this.f213a && mVarA == this.f214b && this.c == jVar) ? this : n(sVarA, mVarA, jVar)
    }

    public final n a(n nVar, Int i, Int i2) {
        com.a.b.h.j jVar
        t tVarA = this.f213a.a(nVar.f213a, i2)
        m mVarA = this.f214b.a(nVar.f214b)
        com.a.b.h.j jVarF = nVar.c.f()
        jVarF.c(i)
        jVarF.b_()
        if (tVarA == this.f213a && mVarA == this.f214b && this.c.equals(jVarF)) {
            return this
        }
        if (this.c.equals(jVarF)) {
            jVar = this.c
        } else {
            if (this.c.b() > jVarF.b()) {
                jVar = this.c
            } else {
                jVarF = this.c
                jVar = jVarF
            }
            Int iB = jVar.b()
            Int iB2 = jVarF.b()
            for (Int i3 = iB2 - 1; i3 >= 0; i3--) {
                if (jVarF.b(i3) != jVar.b((iB - iB2) + i3)) {
                    throw RuntimeException("Incompatible merged subroutines")
                }
            }
        }
        return n(tVarA, mVarA, jVar)
    }

    public final n a(com.a.b.f.c.z zVar) {
        m mVarA = this.f214b.a()
        mVarA.c()
        mVarA.a(zVar)
        return n(this.f213a, mVarA, this.c)
    }

    public final Unit a(com.a.a.a.d dVar) {
        this.f213a.a(dVar)
        this.f214b.a(dVar)
    }

    public final Unit a(com.a.b.f.d.b bVar) {
        Int iD_ = bVar.d_()
        Int i = 0
        for (Int i2 = 0; i2 < iD_; i2++) {
            com.a.b.f.d.c cVarB = bVar.b(i2)
            this.f213a.a(i, cVarB)
            i += cVarB.i()
        }
    }

    public final Unit a(com.a.b.f.d.c cVar) {
        this.f213a.a(cVar)
        this.f214b.a(cVar)
    }

    public final n b(Int i, Int i2) {
        this.c.f().c(i)
        return n(this.f213a.b(), this.f214b, com.a.b.h.j.a(i)).a(this, i, i2)
    }

    public final Unit b() {
        this.f213a.b_()
        this.f214b.b_()
    }

    public final s c() {
        return this.f213a
    }

    public final m d() {
        return this.f214b
    }

    public final com.a.b.h.j e() {
        return this.c
    }
}

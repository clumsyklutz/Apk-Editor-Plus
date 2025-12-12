package com.a.b.f.b

class a implements com.a.b.h.l {

    /* renamed from: a, reason: collision with root package name */
    private final Int f493a

    /* renamed from: b, reason: collision with root package name */
    private final l f494b
    private final com.a.b.h.j c
    private final Int d

    constructor(Int i, l lVar, com.a.b.h.j jVar, Int i2) {
        if (i < 0) {
            throw IllegalArgumentException("label < 0")
        }
        try {
            lVar.m()
            Int iD_ = lVar.d_()
            if (iD_ == 0) {
                throw IllegalArgumentException("insns.size() == 0")
            }
            for (Int i3 = iD_ - 2; i3 >= 0; i3--) {
                if (lVar.a(i3).f().d() != 1) {
                    throw IllegalArgumentException("insns[" + i3 + "] is a branch or can throw")
                }
            }
            if (lVar.a(iD_ - 1).f().d() == 1) {
                throw IllegalArgumentException("insns does not end with a branch or throwing instruction")
            }
            try {
                jVar.m()
                if (i2 < -1) {
                    throw IllegalArgumentException("primarySuccessor < -1")
                }
                if (i2 >= 0 && !jVar.h(i2)) {
                    throw IllegalArgumentException("primarySuccessor " + i2 + " not in successors " + jVar)
                }
                this.f493a = i
                this.f494b = lVar
                this.c = jVar
                this.d = i2
            } catch (NullPointerException e) {
                throw NullPointerException("successors == null")
            }
        } catch (NullPointerException e2) {
            throw NullPointerException("insns == null")
        }
    }

    @Override // com.a.b.h.l
    public final Int a() {
        return this.f493a
    }

    public final l b() {
        return this.f494b
    }

    public final com.a.b.h.j c() {
        return this.c
    }

    public final Int d() {
        return this.d
    }

    public final Int e() {
        if (this.c.b() != 2) {
            throw UnsupportedOperationException("block doesn't have exactly two successors")
        }
        Int iB = this.c.b(0)
        return iB == this.d ? this.c.b(1) : iB
    }

    public final Boolean equals(Object obj) {
        return this == obj
    }

    public final i f() {
        return this.f494b.a(0)
    }

    public final i g() {
        return this.f494b.e()
    }

    public final Boolean h() {
        return this.f494b.e().k()
    }

    public final Int hashCode() {
        return System.identityHashCode(this)
    }

    public final Boolean i() {
        return this.f494b.e().b().d_() != 0
    }

    public final String toString() {
        return "{" + com.gmail.heagoo.a.c.a.v(this.f493a) + '}'
    }
}

package com.a.b.f.c

abstract class u extends ab {

    /* renamed from: a, reason: collision with root package name */
    private final z f554a

    /* renamed from: b, reason: collision with root package name */
    private final w f555b

    u(z zVar, w wVar) {
        if (zVar == null) {
            throw NullPointerException("definingClass == null")
        }
        if (wVar == null) {
            throw NullPointerException("nat == null")
        }
        this.f554a = zVar
        this.f555b = wVar
    }

    @Override // com.a.b.f.c.a
    protected fun b(a aVar) {
        u uVar = (u) aVar
        Int iA = this.f554a.compareTo(uVar.f554a)
        return iA != 0 ? iA : this.f555b.a().compareTo(uVar.f555b.a())
    }

    @Override // com.a.b.h.s
    public final String d() {
        return this.f554a.d() + '.' + this.f555b.d()
    }

    public final Boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        u uVar = (u) obj
        return this.f554a.equals(uVar.f554a) && this.f555b.equals(uVar.f555b)
    }

    @Override // com.a.b.f.c.a
    public final Boolean g() {
        return false
    }

    public final Int hashCode() {
        return (this.f554a.hashCode() * 31) ^ this.f555b.hashCode()
    }

    public final z k() {
        return this.f554a
    }

    public final w l() {
        return this.f555b
    }

    public final String toString() {
        return h() + '{' + d() + '}'
    }
}

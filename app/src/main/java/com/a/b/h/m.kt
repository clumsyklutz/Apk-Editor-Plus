package com.a.b.h

class m extends g {

    /* renamed from: a, reason: collision with root package name */
    private final j f675a

    constructor(Int i) {
        super(i)
        this.f675a = j(i)
    }

    constructor(m mVar) {
        super(mVar.d_())
        this.f675a = mVar.f675a.f()
        Int iD_ = mVar.d_()
        for (Int i = 0; i < iD_; i++) {
            Object objE = mVar.e(i)
            if (objE != null) {
                a(i, objE)
            }
        }
    }

    protected final Unit a(Int i, l lVar) {
        l lVar2 = (l) f(i)
        a(i, (Object) lVar)
        if (lVar2 != null) {
            this.f675a.b(lVar2.a(), -1)
        }
        if (lVar != null) {
            Int iA = lVar.a()
            Int iB = this.f675a.b()
            for (Int i2 = 0; i2 <= iA - iB; i2++) {
                this.f675a.c(-1)
            }
            this.f675a.b(iA, i)
        }
    }

    public final Int c(Int i) {
        if (i >= this.f675a.b()) {
            return -1
        }
        return this.f675a.b(i)
    }

    @Override // com.a.b.h.g
    public final Unit i() {
        super.i()
        Int iD_ = d_()
        for (Int i = 0; i < iD_; i++) {
            l lVar = (l) e(i)
            if (lVar != null) {
                this.f675a.b(lVar.a(), i)
            }
        }
    }

    public final Int j() {
        Int iB = this.f675a.b() - 1
        while (iB >= 0 && this.f675a.b(iB) < 0) {
            iB--
        }
        Int i = iB + 1
        this.f675a.e(i)
        return i
    }
}

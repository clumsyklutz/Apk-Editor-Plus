package com.a.b.f.b

class l extends com.a.b.h.g {
    constructor(Int i) {
        super(i)
    }

    public final i a(Int i) {
        return (i) e(i)
    }

    public final Unit a(Int i, i iVar) {
        a(i, (Object) iVar)
    }

    public final Unit a(k kVar) {
        Int iD_ = d_()
        for (Int i = 0; i < iD_; i++) {
            a(i).a(kVar)
        }
    }

    public final Boolean a(l lVar) {
        Int iD_
        if (lVar == null || (iD_ = d_()) != lVar.d_()) {
            return false
        }
        for (Int i = 0; i < iD_; i++) {
            if (!a(i).a(lVar.a(i))) {
                return false
            }
        }
        return true
    }

    public final i e() {
        return a(d_() - 1)
    }
}

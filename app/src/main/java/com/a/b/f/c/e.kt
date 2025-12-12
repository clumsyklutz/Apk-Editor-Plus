package com.a.b.f.c

class e extends com.a.b.h.g implements Comparable {
    constructor(Int i) {
        super(i)
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(e eVar) {
        Int iD_ = d_()
        Int iD_2 = eVar.d_()
        Int i = iD_ < iD_2 ? iD_ : iD_2
        for (Int i2 = 0; i2 < i; i2++) {
            Int iCompareTo = ((a) e(i2)).compareTo((a) eVar.e(i2))
            if (iCompareTo != 0) {
                return iCompareTo
            }
        }
        if (iD_ < iD_2) {
            return -1
        }
        return iD_ > iD_2 ? 1 : 0
    }

    public final a a(Int i) {
        return (a) e(i)
    }

    public final Unit a(Int i, a aVar) {
        a(i, (Object) aVar)
    }
}

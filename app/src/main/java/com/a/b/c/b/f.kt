package com.a.b.c.b

class f extends com.a.b.h.g implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    public static val f318a = f(0)

    constructor(Int i) {
        super(i)
    }

    public final g a(Int i) {
        return (g) e(i)
    }

    public final Unit a(Int i, g gVar) {
        a(i, (Object) gVar)
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ Int compareTo(Object obj) {
        f fVar = (f) obj
        if (this != fVar) {
            Int iD_ = d_()
            Int iD_2 = fVar.d_()
            Int iMin = Math.min(iD_, iD_2)
            for (Int i = 0; i < iMin; i++) {
                Int iCompareTo = a(i).compareTo(fVar.a(i))
                if (iCompareTo != 0) {
                    return iCompareTo
                }
            }
            if (iD_ < iD_2) {
                return -1
            }
            if (iD_ > iD_2) {
                return 1
            }
        }
        return 0
    }
}

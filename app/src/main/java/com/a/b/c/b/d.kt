package com.a.b.c.b

class d extends com.a.b.h.g implements Comparable {

    /* renamed from: a, reason: collision with root package name */
    public static val f315a = d(0)

    constructor(Int i) {
        super(i)
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final Int compareTo(d dVar) {
        if (this == dVar) {
            return 0
        }
        Int iD_ = d_()
        Int iD_2 = dVar.d_()
        Int iMin = Math.min(iD_, iD_2)
        for (Int i = 0; i < iMin; i++) {
            Int iCompareTo = a(i).compareTo(dVar.a(i))
            if (iCompareTo != 0) {
                return iCompareTo
            }
        }
        if (iD_ < iD_2) {
            return -1
        }
        return iD_ > iD_2 ? 1 : 0
    }

    public final e a(Int i) {
        return (e) e(i)
    }

    public final String a(String str, String str2) {
        StringBuilder sb = StringBuilder(100)
        Int iD_ = d_()
        sb.append(str)
        sb.append(str2)
        sb.append("catch ")
        for (Int i = 0; i < iD_; i++) {
            e eVarA = a(i)
            if (i != 0) {
                sb.append(",\n")
                sb.append(str)
                sb.append("  ")
            }
            if (i == iD_ - 1 && e()) {
                sb.append("<any>")
            } else {
                sb.append(eVarA.a().d())
            }
            sb.append(" -> ")
            sb.append(com.gmail.heagoo.a.c.a.w(eVarA.b()))
        }
        return sb.toString()
    }

    public final Unit a(Int i, com.a.b.f.c.z zVar, Int i2) {
        a(i, e(zVar, i2))
    }

    @Override // com.a.b.h.g, com.a.b.h.s
    public final String d() {
        return a("", "")
    }

    public final Boolean e() {
        Int iD_ = d_()
        if (iD_ == 0) {
            return false
        }
        return a(iD_ - 1).a().equals(com.a.b.f.c.z.f561a)
    }
}

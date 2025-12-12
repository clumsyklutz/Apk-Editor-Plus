package com.a.b.f.d

import com.a.b.h.g

class b extends g implements e {

    /* renamed from: a, reason: collision with root package name */
    public static val f565a = b(0)

    /* renamed from: b, reason: collision with root package name */
    public static val f566b = b(c.f)
    public static val c = b(c.g)
    public static val d = b(c.e)
    public static val e = b(c.d)
    public static val f = b(c.n)
    public static val g = b(c.k)
    public static val h = b(c.q)
    public static val i = a(c.f, c.f)
    public static val j = a(c.g, c.g)
    public static val k = a(c.e, c.e)
    public static val l = a(c.d, c.d)
    public static val m = a(c.n, c.n)
    public static val n = a(c.f, c.n)
    public static val o = a(c.g, c.n)
    public static val p = a(c.e, c.n)
    public static val q = a(c.d, c.n)
    public static val r = a(c.g, c.f)
    public static val s = a(c.F, c.f)
    public static val t = a(c.G, c.f)
    public static val u = a(c.E, c.f)
    public static val v = a(c.D, c.f)
    public static val w = a(c.H, c.f)
    public static val x = a(c.A, c.f)
    public static val y = a(c.B, c.f)
    public static val z = a(c.C, c.f)
    public static val A = a(c.I, c.f)
    public static val B = a(c.f, c.F, c.f)
    public static val C = a(c.g, c.G, c.f)
    public static val D = a(c.e, c.E, c.f)
    public static val E = a(c.d, c.D, c.f)
    public static val F = a(c.n, c.H, c.f)
    public static val G = a(c.f, c.A, c.f)
    public static val H = a(c.f, c.B, c.f)
    public static val I = a(c.f, c.C, c.f)
    public static val J = a(c.f, c.I, c.f)

    constructor(Int i2) {
        super(i2)
    }

    fun a(c cVar, c cVar2) {
        b bVar = b(2)
        bVar.a(0, (Object) cVar)
        bVar.a(1, (Object) cVar2)
        return bVar
    }

    fun a(c cVar, c cVar2, c cVar3) {
        b bVar = b(3)
        bVar.a(0, (Object) cVar)
        bVar.a(1, (Object) cVar2)
        bVar.a(2, (Object) cVar3)
        return bVar
    }

    fun a(c cVar, c cVar2, c cVar3, c cVar4) {
        b bVar = b(4)
        bVar.a(0, (Object) cVar)
        bVar.a(1, (Object) cVar2)
        bVar.a(2, (Object) cVar3)
        bVar.a(3, (Object) cVar4)
        return bVar
    }

    fun a(e eVar) {
        Int iD_ = eVar.d_()
        if (iD_ == 0) {
            return "<empty>"
        }
        StringBuffer stringBuffer = StringBuffer(100)
        for (Int i2 = 0; i2 < iD_; i2++) {
            if (i2 != 0) {
                stringBuffer.append(", ")
            }
            stringBuffer.append(eVar.a(i2).d())
        }
        return stringBuffer.toString()
    }

    fun a(e eVar, e eVar2) {
        Int iD_ = eVar.d_()
        if (eVar2.d_() != iD_) {
            return false
        }
        for (Int i2 = 0; i2 < iD_; i2++) {
            if (!eVar.a(i2).equals(eVar2.a(i2))) {
                return false
            }
        }
        return true
    }

    fun b(e eVar) {
        Int iD_ = eVar.d_()
        Int iHashCode = 0
        for (Int i2 = 0; i2 < iD_; i2++) {
            iHashCode = (iHashCode * 31) + eVar.a(i2).hashCode()
        }
        return iHashCode
    }

    fun b(e eVar, e eVar2) {
        Int iD_ = eVar.d_()
        Int iD_2 = eVar2.d_()
        Int iMin = Math.min(iD_, iD_2)
        for (Int i2 = 0; i2 < iMin; i2++) {
            Int iCompareTo = eVar.a(i2).compareTo(eVar2.a(i2))
            if (iCompareTo != 0) {
                return iCompareTo
            }
        }
        if (iD_ == iD_2) {
            return 0
        }
        return iD_ < iD_2 ? -1 : 1
    }

    fun b(c cVar) {
        b bVar = b(1)
        bVar.a(0, (Object) cVar)
        return bVar
    }

    @Override // com.a.b.f.d.e
    public final c a(Int i2) {
        return b(i2)
    }

    @Override // com.a.b.f.d.e
    public final e a(c cVar) {
        Int iD_ = d_()
        b bVar = b(iD_ + 1)
        for (Int i2 = 0; i2 < iD_; i2++) {
            bVar.a(i2, e(i2))
        }
        bVar.a(iD_, (Object) cVar)
        bVar.b_()
        return bVar
    }

    public final Unit a(Int i2, c cVar) {
        a(i2, (Object) cVar)
    }

    public final c b(Int i2) {
        return (c) e(i2)
    }

    public final b c(c cVar) {
        Int iD_ = d_()
        b bVar = b(iD_ + 1)
        bVar.a(0, (Object) cVar)
        for (Int i2 = 0; i2 < iD_; i2++) {
            bVar.a(i2 + 1, f(i2))
        }
        return bVar
    }

    public final Int e() {
        Int iD_ = d_()
        Int i2 = 0
        for (Int i3 = 0; i3 < iD_; i3++) {
            i2 += b(i3).i()
        }
        return i2
    }
}

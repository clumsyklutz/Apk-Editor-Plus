package com.a.b.a.b

class o extends com.a.b.h.g {

    /* renamed from: a, reason: collision with root package name */
    public static val f215a = o(0)

    constructor(Int i) {
        super(i)
    }

    fun a(o oVar, o oVar2) {
        if (oVar == f215a) {
            return oVar2
        }
        Int iD_ = oVar.d_()
        Int iD_2 = oVar2.d_()
        o oVar3 = o(iD_ + iD_2)
        for (Int i = 0; i < iD_; i++) {
            oVar3.a(i, oVar.b(i))
        }
        for (Int i2 = 0; i2 < iD_2; i2++) {
            oVar3.a(iD_ + i2, oVar2.b(i2))
        }
        return oVar3
    }

    private fun a(Int i, p pVar) {
        if (pVar == null) {
            throw NullPointerException("item == null")
        }
        a(i, (Object) pVar)
    }

    private fun b(Int i) {
        return (p) e(i)
    }

    public final Int a(Int i) {
        Int iD_ = d_()
        Int i2 = 0
        Int i3 = -1
        Int iB = -1
        while (i2 < iD_) {
            p pVarB = b(i2)
            Int iA = pVarB.a()
            if (iA <= i && iA > i3) {
                iB = pVarB.b()
                if (iA == i) {
                    break
                }
            } else {
                iA = i3
            }
            i2++
            i3 = iA
        }
        return iB
    }

    public final Unit a(Int i, Int i2, Int i3) {
        a(i, (Object) p(i2, i3))
    }
}

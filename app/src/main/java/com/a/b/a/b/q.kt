package com.a.b.a.b

class q extends com.a.b.h.g {

    /* renamed from: a, reason: collision with root package name */
    public static val f218a = q(0)

    constructor(Int i) {
        super(i)
    }

    fun a(q qVar, q qVar2) {
        if (qVar == f218a) {
            return qVar2
        }
        Int iD_ = qVar.d_()
        Int iD_2 = qVar2.d_()
        q qVar3 = q(iD_ + iD_2)
        for (Int i = 0; i < iD_; i++) {
            qVar3.a(i, qVar.a(i))
        }
        for (Int i2 = 0; i2 < iD_2; i2++) {
            qVar3.a(iD_ + i2, qVar2.a(i2))
        }
        qVar3.b_()
        return qVar3
    }

    private fun a(Int i) {
        return (r) e(i)
    }

    private fun a(Int i, r rVar) {
        if (rVar == null) {
            throw NullPointerException("item == null")
        }
        a(i, (Object) rVar)
    }

    fun b(q qVar, q qVar2) {
        r rVar
        Int iD_ = qVar.d_()
        q qVar3 = q(iD_)
        for (Int i = 0; i < iD_; i++) {
            r rVarA = qVar.a(i)
            Int iD_2 = qVar2.d_()
            Int i2 = 0
            while (true) {
                if (i2 >= iD_2) {
                    rVar = null
                    break
                }
                rVar = (r) qVar2.e(i2)
                if (rVar != null && rVar.a(rVarA)) {
                    break
                }
                i2++
            }
            qVar3.a(i, rVar != null ? rVarA.a(rVar.e) : rVarA)
        }
        qVar3.b_()
        return qVar3
    }

    public final r a(Int i, Int i2) {
        Int iD_ = d_()
        for (Int i3 = 0; i3 < iD_; i3++) {
            r rVar = (r) e(i3)
            if (rVar != null && rVar.a(i, i2)) {
                return rVar
            }
        }
        return null
    }

    public final Unit a(Int i, Int i2, Int i3, com.a.b.f.c.y yVar, com.a.b.f.c.y yVar2, com.a.b.f.c.y yVar3, Int i4) {
        a(i, (Object) r(i2, i3, yVar, yVar2, yVar3, i4))
    }
}

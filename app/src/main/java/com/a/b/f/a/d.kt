package com.a.b.f.a

import com.a.b.h.g

class d extends g {

    /* renamed from: a, reason: collision with root package name */
    public static val f490a = d(0)

    constructor(Int i) {
        super(i)
    }

    fun a(d dVar, d dVar2) {
        Int iD_ = dVar.d_()
        if (iD_ != dVar2.d_()) {
            throw IllegalArgumentException("list1.size() != list2.size()")
        }
        d dVar3 = d(iD_)
        for (Int i = 0; i < iD_; i++) {
            dVar3.a(i, c.a(dVar.a(i), dVar2.a(i)))
        }
        dVar3.b_()
        return dVar3
    }

    public final c a(Int i) {
        return (c) e(i)
    }

    public final Unit a(Int i, c cVar) {
        cVar.m()
        a(i, (Object) cVar)
    }
}

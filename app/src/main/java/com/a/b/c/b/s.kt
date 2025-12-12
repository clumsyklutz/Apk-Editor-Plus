package com.a.b.c.b

class s extends com.a.b.h.g {

    /* renamed from: a, reason: collision with root package name */
    public static val f334a = s(0)

    constructor(Int i) {
        super(i)
    }

    fun a(m mVar) {
        Int iD_ = mVar.d_()
        v vVar = v(iD_)
        for (Int i = 0; i < iD_; i++) {
            l lVarA = mVar.a(i)
            if (lVarA is w) {
                vVar.a(lVarA.g(), ((w) lVarA).c())
            } else if (lVarA is x) {
                vVar.a(lVarA.g(), ((x) lVarA).c())
            }
        }
        return vVar.a()
    }

    public final u a(Int i) {
        return (u) e(i)
    }

    public final Unit a(Int i, u uVar) {
        a(i, (Object) uVar)
    }
}

package com.a.b.a.e

class k extends com.a.b.h.g implements b {
    constructor(Int i) {
        super(i)
    }

    private fun a(Int i) {
        return (a) e(i)
    }

    @Override // com.a.b.a.e.b
    public final a a(a aVar) {
        Int iD_ = d_()
        for (Int i = 0; i < iD_; i++) {
            if (a(i) == aVar) {
                String strG = aVar.g()
                for (Int i2 = i + 1; i2 < iD_; i2++) {
                    a aVarA = a(i2)
                    if (aVarA.g().equals(strG)) {
                        return aVarA
                    }
                }
                return null
            }
        }
        return null
    }

    @Override // com.a.b.a.e.b
    public final a a(String str) {
        Int iD_ = d_()
        for (Int i = 0; i < iD_; i++) {
            a aVarA = a(i)
            if (aVarA.g().equals(str)) {
                return aVarA
            }
        }
        return null
    }

    public final Unit a(Int i, a aVar) {
        a(i, (Object) aVar)
    }

    @Override // com.a.b.a.e.b
    public final Int b() {
        Int iD_ = d_()
        Int iA = 2
        for (Int i = 0; i < iD_; i++) {
            iA += a(i).a()
        }
        return iA
    }
}

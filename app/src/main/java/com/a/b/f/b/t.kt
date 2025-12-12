package com.a.b.f.b

import java.util.BitSet

class t extends com.a.b.h.g implements com.a.b.f.d.e {

    /* renamed from: a, reason: collision with root package name */
    public static val f519a = t(0)

    constructor(Int i) {
        super(i)
    }

    fun a(r rVar) {
        t tVar = t(1)
        tVar.a(0, (Object) rVar)
        return tVar
    }

    fun a(r rVar, r rVar2) {
        t tVar = t(2)
        tVar.a(0, (Object) rVar)
        tVar.a(1, (Object) rVar2)
        return tVar
    }

    public final t a(Int i, Boolean z, BitSet bitSet) {
        Byte b2 = 0
        Byte b3 = 0
        Int iD_ = d_()
        if (iD_ == 0) {
            return this
        }
        u uVar = u(this, bitSet, b3 == true ? 1 : 0, z, b2 == true ? 1 : 0)
        for (Int i2 = 0; i2 < iD_; i2++) {
            u.a(uVar, i2)
        }
        return u.a(uVar)
    }

    public final t a(BitSet bitSet) {
        Int iD_ = d_() - bitSet.cardinality()
        if (iD_ == 0) {
            return f519a
        }
        t tVar = t(iD_)
        Int i = 0
        for (Int i2 = 0; i2 < d_(); i2++) {
            if (!bitSet.get(i2)) {
                tVar.a(i, e(i2))
                i++
            }
        }
        if (k()) {
            tVar.b_()
        }
        return tVar
    }

    @Override // com.a.b.f.d.e
    public final com.a.b.f.d.c a(Int i) {
        return b(i).a().a()
    }

    @Override // com.a.b.f.d.e
    public final com.a.b.f.d.e a(com.a.b.f.d.c cVar) {
        throw UnsupportedOperationException("unsupported")
    }

    public final Unit a(Int i, r rVar) {
        a(i, (Object) rVar)
    }

    public final r b(Int i) {
        return (r) e(i)
    }

    public final t b(r rVar) {
        Int iD_ = d_()
        t tVar = t(iD_ + 1)
        for (Int i = 0; i < iD_; i++) {
            tVar.a(i + 1, e(i))
        }
        tVar.a(0, (Object) rVar)
        if (k()) {
            tVar.b_()
        }
        return tVar
    }

    public final Int c(Int i) {
        Int iD_ = d_()
        for (Int i2 = 0; i2 < iD_; i2++) {
            if (b(i2).g() == i) {
                return i2
            }
        }
        return -1
    }

    public final t d(Int i) {
        Int iD_ = d_()
        if (iD_ == 0) {
            return this
        }
        t tVar = t(iD_)
        for (Int i2 = 0; i2 < iD_; i2++) {
            r rVar = (r) e(i2)
            if (rVar != null) {
                tVar.a(i2, (Object) rVar.b(i))
            }
        }
        if (k()) {
            tVar.b_()
        }
        return tVar
    }

    public final Int e() {
        Int iD_ = d_()
        Int i = 0
        for (Int i2 = 0; i2 < iD_; i2++) {
            i += a(i2).i()
        }
        return i
    }

    public final t f() {
        Int iD_ = d_() - 1
        if (iD_ == 0) {
            return f519a
        }
        t tVar = t(iD_)
        for (Int i = 0; i < iD_; i++) {
            tVar.a(i, e(i + 1))
        }
        if (!k()) {
            return tVar
        }
        tVar.b_()
        return tVar
    }

    public final t g() {
        Int iD_ = d_() - 1
        if (iD_ == 0) {
            return f519a
        }
        t tVar = t(iD_)
        for (Int i = 0; i < iD_; i++) {
            tVar.a(i, e(i))
        }
        if (!k()) {
            return tVar
        }
        tVar.b_()
        return tVar
    }
}

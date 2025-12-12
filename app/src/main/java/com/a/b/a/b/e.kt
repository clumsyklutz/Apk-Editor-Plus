package com.a.b.a.b

class e extends com.a.b.h.g {

    /* renamed from: a, reason: collision with root package name */
    public static val f199a = e(0)

    constructor(Int i) {
        super(i)
    }

    public final f a(Int i) {
        return (f) e(i)
    }

    public final Unit a(Int i, Int i2, Int i3, Int i4, com.a.b.f.c.z zVar) {
        a(i, f(i2, i3, i4, zVar))
    }

    public final com.a.b.f.d.e a_() {
        Int iD_ = d_()
        if (iD_ == 0) {
            return com.a.b.f.d.b.f565a
        }
        com.a.b.f.d.b bVar = new com.a.b.f.d.b(iD_)
        for (Int i = 0; i < iD_; i++) {
            bVar.a(i, a(i).d().i())
        }
        bVar.b_()
        return bVar
    }

    public final e b(Int i) {
        Boolean z
        Int iD_ = d_()
        Array<f> fVarArr = new f[iD_]
        Int i2 = 0
        for (Int i3 = 0; i3 < iD_; i3++) {
            f fVarA = a(i3)
            if (fVarA.a(i)) {
                com.a.b.f.c.z zVarD = fVarA.d()
                for (Int i4 = 0; i4 < i2; i4++) {
                    com.a.b.f.c.z zVarD2 = fVarArr[i4].d()
                    if (zVarD2 == zVarD || zVarD2 == com.a.b.f.c.z.f561a) {
                        z = false
                        break
                    }
                }
                z = true
                if (z) {
                    fVarArr[i2] = fVarA
                    i2++
                }
            }
        }
        if (i2 == 0) {
            return f199a
        }
        e eVar = e(i2)
        for (Int i5 = 0; i5 < i2; i5++) {
            f fVar = fVarArr[i5]
            if (fVar == null) {
                throw NullPointerException("item == null")
            }
            eVar.a(i5, fVar)
        }
        eVar.b_()
        return eVar
    }
}

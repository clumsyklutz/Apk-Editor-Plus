package com.a.b.c.b

class ab extends com.a.b.h.g {

    /* renamed from: a, reason: collision with root package name */
    private static ab f297a = ab(0)

    private constructor(Int i) {
        super(i)
    }

    fun a(m mVar, Int i) {
        switch (i) {
            case 1:
                return f297a
            case 2:
            case 3:
                com.a.b.f.b.z zVar = com.a.b.f.b.z.f530a
                Int iD_ = mVar.d_()
                Array<ac> acVarArr = new ac[iD_]
                Int i2 = 0
                Boolean z = false
                com.a.b.f.b.z zVar2 = zVar
                for (Int i3 = 0; i3 < iD_; i3++) {
                    l lVarA = mVar.a(i3)
                    if (lVarA is h) {
                        z = true
                    } else {
                        com.a.b.f.b.z zVarI = lVarA.i()
                        if (!zVarI.equals(zVar) && !zVarI.a(zVar2) && (i != 3 || z)) {
                            acVarArr[i2] = ac(lVarA.g(), zVarI)
                            i2++
                            z = false
                            zVar2 = zVarI
                        }
                    }
                }
                ab abVar = ab(i2)
                for (Int i4 = 0; i4 < i2; i4++) {
                    abVar.a(i4, acVarArr[i4])
                }
                abVar.b_()
                return abVar
            default:
                throw IllegalArgumentException("bogus howMuch")
        }
    }

    public final ac a(Int i) {
        return (ac) e(i)
    }
}

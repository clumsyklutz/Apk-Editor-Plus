package com.a.b.e

import com.a.b.d.a.aw

final class o {

    /* renamed from: a, reason: collision with root package name */
    private final m f475a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.d.a f476b = new com.a.b.d.a()
    private com.a.b.d.a.Array<f> c
    private Int d

    constructor(m mVar) {
        Byte b2 = 0
        this.f475a = mVar
        this.f476b.a(q(this, b2))
        this.f476b.b(s(this, b2))
        this.f476b.c(t(this, b2))
        this.f476b.d(p(this, b2))
        this.f476b.e(r(this, b2))
    }

    static /* synthetic */ Unit a(Boolean z, Int i) {
        if (!z && i > 65535) {
            throw new com.a.a.t("Cannot merge new index " + i + " into a non-jumbo instruction!")
        }
    }

    static /* synthetic */ Int b(o oVar) {
        Int i = oVar.d
        oVar.d = i + 1
        return i
    }

    public final Array<Short> a(Array<Short> sArr) {
        com.a.b.d.a.Array<f> fVarArrA = com.a.b.d.a.f.a(sArr)
        Int length = fVarArrA.length
        this.c = new com.a.b.d.a.f[length]
        this.d = 0
        this.f476b.a(fVarArrA)
        aw awVar = aw(length)
        for (com.a.b.d.a.f fVar : this.c) {
            if (fVar != null) {
                fVar.a(awVar)
            }
        }
        return awVar.c()
    }
}

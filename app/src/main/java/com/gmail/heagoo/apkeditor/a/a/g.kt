package com.gmail.heagoo.apkeditor.a.a

final class g implements d {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ e f788a

    g(e eVar) {
        this.f788a = eVar
    }

    @Override // com.gmail.heagoo.apkeditor.a.a.d
    public final Unit a(x xVar) {
        if (xVar.b() == 3) {
            Array<Int> iArrB = this.f788a.g.b()
            Int iC = xVar.c()
            if (iC < 0 || iC >= iArrB.length) {
                return
            }
            Int i = iArrB[iC] + 1
            e.a(xVar.a(), xVar.f816a + 8, i)
            e.a(xVar.a(), xVar.f816a + 16, i)
        }
    }
}

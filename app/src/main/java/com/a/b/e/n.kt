package com.a.b.e

final class n {

    /* renamed from: a, reason: collision with root package name */
    private final com.a.a.a.c f473a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ m f474b

    constructor(m mVar, com.a.a.a.c cVar) {
        this.f474b = mVar
        this.f473a = cVar
    }

    private fun a(Int i, Int i2) {
        this.f473a.d((i2 << 5) | i)
    }

    private fun a(com.a.a.v vVar) {
        switch (vVar.a()) {
            case 0:
                com.gmail.heagoo.a.c.a.a(this.f473a, 0, vVar.f())
                return
            case 1:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw new com.a.a.s("Unexpected type: " + Integer.toHexString(vVar.a()))
            case 2:
                com.gmail.heagoo.a.c.a.a(this.f473a, 2, vVar.g())
                return
            case 3:
                com.gmail.heagoo.a.c.a.b(this.f473a, 3, vVar.h())
                return
            case 4:
                com.gmail.heagoo.a.c.a.a(this.f473a, 4, vVar.i())
                return
            case 6:
                com.gmail.heagoo.a.c.a.a(this.f473a, 6, vVar.j())
                return
            case 16:
                com.gmail.heagoo.a.c.a.c(this.f473a, 16, Float.floatToIntBits(vVar.k()) << 32)
                return
            case 17:
                com.gmail.heagoo.a.c.a.c(this.f473a, 17, Double.doubleToLongBits(vVar.l()))
                return
            case 23:
                com.gmail.heagoo.a.c.a.b(this.f473a, 23, this.f474b.a(vVar.m()))
                return
            case 24:
                com.gmail.heagoo.a.c.a.b(this.f473a, 24, this.f474b.b(vVar.n()))
                return
            case 25:
                com.gmail.heagoo.a.c.a.b(this.f473a, 25, this.f474b.c(vVar.o()))
                return
            case 26:
                com.gmail.heagoo.a.c.a.b(this.f473a, 26, this.f474b.d(vVar.q()))
                return
            case 27:
                com.gmail.heagoo.a.c.a.b(this.f473a, 27, this.f474b.c(vVar.p()))
                return
            case 28:
                a(28, 0)
                c(vVar)
                return
            case 29:
                a(29, 0)
                b(vVar)
                return
            case 30:
                vVar.r()
                a(30, 0)
                return
            case 31:
                a(31, vVar.s() ? 1 : 0)
                return
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun b(com.a.a.v vVar) {
        Int iC = vVar.c()
        com.gmail.heagoo.a.c.a.a(this.f473a, this.f474b.b(vVar.d()))
        com.gmail.heagoo.a.c.a.a(this.f473a, iC)
        for (Int i = 0; i < iC; i++) {
            com.gmail.heagoo.a.c.a.a(this.f473a, this.f474b.a(vVar.e()))
            a(vVar)
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun c(com.a.a.v vVar) {
        Int iB = vVar.b()
        com.gmail.heagoo.a.c.a.a(this.f473a, iB)
        for (Int i = 0; i < iB; i++) {
            a(vVar)
        }
    }
}

package com.a.b.a.b

final class ac extends ad {

    /* renamed from: b, reason: collision with root package name */
    private Int f181b

    ac(x xVar) {
        super(xVar.d)
        this.f181b = xVar.d + xVar.f225a.l().d_()
    }

    @Override // com.a.b.a.b.ad
    final Int a() {
        if (this.f182a >= this.f181b) {
            throw IndexOutOfBoundsException()
        }
        Int i = this.f182a
        this.f182a = i + 1
        return i
    }
}

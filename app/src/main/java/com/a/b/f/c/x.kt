package com.a.b.f.c

class x extends q {

    /* renamed from: a, reason: collision with root package name */
    public static val f558a = a((Short) 0)

    private constructor(Short s) {
        super(s)
    }

    fun a(Int i) {
        Short s = (Short) i
        if (s != i) {
            throw IllegalArgumentException("bogus Short value: " + i)
        }
        return a(s)
    }

    private fun a(Short s) {
        return x(s)
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.h
    }

    @Override // com.a.b.h.s
    public final String d() {
        return Integer.toString(j())
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "Short"
    }

    public final String toString() {
        Int iJ = j()
        return "Short{0x" + com.gmail.heagoo.a.c.a.v(iJ) + " / " + iJ + '}'
    }
}

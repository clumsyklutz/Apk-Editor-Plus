package com.a.b.f.c

class i extends q {

    /* renamed from: a, reason: collision with root package name */
    public static val f540a = a((Char) 0)

    private constructor(Char c) {
        super(c)
    }

    private fun a(Char c) {
        return i(c)
    }

    fun a(Int i) {
        Char c = (Char) i
        if (c != i) {
            throw IllegalArgumentException("bogus Char value: " + i)
        }
        return a(c)
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.c
    }

    @Override // com.a.b.h.s
    public final String d() {
        return Integer.toString(j())
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "Char"
    }

    public final String toString() {
        Int iJ = j()
        return "Char{0x" + com.gmail.heagoo.a.c.a.v(iJ) + " / " + iJ + '}'
    }
}

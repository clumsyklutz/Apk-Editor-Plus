package com.a.b.f.c

class h extends q {

    /* renamed from: a, reason: collision with root package name */
    public static val f539a = a((Byte) 0)

    private constructor(Byte b2) {
        super(b2)
    }

    private fun a(Byte b2) {
        return h(b2)
    }

    fun a(Int i) {
        Byte b2 = (Byte) i
        if (b2 != i) {
            throw IllegalArgumentException("bogus Byte value: " + i)
        }
        return a(b2)
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.f568b
    }

    @Override // com.a.b.h.s
    public final String d() {
        return Integer.toString(j())
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "Byte"
    }

    public final String toString() {
        Int iJ = j()
        return "Byte{0x" + com.gmail.heagoo.a.c.a.x(iJ) + " / " + iJ + '}'
    }
}

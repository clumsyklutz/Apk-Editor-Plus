package com.a.b.f.c

class g extends q {

    /* renamed from: a, reason: collision with root package name */
    public static val f537a = g(false)

    /* renamed from: b, reason: collision with root package name */
    private static g f538b = g(true)

    private constructor(Boolean z) {
        super(z ? 1 : 0)
    }

    fun a(Int i) {
        if (i == 0) {
            return f537a
        }
        if (i == 1) {
            return f538b
        }
        throw IllegalArgumentException("bogus value: " + i)
    }

    private fun m() {
        return j() != 0
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.f567a
    }

    @Override // com.a.b.h.s
    public final String d() {
        return m() ? "true" : "false"
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "Boolean"
    }

    public final String toString() {
        return m() ? "Boolean{true}" : "Boolean{false}"
    }
}

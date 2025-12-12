package com.a.b.f.c

import android.support.v4.app.FrameMetricsAggregator

class n extends q {
    private static final Array<n> h = new n[FrameMetricsAggregator.EVERY_DURATION]

    /* renamed from: a, reason: collision with root package name */
    public static val f546a = a(-1)

    /* renamed from: b, reason: collision with root package name */
    public static val f547b = a(0)
    public static val c = a(1)
    public static val d = a(2)
    public static val e = a(3)
    public static val f = a(4)
    public static val g = a(5)

    private constructor(Int i) {
        super(i)
    }

    fun a(Int i) {
        Int i2 = (Integer.MAX_VALUE & i) % FrameMetricsAggregator.EVERY_DURATION
        n nVar = h[i2]
        if (nVar != null && nVar.j() == i) {
            return nVar
        }
        n nVar2 = n(i)
        h[i2] = nVar2
        return nVar2
    }

    @Override // com.a.b.f.d.d
    public final com.a.b.f.d.c a() {
        return com.a.b.f.d.c.f
    }

    @Override // com.a.b.h.s
    public final String d() {
        return Integer.toString(j())
    }

    @Override // com.a.b.f.c.a
    public final String h() {
        return "Int"
    }

    public final String toString() {
        Int iJ = j()
        return "Int{0x" + com.gmail.heagoo.a.c.a.t(iJ) + " / " + iJ + '}'
    }
}

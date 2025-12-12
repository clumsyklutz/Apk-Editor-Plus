package com.a.b.g

import java.util.EnumSet

class aa {

    /* renamed from: a, reason: collision with root package name */
    private static Boolean f591a = true

    /* renamed from: b, reason: collision with root package name */
    private static com.a.b.f.b.ad f592b

    public static com.a.b.f.b.x a(com.a.b.f.b.x xVar, Int i, Boolean z, Boolean z2, com.a.b.f.b.ad adVar) {
        EnumSet enumSetAllOf = EnumSet.allOf(ab.class)
        f591a = z2
        f592b = adVar
        an anVarA = e.a(xVar, i, z)
        a(anVarA, enumSetAllOf)
        com.a.b.f.b.x xVarA = com.a.b.g.a.n.a(anVarA, false)
        if (xVarA.a().e() <= 16) {
            return xVarA
        }
        an anVarA2 = e.a(xVar, i, z)
        EnumSet enumSetClone = enumSetAllOf.clone()
        enumSetClone.remove(ab.d)
        a(anVarA2, enumSetClone)
        return com.a.b.g.a.n.a(anVarA2, true)
    }

    private fun a(an anVar, EnumSet enumSet) {
        Boolean z = false
        Boolean z2 = true
        if (enumSet.contains(ab.f593a)) {
            w.a(anVar)
        }
        if (enumSet.contains(ab.f594b)) {
            ah.a(anVar)
            e.a(anVar)
            z2 = false
        }
        if (enumSet.contains(ab.c)) {
            s.a(anVar)
            e.a(anVar)
            z2 = false
        }
        enumSet.remove(ab.e)
        if (enumSet.contains(ab.e)) {
            l.a(anVar)
            e.a(anVar)
            z2 = false
        }
        if (enumSet.contains(ab.d)) {
            b.a(anVar)
            e.a(anVar)
        } else {
            z = z2
        }
        if (z) {
            e.a(anVar)
        }
        af.a(anVar)
    }

    fun a() {
        return f591a
    }

    public static com.a.b.f.b.ad b() {
        return f592b
    }
}

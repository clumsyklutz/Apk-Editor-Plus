package com.a.b.a.d

import com.a.b.a.a.w
import com.a.b.f.c.y

class b {
    public final com.a.b.a.e.a a(k kVar, Int i, Int i2, com.a.b.a.e.j jVar) {
        if (kVar == null) {
            throw NullPointerException("cf == null")
        }
        if (i < 0 || i >= 4) {
            throw IllegalArgumentException("bad context")
        }
        y yVar = null
        try {
            com.a.b.h.c cVarB = kVar.b()
            com.a.b.f.c.b bVarG = kVar.g()
            Int iF = cVarB.f(i2)
            Int iC = cVarB.c(i2 + 2)
            yVar = (y) bVarG.a(iF)
            if (jVar != null) {
                StringBuilder("name: ").append(yVar.d())
                StringBuilder("length: ").append(com.gmail.heagoo.a.c.a.t(iC))
            }
            return a(kVar, i, yVar.j(), i2 + 6, iC, jVar)
        } catch (com.a.b.a.e.i e) {
            e.a("...while parsing " + (yVar != null ? yVar.d() + " " : "") + "attribute at offset " + com.gmail.heagoo.a.c.a.t(i2))
            throw e
        }
    }

    protected com.a.b.a.e.a a(k kVar, Int i, String str, Int i2, Int i3, com.a.b.a.e.j jVar) {
        return w(str, kVar.b(), i2, i3, kVar.g())
    }
}

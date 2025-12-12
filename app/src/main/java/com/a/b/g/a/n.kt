package com.a.b.g.a

import com.a.b.f.b.w
import com.a.b.f.b.x
import com.a.b.f.b.y
import com.a.b.g.ag
import com.a.b.g.ai
import com.a.b.g.ak
import com.a.b.g.al
import com.a.b.g.an
import java.util.ArrayList
import java.util.Iterator

class n {

    /* renamed from: a, reason: collision with root package name */
    private final an f587a

    /* renamed from: b, reason: collision with root package name */
    private final Boolean f588b
    private final i c

    private constructor(an anVar, Boolean z) {
        this.f588b = z
        this.f587a = anVar
        this.c = j.a(anVar)
    }

    private com.a.b.f.b.c a() {
        Int i
        com.a.b.h.j jVar
        Int i2 = 0
        ArrayList arrayListJ = this.f587a.j()
        ai aiVarF = this.f587a.f()
        this.f587a.l()
        com.a.b.f.b.c cVar = new com.a.b.f.b.c(this.f587a.k() - ((aiVarF == null || !aiVarF.r()) ? 0 : 1))
        Iterator it = arrayListJ.iterator()
        while (true) {
            Int i3 = i2
            if (!it.hasNext()) {
                if (aiVarF == null || aiVarF.c().size() == 0) {
                    return cVar
                }
                throw RuntimeException("Exit block must have no insns when leaving SSA form")
            }
            ai aiVar = (ai) it.next()
            if (!aiVar.r() || aiVar == aiVarF) {
                i2 = i3
            } else {
                Int i4 = i3 + 1
                com.a.b.h.j jVarM = aiVar.m()
                Int iK = aiVar.k()
                ai aiVarF2 = this.f587a.f()
                if (!jVarM.h(aiVarF2 == null ? -1 : aiVarF2.f())) {
                    i = iK
                    jVar = jVarM
                } else {
                    if (jVarM.b() > 1) {
                        throw RuntimeException("Exit predecessor must have no other successors" + com.gmail.heagoo.a.c.a.v(aiVar.f()))
                    }
                    jVar = com.a.b.h.j.f673a
                    w wVarC = ((al) aiVar.c().get(r1.size() - 1)).c()
                    if (wVarC.d() != 2 && wVarC != y.bi) {
                        throw RuntimeException("Exit predecessor must end in valid exit statement.")
                    }
                    i = -1
                }
                jVar.b_()
                cVar.a(i3, new com.a.b.f.b.a(aiVar.f(), a(aiVar.c()), jVar, i))
                i2 = i4
            }
        }
    }

    private static com.a.b.f.b.l a(ArrayList arrayList) {
        Int size = arrayList.size()
        com.a.b.f.b.l lVar = new com.a.b.f.b.l(size)
        for (Int i = 0; i < size; i++) {
            lVar.a(i, ((al) arrayList.get(i)).b())
        }
        lVar.b_()
        return lVar
    }

    fun a(an anVar, Boolean z) {
        n nVar = n(anVar, z)
        ag agVarA = a(nVar.f587a, nVar.c, nVar.f588b).a()
        nVar.f587a.p()
        nVar.f587a.a(agVarA)
        ArrayList arrayListJ = nVar.f587a.j()
        Iterator it = arrayListJ.iterator()
        while (it.hasNext()) {
            ai aiVar = (ai) it.next()
            aiVar.a(p(arrayListJ))
            aiVar.b()
        }
        Iterator it2 = arrayListJ.iterator()
        while (it2.hasNext()) {
            ((ai) it2.next()).s()
        }
        Int iH = nVar.f587a.h()
        com.a.b.g.a aVar = new com.a.b.g.a(nVar.f587a.g())
        Int iG = nVar.f587a.g()
        for (Int i = 0; i < iG; i++) {
            if (i < iH) {
                aVar.a(i, (iG - iH) + i, 1)
            } else {
                aVar.a(i, i - iH, 1)
            }
        }
        nVar.f587a.a(aVar)
        nVar.f587a.a(false, (ak) o(nVar, nVar.f587a.j()))
        return h(x(nVar.a(), nVar.f587a.a(nVar.f587a.c()))).a()
    }
}

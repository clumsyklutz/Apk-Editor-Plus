package com.a.b.g.a

import com.a.b.f.b.q
import com.a.b.f.b.r
import com.a.b.f.b.t
import com.a.b.f.b.y
import com.a.b.f.b.z
import com.a.b.g.ag
import com.a.b.g.ai
import com.a.b.g.al
import com.a.b.g.an
import java.util.ArrayList

abstract class m {

    /* renamed from: a, reason: collision with root package name */
    protected final an f585a

    /* renamed from: b, reason: collision with root package name */
    private i f586b

    constructor(an anVar, i iVar) {
        this.f585a = anVar
        this.f586b = iVar
    }

    protected final r a(al alVar, r rVar) {
        ai aiVarO = alVar.o()
        ArrayList arrayListC = aiVarO.c()
        Int iIndexOf = arrayListC.indexOf(alVar)
        if (iIndexOf < 0) {
            throw IllegalArgumentException("specified insn is not in this block")
        }
        if (iIndexOf != arrayListC.size() - 1) {
            throw IllegalArgumentException("Adding move here not supported:" + alVar.d())
        }
        r rVarA = r.a(this.f585a.o(), rVar.h())
        arrayListC.add(iIndexOf, al.a(q(y.a(rVarA.a()), z.f530a, rVarA, t.a(rVar)), aiVarO))
        Int iG = rVarA.g()
        com.a.b.h.i iVarB = aiVarO.p().b()
        while (iVarB.a()) {
            this.f586b.a(iG, iVarB.b())
        }
        t tVarA = alVar.a()
        Int iD_ = tVarA.d_()
        for (Int i = 0; i < iD_; i++) {
            this.f586b.a(iG, tVarA.b(i).g())
        }
        this.f585a.m()
        return rVarA
    }

    public abstract ag a()

    protected final r b(Int i) {
        al alVarC = this.f585a.c(i)
        if (alVarC == null) {
            return null
        }
        return alVarC.n()
    }
}

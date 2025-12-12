package com.a.b.g

import java.util.ArrayList
import java.util.HashMap

class v extends com.a.b.h.p {

    /* renamed from: a, reason: collision with root package name */
    private final Int f653a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.b.v f654b
    private final com.a.b.f.b.Array<v> c
    private final HashMap d

    constructor(an anVar) {
        if (anVar == null) {
            throw NullPointerException("method == null")
        }
        ArrayList arrayListJ = anVar.j()
        this.f653a = anVar.g()
        this.f654b = new com.a.b.f.b.v(this.f653a)
        this.c = new com.a.b.f.b.v[arrayListJ.size()]
        this.d = HashMap()
        this.f654b.b_()
    }

    private fun b(Int i, com.a.b.f.b.v vVar) {
        l()
        if (vVar == null) {
            throw NullPointerException("specs == null")
        }
        try {
            this.c[i] = vVar
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("bogus index")
        }
    }

    private com.a.b.f.b.v c(Int i) {
        try {
            return this.c[i]
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("bogus index")
        }
    }

    public final com.a.b.f.b.v a(Int i) {
        com.a.b.f.b.v vVarC = c(i)
        return vVarC != null ? vVarC : this.f654b
    }

    public final Unit a(al alVar, com.a.b.f.b.r rVar) {
        l()
        if (alVar == null) {
            throw NullPointerException("insn == null")
        }
        if (rVar == null) {
            throw NullPointerException("spec == null")
        }
        this.d.put(alVar, rVar)
    }

    public final Boolean a(Int i, com.a.b.f.b.v vVar) {
        com.a.b.f.b.v vVarC = c(i)
        if (vVarC == null) {
            b(i, vVar)
            return true
        }
        com.a.b.f.b.v vVarE = vVarC.e()
        vVarE.a(vVar, true)
        if (vVarC.equals(vVarE)) {
            return false
        }
        vVarE.b_()
        b(i, vVarE)
        return true
    }

    public final com.a.b.f.b.v b(Int i) {
        com.a.b.f.b.v vVarC = c(i)
        return vVarC != null ? vVarC.e() : new com.a.b.f.b.v(this.f653a)
    }
}

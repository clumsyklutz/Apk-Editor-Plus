package com.a.b.f.b

import java.util.HashMap

class o extends com.a.b.h.p {

    /* renamed from: a, reason: collision with root package name */
    private final Int f513a

    /* renamed from: b, reason: collision with root package name */
    private final v f514b
    private final Array<v> c
    private final HashMap d

    constructor(x xVar) {
        if (xVar == null) {
            throw NullPointerException("method == null")
        }
        c cVarA = xVar.a()
        Int iJ = cVarA.j()
        this.f513a = cVarA.e()
        this.f514b = v(this.f513a)
        this.c = new v[iJ]
        this.d = HashMap(cVarA.f())
        this.f514b.b_()
    }

    private fun b(Int i, v vVar) {
        l()
        if (vVar == null) {
            throw NullPointerException("specs == null")
        }
        try {
            this.c[i] = vVar
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("bogus label")
        }
    }

    private fun c(Int i) {
        try {
            return this.c[i]
        } catch (ArrayIndexOutOfBoundsException e) {
            throw IllegalArgumentException("bogus label")
        }
    }

    public final r a(i iVar) {
        return (r) this.d.get(iVar)
    }

    public final v a(Int i) {
        v vVarC = c(i)
        return vVarC != null ? vVarC : this.f514b
    }

    public final Unit a(i iVar, r rVar) {
        l()
        if (iVar == null) {
            throw NullPointerException("insn == null")
        }
        if (rVar == null) {
            throw NullPointerException("spec == null")
        }
        this.d.put(iVar, rVar)
    }

    public final Boolean a(Int i, v vVar) {
        v vVarC = c(i)
        if (vVarC == null) {
            b(i, vVar)
            return true
        }
        v vVarE = vVarC.e()
        if (vVarC.d() != 0) {
            vVarE.a(vVar, true)
        } else {
            vVarE = vVar.e()
        }
        if (vVarC.equals(vVarE)) {
            return false
        }
        vVarE.b_()
        b(i, vVarE)
        return true
    }

    public final Int b() {
        return this.d.size()
    }

    public final v b(Int i) {
        v vVarC = c(i)
        return vVarC != null ? vVarC.e() : v(this.f513a)
    }
}

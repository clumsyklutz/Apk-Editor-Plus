package com.a.b.g

import androidx.appcompat.R

class z extends al implements Cloneable {

    /* renamed from: a, reason: collision with root package name */
    private com.a.b.f.b.i f660a

    z(com.a.b.f.b.i iVar, ai aiVar) {
        super(iVar.h(), aiVar)
        this.f660a = iVar
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.t a() {
        return this.f660a.j()
    }

    public final Unit a(Int i, com.a.b.f.b.r rVar) {
        com.a.b.f.b.t tVarJ = this.f660a.j()
        Int iD_ = tVarJ.d_()
        com.a.b.f.b.t tVar = new com.a.b.f.b.t(iD_)
        Int i2 = 0
        while (i2 < iD_) {
            tVar.a(i2, i2 == i ? rVar : tVarJ.b(i2))
            i2++
        }
        tVar.b_()
        com.a.b.f.b.r rVarB = tVarJ.b(i)
        if (rVarB.g() != rVar.g()) {
            o().n().a(this, rVarB, rVar)
        }
        this.f660a = this.f660a.a(n(), tVar)
    }

    public final Unit a(com.a.b.f.b.t tVar) {
        if (this.f660a.j().d_() != tVar.d_()) {
            throw RuntimeException("Sources counts don't match")
        }
        this.f660a = this.f660a.a(n(), tVar)
    }

    @Override // com.a.b.g.al
    public final Unit a(ag agVar) {
        com.a.b.f.b.t tVarJ = this.f660a.j()
        com.a.b.f.b.t tVarA = agVar.a(tVarJ)
        if (tVarA != tVarJ) {
            this.f660a = this.f660a.a(n(), tVarA)
            o().n().a(this, tVarJ)
        }
    }

    @Override // com.a.b.g.al
    public final Unit a(am amVar) {
        if (h()) {
            amVar.a(this)
        } else {
            amVar.b(this)
        }
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.i b() {
        return this.f660a.a(n(), this.f660a.j())
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.w c() {
        return this.f660a.f()
    }

    @Override // com.a.b.g.al
    public final /* synthetic */ Object clone() {
        return (z) super.clone()
    }

    @Override // com.a.b.h.s
    public final String d() {
        return b().d()
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.i e() {
        return this.f660a
    }

    @Override // com.a.b.g.al
    public final com.a.b.f.b.r f() {
        com.a.b.f.b.r rVarB = this.f660a.f().a() == 54 ? this.f660a.j().b(0) : n()
        if (rVarB == null || rVarB.i() == null) {
            return null
        }
        return rVarB
    }

    public final Unit g() {
        com.a.b.f.b.t tVarJ = this.f660a.j()
        this.f660a = this.f660a.l()
        o().n().a(this, tVarJ)
    }

    @Override // com.a.b.g.al
    public final Boolean h() {
        return this.f660a.f().a() == 2
    }

    @Override // com.a.b.g.al
    public final Boolean i() {
        return this.f660a.f().a() == 4
    }

    @Override // com.a.b.g.al
    public final Boolean j() {
        return this.f660a.k()
    }

    @Override // com.a.b.g.al
    public final Boolean k() {
        return h()
    }

    @Override // com.a.b.g.al
    public final Boolean l() {
        com.a.b.f.b.w wVarF = this.f660a.f()
        if (wVarF.d() != 1) {
            return true
        }
        Boolean z = aa.a() && f() != null
        switch (wVarF.a()) {
            case 2:
            case 5:
            case R.styleable.AppCompatTheme_selectableItemBackgroundBorderless /* 55 */:
                return z
            default:
                return true
        }
    }

    @Override // com.a.b.g.al
    /* renamed from: m */
    public final /* bridge */ /* synthetic */ al clone() {
        return (z) super.clone()
    }
}

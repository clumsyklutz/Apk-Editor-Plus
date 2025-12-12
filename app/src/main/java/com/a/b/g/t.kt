package com.a.b.g

final class t implements am {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ com.a.b.f.b.ad f649a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ s f650b

    t(s sVar, com.a.b.f.b.ad adVar) {
        this.f650b = sVar
        this.f649a = adVar
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        com.a.b.f.b.w wVarF = zVar.e().f()
        com.a.b.f.b.t tVarA = zVar.a()
        if (!s.a(this.f650b, zVar) && tVarA.d_() == 2) {
            if (wVarF.d() == 4) {
                if (s.a(tVarA.b(0))) {
                    this.f650b.a(zVar, tVarA.f(), com.gmail.heagoo.a.c.a.r(wVarF.a()), null)
                    return
                } else {
                    if (s.a(tVarA.b(1))) {
                        this.f650b.a(zVar, tVarA.g(), wVarF.a(), null)
                        return
                    }
                    return
                }
            }
            if (this.f649a.a(wVarF, tVarA.b(0), tVarA.b(1))) {
                zVar.g()
            } else if (wVarF.f() && this.f649a.a(wVarF, tVarA.b(1), tVarA.b(0))) {
                zVar.a(com.a.b.f.b.t.a(tVarA.b(1), tVarA.b(0)))
                zVar.g()
            }
        }
    }
}

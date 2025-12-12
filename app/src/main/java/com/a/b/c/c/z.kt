package com.a.b.c.c

import androidx.appcompat.R

class z extends ac {
    @Override // com.a.b.c.c.ad
    public final ae a() {
        return ae.f350a
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar) {
    }

    @Override // com.a.b.c.c.ad
    public final Unit a(r rVar, com.a.b.h.r rVar2) {
        Int iG = rVar.g().g()
        at atVarP = rVar.p()
        at atVarQ = rVar.q()
        Int iG2 = atVarP.g()
        Int iG3 = (atVarQ.g() + atVarQ.f_()) - iG2
        String strC = com.gmail.heagoo.a.c.a.c(rVar.b().f263b)
        if (rVar2.b()) {
            rVar2.a(8, "magic: " + new com.a.b.f.c.y(strC).i())
            rVar2.a(4, "checksum")
            rVar2.a(20, "signature")
            rVar2.a(4, "file_size:       " + com.gmail.heagoo.a.c.a.t(rVar.c()))
            rVar2.a(4, "header_size:     " + com.gmail.heagoo.a.c.a.t(R.styleable.AppCompatTheme_ratingBarStyleSmall))
            rVar2.a(4, "endian_tag:      " + com.gmail.heagoo.a.c.a.t(305419896))
            rVar2.a(4, "link_size:       0")
            rVar2.a(4, "link_off:        0")
            rVar2.a(4, "map_off:         " + com.gmail.heagoo.a.c.a.t(iG))
        }
        for (Int i = 0; i < 8; i++) {
            rVar2.d(strC.charAt(i))
        }
        rVar2.f(24)
        rVar2.c(rVar.c())
        rVar2.c(R.styleable.AppCompatTheme_ratingBarStyleSmall)
        rVar2.c(305419896)
        rVar2.f(8)
        rVar2.c(iG)
        rVar.h().b(rVar2)
        rVar.k().b(rVar2)
        rVar.l().b(rVar2)
        rVar.m().a(rVar2)
        rVar.n().a(rVar2)
        rVar.i().a(rVar2)
        if (rVar2.b()) {
            rVar2.a(4, "data_size:       " + com.gmail.heagoo.a.c.a.t(iG3))
            rVar2.a(4, "data_off:        " + com.gmail.heagoo.a.c.a.t(iG2))
        }
        rVar2.c(iG3)
        rVar2.c(iG2)
    }

    @Override // com.a.b.c.c.ad
    public final Int e_() {
        return R.styleable.AppCompatTheme_ratingBarStyleSmall
    }
}

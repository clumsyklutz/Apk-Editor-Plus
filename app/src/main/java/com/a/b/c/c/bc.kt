package com.a.b.c.c

import java.util.Collection
import java.util.Iterator

abstract class bc extends at {
    constructor(String str, r rVar, Int i) {
        super(str, rVar, 4)
    }

    @Override // com.a.b.c.c.at
    public final Int a(ad adVar) {
        ac acVar = (ac) adVar
        return c(acVar.i() * acVar.e_())
    }

    @Override // com.a.b.c.c.at
    protected final Unit a_(com.a.b.h.r rVar) {
        r rVarE = e()
        Int iF = f()
        Iterator it = a().iterator()
        while (it.hasNext()) {
            ((ad) it.next()).a(rVarE, rVar)
            rVar.g(iF)
        }
    }

    protected abstract Unit b()

    @Override // com.a.b.c.c.at
    protected final Unit c() {
        r rVarE = e()
        b()
        Iterator it = a().iterator()
        while (it.hasNext()) {
            ((ad) it.next()).a(rVarE)
        }
    }

    @Override // com.a.b.c.c.at
    public final Int f_() {
        Collection collectionA = a()
        Int size = collectionA.size()
        if (size == 0) {
            return 0
        }
        return ((ad) collectionA.iterator().next()).e_() * size
    }
}

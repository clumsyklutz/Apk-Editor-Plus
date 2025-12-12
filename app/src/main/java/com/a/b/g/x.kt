package com.a.b.g

import java.util.HashSet
import java.util.List

final class x implements am {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ w f656a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ com.a.b.f.b.Array<r> f657b
    private /* synthetic */ HashSet c

    x(w wVar, com.a.b.f.b.Array<r> rVarArr, HashSet hashSet) {
        this.f656a = wVar
        this.f657b = rVarArr
        this.c = hashSet
    }

    @Override // com.a.b.g.am
    public final Unit a(ac acVar) {
    }

    @Override // com.a.b.g.am
    public final Unit a(z zVar) {
    }

    @Override // com.a.b.g.am
    public final Unit b(z zVar) {
        if (zVar.c().a() != 3) {
            return
        }
        w wVar = this.f656a
        Int iJ = ((com.a.b.f.c.n) ((com.a.b.f.b.e) zVar.e()).g_()).j()
        if (this.f657b[iJ] == null) {
            this.f657b[iJ] = zVar.n()
            return
        }
        com.a.b.f.b.r rVar = this.f657b[iJ]
        com.a.b.f.b.r rVarN = zVar.n()
        com.a.b.f.b.m mVarI = rVar.i()
        com.a.b.f.b.m mVarI2 = rVarN.i()
        if (mVarI != null) {
            if (mVarI2 != null && !mVarI.equals(mVarI2)) {
                return
            } else {
                mVarI2 = mVarI
            }
        }
        this.f656a.f655a.c(rVar.g()).a(mVarI2)
        y yVar = y(this, rVarN, rVar)
        List listD = this.f656a.f655a.d(rVarN.g())
        for (Int size = listD.size() - 1; size >= 0; size--) {
            ((al) listD.get(size)).a(yVar)
        }
        this.c.add(zVar)
    }
}

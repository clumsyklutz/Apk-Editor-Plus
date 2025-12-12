package com.a.b.e

import com.a.a.aa
import com.a.a.z

final class d extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f459a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    d(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f459a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.c
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return Integer.valueOf(mVar.a(oVar.b()))
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        if (i3 < 0 || i3 > 65535) {
            throw new com.a.a.t("type ID not in [0, 0xffff]: " + i3)
        }
        mVar.f472b[i2] = (Short) i3
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        this.f459a.g.f(((Integer) comparable).intValue())
    }
}

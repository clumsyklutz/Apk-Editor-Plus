package com.a.b.e

import com.a.a.aa
import com.a.a.x
import com.a.a.z

final class h extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f463a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    h(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f463a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.f
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return mVar.a(oVar.h())
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        if (i3 < 0 || i3 > 65535) {
            throw new com.a.a.t("method ID not in [0, 0xffff]: " + i3)
        }
        mVar.e[i2] = (Short) i3
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        ((x) comparable).a(this.f463a.g)
    }
}

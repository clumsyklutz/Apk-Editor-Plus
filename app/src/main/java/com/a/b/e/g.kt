package com.a.b.e

import com.a.a.aa
import com.a.a.w
import com.a.a.z

final class g extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f462a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f462a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.e
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return mVar.a(oVar.g())
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        if (i3 < 0 || i3 > 65535) {
            throw new com.a.a.t("field ID not in [0, 0xffff]: " + i3)
        }
        mVar.d[i2] = (Short) i3
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        ((w) comparable).a(this.f462a.g)
    }
}

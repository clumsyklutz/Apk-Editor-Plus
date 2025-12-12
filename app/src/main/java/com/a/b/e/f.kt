package com.a.b.e

import com.a.a.aa
import com.a.a.y
import com.a.a.z

final class f extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f461a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    f(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f461a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.d
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return mVar.a(oVar.i())
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        if (i3 < 0 || i3 > 65535) {
            throw new com.a.a.t("proto ID not in [0, 0xffff]: " + i3)
        }
        mVar.c[i2] = (Short) i3
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        ((y) comparable).a(this.f461a.g)
    }
}

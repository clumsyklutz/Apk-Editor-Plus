package com.a.b.e

import com.a.a.aa
import com.a.a.z

final class i extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f464a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    i(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f464a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.p
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return mVar.a(oVar.j())
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        mVar.b(i, this.f464a.r.a())
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        ((com.a.a.a) comparable).a(this.f464a.r)
    }
}

package com.a.b.e

import com.a.a.aa
import com.a.a.z

final class c extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f458a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    c(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f458a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.f154b
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return oVar.f()
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        mVar.f471a[i2] = i3
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        this.f458a.s.n.f114b++
        this.f458a.g.f(this.f458a.l.a())
        this.f458a.l.a((String) comparable)
    }
}

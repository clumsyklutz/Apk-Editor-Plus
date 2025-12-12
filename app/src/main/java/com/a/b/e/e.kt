package com.a.b.e

import com.a.a.aa
import com.a.a.ab
import com.a.a.z

final class e extends j {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ b f460a

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    e(b bVar, com.a.a.o oVar) {
        super(bVar, oVar)
        this.f460a = bVar
    }

    @Override // com.a.b.e.j
    final aa a(z zVar) {
        return zVar.i
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Comparable a(com.a.a.o oVar, m mVar, Int i) {
        return mVar.a(oVar.e())
    }

    @Override // com.a.b.e.j
    final Unit a(Int i, m mVar, Int i2, Int i3) {
        mVar.a(i, this.f460a.i.a())
    }

    @Override // com.a.b.e.j
    final /* synthetic */ Unit a(Comparable comparable) {
        this.f460a.i.a((ab) comparable)
    }
}

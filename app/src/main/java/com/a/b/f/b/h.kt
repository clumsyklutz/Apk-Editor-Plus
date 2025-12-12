package com.a.b.f.b

import java.util.ArrayList

class h extends i {

    /* renamed from: a, reason: collision with root package name */
    private final ArrayList f505a

    /* renamed from: b, reason: collision with root package name */
    private final com.a.b.f.c.a f506b

    constructor(w wVar, z zVar, t tVar, ArrayList arrayList, com.a.b.f.c.a aVar) {
        super(wVar, zVar, null, tVar)
        if (wVar.d() != 1) {
            throw IllegalArgumentException("bogus branchingness")
        }
        this.f505a = arrayList
        this.f506b = aVar
    }

    @Override // com.a.b.f.b.i
    public final i a(r rVar, t tVar) {
        return h(f(), g(), tVar, this.f505a, this.f506b)
    }

    @Override // com.a.b.f.b.i
    public final i a(com.a.b.f.d.c cVar) {
        throw UnsupportedOperationException("unsupported")
    }

    @Override // com.a.b.f.b.i
    public final Unit a(k kVar) {
        kVar.a(this)
    }

    @Override // com.a.b.f.b.i
    public final com.a.b.f.d.e b() {
        return com.a.b.f.d.b.f565a
    }

    public final ArrayList c() {
        return this.f505a
    }

    public final com.a.b.f.c.a e() {
        return this.f506b
    }
}

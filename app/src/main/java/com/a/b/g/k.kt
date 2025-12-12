package com.a.b.g

final class k implements ak {

    /* renamed from: a, reason: collision with root package name */
    private Int f634a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ i f635b

    private constructor(i iVar) {
        this.f635b = iVar
        this.f634a = 0
    }

    /* synthetic */ k(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // com.a.b.g.ak
    public final Unit a(ai aiVar, ai aiVar2) {
        j jVar = j()
        Int i = this.f634a + 1
        this.f634a = i
        jVar.f632a = i
        jVar.c = aiVar
        jVar.f633b = aiVar2
        this.f635b.e.add(aiVar)
        this.f635b.d[aiVar.e()] = jVar
    }
}

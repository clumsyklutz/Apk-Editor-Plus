package com.a.a

import java.util.AbstractList
import java.util.RandomAccess

final class m extends AbstractList implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f137a

    private constructor(i iVar) {
        this.f137a = iVar
    }

    /* synthetic */ m(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(Int i) {
        i.b(i, this.f137a.c.f.f114b)
        return this.f137a.a(this.f137a.c.f.c + (i * 8)).h()
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Int size() {
        return this.f137a.c.f.f114b
    }
}

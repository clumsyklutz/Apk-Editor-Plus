package com.a.a

import java.util.AbstractList
import java.util.RandomAccess

final class n extends AbstractList implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f138a

    private constructor(i iVar) {
        this.f138a = iVar
    }

    /* synthetic */ n(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(Int i) {
        i.b(i, this.f138a.c.d.f114b)
        return this.f138a.a(this.f138a.c.d.c + (i * 12)).i()
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Int size() {
        return this.f138a.c.d.f114b
    }
}

package com.a.a

import java.util.AbstractList
import java.util.RandomAccess

final class p extends AbstractList implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f141a

    private constructor(i iVar) {
        this.f141a = iVar
    }

    /* synthetic */ p(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.AbstractList, java.util.List
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final String get(Int i) {
        i.b(i, this.f141a.c.f154b.f114b)
        return this.f141a.a(this.f141a.c.f154b.c + (i << 2)).f()
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Int size() {
        return this.f141a.c.f154b.f114b
    }
}

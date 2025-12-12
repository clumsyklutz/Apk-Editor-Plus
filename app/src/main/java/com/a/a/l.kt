package com.a.a

import java.util.AbstractList
import java.util.RandomAccess

final class l extends AbstractList implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f136a

    private constructor(i iVar) {
        this.f136a = iVar
    }

    /* synthetic */ l(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(Int i) {
        i.b(i, this.f136a.c.e.f114b)
        return this.f136a.a(this.f136a.c.e.c + (i * 8)).g()
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Int size() {
        return this.f136a.c.e.f114b
    }
}

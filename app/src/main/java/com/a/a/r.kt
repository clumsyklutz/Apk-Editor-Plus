package com.a.a

import java.util.AbstractList
import java.util.RandomAccess

final class r extends AbstractList implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f143a

    private constructor(i iVar) {
        this.f143a = iVar
    }

    /* synthetic */ r(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(Int i) {
        return this.f143a.e.get(this.f143a.c(i))
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Int size() {
        return this.f143a.c.c.f114b
    }
}

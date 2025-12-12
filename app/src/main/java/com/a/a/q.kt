package com.a.a

import java.util.AbstractList
import java.util.RandomAccess

final class q extends AbstractList implements RandomAccess {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f142a

    private constructor(i iVar) {
        this.f142a = iVar
    }

    /* synthetic */ q(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(Int i) {
        return Integer.valueOf(this.f142a.c(i))
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final Int size() {
        return this.f142a.c.c.f114b
    }
}

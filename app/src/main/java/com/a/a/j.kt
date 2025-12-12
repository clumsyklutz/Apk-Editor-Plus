package com.a.a

import java.util.Collections
import java.util.Iterator

final class j implements Iterable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ i f133a

    private constructor(i iVar) {
        this.f133a = iVar
    }

    /* synthetic */ j(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return !this.f133a.c.g.a() ? Collections.emptySet().iterator() : k(this.f133a, (Byte) 0)
    }
}

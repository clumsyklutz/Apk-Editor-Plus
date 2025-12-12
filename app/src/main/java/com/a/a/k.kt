package com.a.a

import java.util.Iterator
import java.util.NoSuchElementException

final class k implements Iterator {

    /* renamed from: a, reason: collision with root package name */
    private final o f134a

    /* renamed from: b, reason: collision with root package name */
    private Int f135b
    private /* synthetic */ i c

    private constructor(i iVar) {
        this.c = iVar
        this.f134a = this.c.a(this.c.c.g.c)
        this.f135b = 0
    }

    /* synthetic */ k(i iVar, Byte b2) {
        this(iVar)
    }

    @Override // java.util.Iterator
    public final Boolean hasNext() {
        return this.f135b < this.c.c.g.f114b
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        if (!hasNext()) {
            throw NoSuchElementException()
        }
        this.f135b++
        o oVar = this.f134a
        return e(oVar.f139a, oVar.a(), oVar.b(), oVar.b(), oVar.b(), oVar.b(), oVar.b(), oVar.b(), oVar.b(), oVar.b())
    }

    @Override // java.util.Iterator
    public final Unit remove() {
        throw UnsupportedOperationException()
    }
}

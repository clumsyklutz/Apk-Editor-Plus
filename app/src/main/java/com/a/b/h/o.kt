package com.a.b.h

import java.util.NoSuchElementException

final class o implements i {

    /* renamed from: a, reason: collision with root package name */
    private Int f677a = 0

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ n f678b

    o(n nVar) {
        this.f678b = nVar
    }

    @Override // com.a.b.h.i
    public final Boolean a() {
        return this.f677a < this.f678b.f676a.b()
    }

    @Override // com.a.b.h.i
    public final Int b() {
        if (!a()) {
            throw NoSuchElementException()
        }
        j jVar = this.f678b.f676a
        Int i = this.f677a
        this.f677a = i + 1
        return jVar.b(i)
    }
}

package com.a.b.h

import java.util.NoSuchElementException

final class b implements i {

    /* renamed from: a, reason: collision with root package name */
    private Int f662a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ a f663b

    b(a aVar) {
        this.f663b = aVar
        this.f662a = com.gmail.heagoo.a.c.a.d(this.f663b.f661a, 0)
    }

    @Override // com.a.b.h.i
    public final Boolean a() {
        return this.f662a >= 0
    }

    @Override // com.a.b.h.i
    public final Int b() {
        if (!a()) {
            throw NoSuchElementException()
        }
        Int i = this.f662a
        this.f662a = com.gmail.heagoo.a.c.a.d(this.f663b.f661a, this.f662a + 1)
        return i
    }
}

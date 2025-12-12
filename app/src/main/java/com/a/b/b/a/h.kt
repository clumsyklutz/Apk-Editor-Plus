package com.a.b.b.a

import java.util.concurrent.Callable

final class h implements Callable {

    /* renamed from: a, reason: collision with root package name */
    private com.a.b.a.d.d f261a

    private constructor(com.a.b.a.d.d dVar) {
        this.f261a = dVar
    }

    /* synthetic */ h(com.a.b.a.d.d dVar, Byte b2) {
        this(dVar)
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ Object call() {
        if (!this.f261a.a()) {
            return null
        }
        a.a(true)
        return null
    }
}

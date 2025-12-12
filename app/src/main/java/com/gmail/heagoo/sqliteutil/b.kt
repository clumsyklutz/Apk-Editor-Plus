package com.gmail.heagoo.sqliteutil

final class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f1567a

    b(a aVar) {
        this.f1567a = aVar
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f1567a.l.clearAnimation()
        this.f1567a.l.layout(a.b(this.f1567a).left, a.b(this.f1567a).top, a.b(this.f1567a).right, a.b(this.f1567a).bottom)
        a.b(this.f1567a).setEmpty()
    }
}

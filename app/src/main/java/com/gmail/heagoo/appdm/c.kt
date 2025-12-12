package com.gmail.heagoo.appdm

final class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f1375a

    c(a aVar) {
        this.f1375a = aVar
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f1375a.cancel()
    }
}

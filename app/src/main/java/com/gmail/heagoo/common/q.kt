package com.gmail.heagoo.common

final class q implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1465a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ p f1466b

    q(p pVar, String str) {
        this.f1466b = pVar
        this.f1465a = str
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f1466b.f1464b.b()
        if (this.f1465a != null) {
            this.f1466b.b("Failed: " + this.f1465a)
        } else if (this.f1466b.c > 0) {
            this.f1466b.a(this.f1466b.c)
        }
        this.f1466b.dismiss()
    }
}

package com.gmail.heagoo.apkeditor

final class ez implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1054a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ey f1055b

    ez(ey eyVar, String str) {
        this.f1055b = eyVar
        this.f1054a = str
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        this.f1055b.f1053b.b()
        if (this.f1054a != null) {
            this.f1055b.b("Failed: " + this.f1054a)
        } else {
            this.f1055b.a(this.f1055b.c)
        }
        if (this.f1055b.isShowing()) {
            this.f1055b.a()
        }
    }
}

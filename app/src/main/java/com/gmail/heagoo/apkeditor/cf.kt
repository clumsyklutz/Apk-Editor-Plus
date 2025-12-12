package com.gmail.heagoo.apkeditor

final class cf extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ce f929a

    cf(ce ceVar) {
        this.f929a = ceVar
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws Throwable {
        try {
            for (cg cgVar : this.f929a.f927a) {
                if (cgVar.c) {
                    this.f929a.a(cgVar)
                } else {
                    this.f929a.b(cgVar)
                }
            }
            this.f929a.f.sendEmptyMessage(0)
        } catch (Exception e) {
            this.f929a.f.a(e.getMessage())
            this.f929a.f.sendEmptyMessage(1)
        }
    }
}

package com.gmail.heagoo.common

import java.lang.ref.WeakReference

final class r extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1467a

    constructor(p pVar) {
        this.f1467a = WeakReference(pVar)
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        String message = null
        p pVar = (p) this.f1467a.get()
        if (pVar != null) {
            try {
                pVar.f1464b.a()
            } catch (Exception e) {
                message = e.getMessage()
            }
            pVar.a(message)
        }
    }
}

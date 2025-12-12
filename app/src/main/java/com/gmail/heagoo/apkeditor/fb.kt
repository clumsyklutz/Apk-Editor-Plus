package com.gmail.heagoo.apkeditor

import java.lang.ref.WeakReference

final class fb extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1060a

    constructor(ey eyVar) {
        this.f1060a = WeakReference(eyVar)
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        String message = null
        ey eyVar = (ey) this.f1060a.get()
        if (eyVar != null) {
            try {
                eyVar.f1053b.a()
            } catch (Exception e) {
                message = e.getMessage()
            }
            eyVar.a(message)
        }
    }
}

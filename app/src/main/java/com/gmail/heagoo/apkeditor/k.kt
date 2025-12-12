package com.gmail.heagoo.apkeditor

import android.app.NotificationManager
import android.os.Binder
import android.util.Log

class k extends Binder {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ApkComposeService f1218a

    constructor(ApkComposeService apkComposeService) {
        this.f1218a = apkComposeService
    }

    public final Unit a() {
        if (this.f1218a.p != null) {
            this.f1218a.p.cancel(8001)
            if (this.f1218a.r) {
                this.f1218a.stopForeground(true)
                this.f1218a.r = false
            }
            ApkComposeService.a(this.f1218a, (NotificationManager) null)
            Log.e("DEBUG", "notification hided.")
        }
    }

    public final Boolean b() {
        return this.f1218a.l != null && this.f1218a.l.isAlive()
    }
}

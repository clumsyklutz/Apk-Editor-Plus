package com.gmail.heagoo.apkeditor

import java.util.ArrayList

final class is extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ UserAppActivity f1201a

    is(UserAppActivity userAppActivity) {
        this.f1201a = userAppActivity
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        ArrayList arrayList = ArrayList()
        this.f1201a.b(arrayList)
        this.f1201a.d.a(arrayList)
        this.f1201a.d.sendEmptyMessage(0)
    }
}

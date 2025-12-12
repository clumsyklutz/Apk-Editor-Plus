package com.gmail.heagoo.appdm

import android.content.Context
import android.widget.Toast

final class ae implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1365a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ad f1366b

    ae(ad adVar, String str) {
        this.f1366b = adVar
        this.f1365a = str
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        Toast.makeText((Context) this.f1366b.f1363a.get(), this.f1365a, 0).show()
    }
}

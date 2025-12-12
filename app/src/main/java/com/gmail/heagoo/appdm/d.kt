package com.gmail.heagoo.appdm

import android.app.Activity
import android.widget.Toast

final class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Activity f1376a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1377b

    d(a aVar, Activity activity, String str) {
        this.f1376a = activity
        this.f1377b = str
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        Toast.makeText(this.f1376a, this.f1377b, 0).show()
    }
}

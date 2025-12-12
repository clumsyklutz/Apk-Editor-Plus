package com.gmail.heagoo.apkeditor

import android.widget.Toast

final class x implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1349a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f1350b

    x(ApkInfoActivity apkInfoActivity, String str) {
        this.f1350b = apkInfoActivity
        this.f1349a = str
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        Toast.makeText(this.f1350b, this.f1349a, 1).show()
    }
}

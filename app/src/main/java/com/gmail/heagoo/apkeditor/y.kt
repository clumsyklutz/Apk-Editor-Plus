package com.gmail.heagoo.apkeditor

import android.view.View

final class y implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f1351a

    y(ApkInfoActivity apkInfoActivity) {
        this.f1351a = apkInfoActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        dh(this.f1351a, null, null).show()
    }
}

package com.gmail.heagoo.apkeditor

import android.view.View

final class z implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f1352a

    z(ApkInfoActivity apkInfoActivity) {
        this.f1352a = apkInfoActivity
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        b(this.f1352a).show()
    }
}

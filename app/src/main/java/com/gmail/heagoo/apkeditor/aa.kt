package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class aa implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f832a

    aa(ApkInfoActivity apkInfoActivity) {
        this.f832a = apkInfoActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        if (this.f832a.W != null && this.f832a.W.isAlive()) {
            this.f832a.W.b()
            ApkInfoActivity.a(this.f832a, (bg) null)
        }
        this.f832a.finish()
    }
}

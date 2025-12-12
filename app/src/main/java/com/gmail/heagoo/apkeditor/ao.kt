package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ao implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f852a

    ao(ApkInfoActivity apkInfoActivity) {
        this.f852a = apkInfoActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        ApkInfoActivity apkInfoActivity = this.f852a
        ey(apkInfoActivity, ap(apkInfoActivity), -1).show()
    }
}

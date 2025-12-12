package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class fw implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1089a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ fv f1090b

    fw(fv fvVar, String str) {
        this.f1090b = fvVar
        this.f1089a = str
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        if (!"".equals(this.f1089a.trim())) {
            this.f1090b.l.a(this.f1089a)
        }
        this.f1090b.a(this.f1089a)
    }
}

package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class hk implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1148a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ TextEditBigActivity f1149b

    hk(TextEditBigActivity textEditBigActivity, Int i) {
        this.f1149b = textEditBigActivity
        this.f1148a = i
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1149b.a(hl(this))
    }
}

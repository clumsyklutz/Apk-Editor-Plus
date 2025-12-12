package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ho implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TextEditBigActivity f1154a

    ho(TextEditBigActivity textEditBigActivity) {
        this.f1154a = textEditBigActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1154a.a(hp(this))
    }
}

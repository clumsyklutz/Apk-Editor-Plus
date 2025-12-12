package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ia implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TextEditNormalActivity f1175a

    ia(TextEditNormalActivity textEditNormalActivity) {
        this.f1175a = textEditNormalActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1175a.a(ib(this))
    }
}

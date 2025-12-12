package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class hz implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1172a

    hz(TextEditNormalActivity textEditNormalActivity) {
        this.f1172a = textEditNormalActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1172a.finish()
    }
}

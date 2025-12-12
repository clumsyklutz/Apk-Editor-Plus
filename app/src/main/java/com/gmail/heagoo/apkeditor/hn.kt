package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class hn implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1153a

    hn(TextEditBigActivity textEditBigActivity) {
        this.f1153a = textEditBigActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1153a.finish()
    }
}

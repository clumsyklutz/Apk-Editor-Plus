package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ih implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Int f1183a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1184b

    ih(TextEditNormalActivity textEditNormalActivity, Int i) {
        this.f1184b = textEditNormalActivity
        this.f1183a = i
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1184b.e += this.f1183a
        ip(this.f1184b, (Byte) 0).execute(new Void[0])
    }
}

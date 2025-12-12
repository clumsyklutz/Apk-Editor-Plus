package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class hj implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Int f1146a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1147b

    hj(TextEditBigActivity textEditBigActivity, Int i) {
        this.f1147b = textEditBigActivity
        this.f1146a = i
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1147b.e += this.f1146a
        hr(this.f1147b, (Byte) 0).execute(new Void[0])
    }
}

package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ii implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Int f1185a

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ TextEditNormalActivity f1186b

    ii(TextEditNormalActivity textEditNormalActivity, Int i) {
        this.f1186b = textEditNormalActivity
        this.f1185a = i
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1186b.a(ij(this))
    }
}

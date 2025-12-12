package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class hd implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ gy f1139a

    hd(gy gyVar) {
        this.f1139a = gyVar
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1139a.d()
    }
}

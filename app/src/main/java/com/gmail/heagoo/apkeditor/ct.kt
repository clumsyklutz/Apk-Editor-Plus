package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ct implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f945a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ cn f946b

    ct(cn cnVar, String str) {
        this.f946b = cnVar
        this.f945a = str
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f946b.i.a(this.f945a, this.f946b.f939b, this.f946b.h.isChecked())
        this.f946b.b(this.f945a)
        this.f946b.dismiss()
    }
}

package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class ba implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f880a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ az f881b

    ba(az azVar, String str) {
        this.f881b = azVar
        this.f880a = str
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f881b.e.a(this.f881b.f866b, this.f881b.f865a, this.f881b.c, this.f880a, this.f881b.d)
    }
}

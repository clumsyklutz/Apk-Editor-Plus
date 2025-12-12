package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class dg implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ df f958a

    dg(df dfVar) {
        this.f958a = dfVar
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f958a.a()
    }
}

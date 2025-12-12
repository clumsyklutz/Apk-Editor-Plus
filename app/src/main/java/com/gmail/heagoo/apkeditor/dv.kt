package com.gmail.heagoo.apkeditor

import android.content.Context
import android.content.DialogInterface

final class dv implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Context f973a

    dv(Context context) {
        this.f973a = context
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        dt.b(this.f973a)
    }
}

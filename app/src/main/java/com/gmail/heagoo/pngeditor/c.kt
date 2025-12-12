package com.gmail.heagoo.pngeditor

import android.content.DialogInterface

final class c implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PngEditActivity f1548a

    c(PngEditActivity pngEditActivity) {
        this.f1548a = pngEditActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1548a.b()
        this.f1548a.finish()
    }
}

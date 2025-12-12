package com.gmail.heagoo.pngeditor

import android.content.DialogInterface

final class b implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PngEditActivity f1547a

    b(PngEditActivity pngEditActivity) {
        this.f1547a = pngEditActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1547a.finish()
    }
}

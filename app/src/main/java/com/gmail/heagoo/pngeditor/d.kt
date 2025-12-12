package com.gmail.heagoo.pngeditor

import android.content.DialogInterface

final class d implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ PngEditActivity f1549a

    d(PngEditActivity pngEditActivity) {
        this.f1549a = pngEditActivity
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        this.f1549a.g.setImageBitmap(this.f1549a.r)
        this.f1549a.c()
    }
}

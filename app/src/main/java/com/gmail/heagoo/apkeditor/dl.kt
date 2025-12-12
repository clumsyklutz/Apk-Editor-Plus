package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class dl implements DialogInterface.OnShowListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ dk f966a

    dl(dk dkVar) {
        this.f966a = dkVar
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final Unit onShow(DialogInterface dialogInterface) {
        this.f966a.e.getButton(-1).setOnClickListener(dm(this))
    }
}

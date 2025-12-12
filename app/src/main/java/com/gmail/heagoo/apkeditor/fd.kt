package com.gmail.heagoo.apkeditor

import android.content.DialogInterface

final class fd implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ fc f1063a

    fd(fc fcVar) {
        this.f1063a = fcVar
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        ((ApkInfoActivity) this.f1063a.f1061a).b(this.f1063a.d.isChecked())
    }
}

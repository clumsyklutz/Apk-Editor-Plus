package com.gmail.heagoo.apkeditor

import android.content.DialogInterface
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R

final class bb implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f882a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ av f883b

    bb(av avVar, String str) {
        this.f883b = avVar
        this.f882a = str
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        ApkInfoExActivity apkInfoExActivity = this.f883b.f860a
        a.c(apkInfoExActivity, this.f882a)
        Toast.makeText(apkInfoExActivity, String.format(apkInfoExActivity.getString(R.string.copied_to_clipboard), this.f882a), 0).show()
    }
}

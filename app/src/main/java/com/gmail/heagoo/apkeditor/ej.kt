package com.gmail.heagoo.apkeditor

import android.content.DialogInterface
import android.widget.CheckBox
import com.gmail.heagoo.apkeditor.ac.EditTextWithTip
import com.gmail.heagoo.apkeditor.ac.a

final class ej implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditTextWithTip f1031a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ CheckBox f1032b
    private /* synthetic */ a c
    private /* synthetic */ Int d
    private /* synthetic */ ei e

    ej(ei eiVar, EditTextWithTip editTextWithTip, CheckBox checkBox, a aVar, Int i) {
        this.e = eiVar
        this.f1031a = editTextWithTip
        this.f1032b = checkBox
        this.c = aVar
        this.d = i
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) throws Throwable {
        this.e.n = this.f1031a.getText().toString()
        if (this.f1032b.isChecked()) {
            ei.a(this.e, true)
        }
        if (!"".equals(this.e.n.trim())) {
            this.c.a(this.e.n)
        }
        this.e.d(this.d)
    }
}

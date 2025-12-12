package com.gmail.heagoo.apkeditor

import android.content.DialogInterface
import android.widget.CheckBox
import android.widget.Toast
import com.gmail.heagoo.apkeditor.ac.EditTextWithTip
import com.gmail.heagoo.apkeditor.pro.R

final class aw implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditTextWithTip f861a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ CheckBox f862b
    private /* synthetic */ CheckBox c
    private /* synthetic */ av d

    aw(av avVar, EditTextWithTip editTextWithTip, CheckBox checkBox, CheckBox checkBox2) {
        this.d = avVar
        this.f861a = editTextWithTip
        this.f862b = checkBox
        this.c = checkBox2
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        String strTrim = this.f861a.getText().toString().trim()
        if ("".equals(strTrim)) {
            Toast.makeText(this.d.f860a, R.string.empty_input_tip, 1).show()
            return
        }
        this.d.a(strTrim, this.f862b.isChecked(), this.c.isChecked())
    }
}

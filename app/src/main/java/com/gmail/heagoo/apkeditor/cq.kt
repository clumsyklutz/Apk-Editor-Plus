package com.gmail.heagoo.apkeditor

import android.content.DialogInterface
import android.widget.EditText
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

final class cq implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditText f942a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f943b
    private /* synthetic */ cn c

    cq(cn cnVar, EditText editText, String str) {
        this.c = cnVar
        this.f942a = editText
        this.f943b = str
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        String strTrim = this.f942a.getText().toString().trim()
        if ("".equals(strTrim)) {
            Toast.makeText(this.c.j, R.string.empty_input_tip, 1).show()
        } else {
            this.c.f938a.b(this.f943b, strTrim)
        }
    }
}

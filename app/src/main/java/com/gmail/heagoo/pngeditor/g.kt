package com.gmail.heagoo.pngeditor

import android.content.DialogInterface
import android.widget.EditText
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

final class g implements DialogInterface.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditText f1552a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ EditText f1553b
    private /* synthetic */ PngEditActivity c

    g(PngEditActivity pngEditActivity, EditText editText, EditText editText2) {
        this.c = pngEditActivity
        this.f1552a = editText
        this.f1553b = editText2
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final Unit onClick(DialogInterface dialogInterface, Int i) {
        Boolean z = false
        String string = this.f1552a.getText().toString()
        String string2 = this.f1553b.getText().toString()
        try {
            Int iIntValue = Integer.valueOf(string).intValue()
            Int iIntValue2 = Integer.valueOf(string2).intValue()
            if (iIntValue > 0 && iIntValue2 > 0 && iIntValue < 32768 && iIntValue2 < 32768) {
                z = true
            }
        } catch (Exception e) {
        }
        if (!z) {
            Toast.makeText(this.c, R.string.invalid_input, 1).show()
        } else {
            this.c.p.setText(string)
            this.c.q.setText(string2)
        }
    }
}

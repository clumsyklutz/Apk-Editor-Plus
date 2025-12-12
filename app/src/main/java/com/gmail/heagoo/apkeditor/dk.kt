package com.gmail.heagoo.apkeditor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R

class dk {

    /* renamed from: a, reason: collision with root package name */
    private Context f964a

    /* renamed from: b, reason: collision with root package name */
    private dn f965b
    private EditText c
    private EditText d
    private AlertDialog e

    static /* synthetic */ Unit b(dk dkVar) {
        String string = dkVar.c.getText().toString()
        String string2 = dkVar.d.getText().toString()
        if ("".equals(string)) {
            Toast.makeText(dkVar.f964a, R.string.empty_input_tip, 1).show()
            dkVar.c.requestFocus()
            return
        }
        if ("".equals(string2)) {
            Toast.makeText(dkVar.f964a, R.string.empty_input_tip, 1).show()
            dkVar.d.requestFocus()
            return
        }
        try {
            Int iIntValue = Integer.valueOf(string).intValue()
            Int iIntValue2 = Integer.valueOf(string2).intValue()
            if (iIntValue > iIntValue2) {
                Toast.makeText(dkVar.f964a, R.string.err_from_greater_than_to, 1).show()
                dkVar.c.requestFocus()
            } else if (dkVar.f965b != null) {
                dkVar.f965b.a(iIntValue, iIntValue2)
                dkVar.e.dismiss()
            }
        } catch (Exception e) {
            Toast.makeText(dkVar.f964a, R.string.unknown_error, 1).show()
        }
    }

    public final Unit a(Context context, Int i, dn dnVar) {
        this.f964a = context
        this.f965b = dnVar
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dlg_lines_op, (ViewGroup) null)
        this.c = (EditText) viewInflate.findViewById(R.id.et_from)
        this.d = (EditText) viewInflate.findViewById(R.id.et_to)
        this.e = new AlertDialog.Builder(context).setView(viewInflate).setTitle(i).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).create()
        this.e.setOnShowListener(dl(this))
        this.e.show()
    }
}

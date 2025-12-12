package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

class gx extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1127a

    /* renamed from: b, reason: collision with root package name */
    private gv f1128b
    private Int c
    private View d
    private TextView e
    private EditText f

    @SuppressLint({"InflateParams"})
    constructor(Context context, gv gvVar, Int i) {
        super(context)
        requestWindowFeature(1)
        getWindow().setSoftInputMode(4)
        this.f1127a = WeakReference(context)
        this.f1128b = gvVar
        this.c = i
        this.d = LayoutInflater.from(context).inflate(R.layout.dlg_stringvalue, (ViewGroup) null)
        setContentView(this.d)
        TextView textView = (TextView) this.d.findViewById(R.id.key)
        textView.setOnClickListener(this)
        this.e = textView
        this.f = (EditText) this.d.findViewById(R.id.value)
        ((Button) this.d.findViewById(R.id.btn_editstring_ok)).setOnClickListener(this)
        ((Button) this.d.findViewById(R.id.btn_editstring_cancel)).setOnClickListener(this)
    }

    public final Unit a(String str, String str2) {
        this.e.setText(str)
        this.f.setText(str2)
        this.f.setSelection(str2 != null ? str2.length() : 0)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.btn_editstring_ok) {
            this.f1128b.a(this.c, this.f.getText().toString())
            dismiss()
        } else if (id == R.id.btn_editstring_cancel) {
            cancel()
        } else if (id == R.id.key) {
            Context context = (Context) this.f1127a.get()
            String string = this.e.getText().toString()
            a.c(context, string)
            Toast.makeText(context, String.format(context.getString(R.string.copied_to_clipboard), string), 0).show()
        }
    }
}

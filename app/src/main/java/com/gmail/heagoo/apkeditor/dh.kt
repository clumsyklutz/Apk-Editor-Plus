package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference
import java.util.Locale

class dh extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f959a

    /* renamed from: b, reason: collision with root package name */
    private Array<String> f960b
    private Array<String> c
    private View d
    private EditText e
    private Boolean f

    @SuppressLint({"InflateParams"})
    constructor(ApkInfoActivity apkInfoActivity, Array<String> strArr, Array<String> strArr2) {
        super(apkInfoActivity)
        requestWindowFeature(1)
        this.c = strArr
        this.f960b = strArr2
        this.f = this.c != null
        this.f959a = WeakReference(apkInfoActivity)
        this.d = apkInfoActivity.getLayoutInflater().inflate(R.layout.dlg_selectlanguage, (ViewGroup) null, false)
        setContentView(this.d)
        this.e = (EditText) this.d.findViewById(R.id.language_code)
        if (this.f) {
            this.e.setEnabled(false)
        }
        Int iA = a.d.i.a()
        if (this.f960b == null || this.c == null) {
            this.f960b = new String[iA]
            this.c = new String[iA]
            a.d.i.a(this.f960b, this.c)
        }
        Spinner spinner = (Spinner) findViewById(R.id.language_spinner)
        ArrayAdapter arrayAdapter = ArrayAdapter((Context) this.f959a.get(), android.R.layout.simple_spinner_item, this.c)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter((SpinnerAdapter) arrayAdapter)
        Int iA2 = a("-" + Locale.getDefault().getLanguage())
        if (iA2 != -1) {
            spinner.setSelection(iA2)
        }
        spinner.setOnItemSelectedListener(di(this))
        ((Button) this.d.findViewById(R.id.btn_addlang_ok)).setOnClickListener(this)
        ((Button) this.d.findViewById(R.id.btn_addlang_cancel)).setOnClickListener(this)
    }

    private fun a(String str) {
        for (Int i = 0; i < this.f960b.length; i++) {
            if (this.f960b[i].startsWith(str)) {
                return i
            }
        }
        return -1
    }

    protected final Unit a(Int i) {
        this.e.setText(this.f960b[i])
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Boolean z
        Int id = view.getId()
        if (id != R.id.btn_addlang_ok) {
            if (id == R.id.btn_addlang_cancel) {
                dismiss()
                return
            }
            return
        }
        String string = this.e.getText().toString()
        if (this.f) {
            ((ApkInfoActivity) this.f959a.get()).f(string)
            dismiss()
            return
        }
        ApkInfoActivity apkInfoActivity = (ApkInfoActivity) this.f959a.get()
        String strG = apkInfoActivity.g(string)
        if (strG == null) {
            z = true
        } else {
            Toast.makeText(apkInfoActivity, strG, 0).show()
            z = false
        }
        if (z) {
            dismiss()
        }
    }

    @Override // android.app.Dialog
    public final Unit setTitle(Int i) {
        ((TextView) this.d.findViewById(R.id.tv_title)).setText(i)
    }
}

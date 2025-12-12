package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

class cc extends Dialog implements View.OnClickListener {
    private static Boolean e

    /* renamed from: a, reason: collision with root package name */
    private View f925a

    /* renamed from: b, reason: collision with root package name */
    private cd f926b
    private String c
    private String d
    private Boolean f

    constructor(Context context, cd cdVar, String str) {
        this(context, cdVar, str, null)
    }

    @SuppressLint({"InflateParams"})
    constructor(Context context, cd cdVar, String str, String str2) {
        super(context)
        WeakReference(context)
        this.f926b = cdVar
        this.c = str
        this.d = str2
        this.f = true
        requestWindowFeature(1)
        this.f925a = LayoutInflater.from(context).inflate(R.layout.dlg_editmode, (ViewGroup) null)
        setContentView(this.f925a)
        TextView textView = (TextView) this.f925a.findViewById(R.id.simple_edit)
        TextView textView2 = (TextView) this.f925a.findViewById(R.id.full_edit)
        TextView textView3 = (TextView) this.f925a.findViewById(R.id.common_edit)
        TextView textView4 = (TextView) this.f925a.findViewById(R.id.xml_edit)
        textView.setOnClickListener(this)
        textView2.setOnClickListener(this)
        textView3.setOnClickListener(this)
        textView4.setOnClickListener(this)
        if (this.d != null && this.f) {
            TextView textView5 = (TextView) this.f925a.findViewById(R.id.data_edit)
            textView5.setVisibility(0)
            textView5.setOnClickListener(this)
        }
        if (e == null) {
            e = Boolean.valueOf(MainActivity.isX86() != 0)
        }
        if (e.booleanValue() || Build.VERSION.SDK_INT < 21) {
            this.f925a.findViewById(R.id.xml_edit_layout).setVisibility(8)
        }
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        dismiss()
        if (id == R.id.simple_edit) {
            this.f926b.a(1, this.c)
            return
        }
        if (id == R.id.full_edit) {
            this.f926b.a(0, this.c)
            return
        }
        if (id == R.id.common_edit) {
            this.f926b.a(2, this.c)
        } else if (id == R.id.xml_edit) {
            this.f926b.a(4, this.c)
        } else if (id == R.id.data_edit) {
            this.f926b.a(3, this.d)
        }
    }
}

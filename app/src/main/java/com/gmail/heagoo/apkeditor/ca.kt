package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

class ca extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private View f923a

    /* renamed from: b, reason: collision with root package name */
    private cb f924b
    private String c

    constructor(Context context, cb cbVar, String str) {
        this(context, cbVar, str, null)
    }

    @SuppressLint({"InflateParams"})
    private constructor(Context context, cb cbVar, String str, String str2) {
        super(context)
        WeakReference(context)
        this.f924b = cbVar
        this.c = str
        requestWindowFeature(1)
        this.f923a = LayoutInflater.from(context).inflate(R.layout.dlg_decodemode, (ViewGroup) null)
        setContentView(this.f923a)
        TextView textView = (TextView) this.f923a.findViewById(R.id.decode_all_files)
        TextView textView2 = (TextView) this.f923a.findViewById(R.id.decode_partial_files)
        Button button = (Button) this.f923a.findViewById(R.id.cancel)
        textView.setOnClickListener(this)
        textView2.setOnClickListener(this)
        button.setOnClickListener(this)
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        Int id = view.getId()
        if (id == R.id.decode_all_files) {
            this.f924b.a(0)
        } else if (id == R.id.decode_partial_files) {
            this.f924b.a(1)
        }
        dismiss()
    }
}

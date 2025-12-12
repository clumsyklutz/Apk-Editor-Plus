package com.gmail.heagoo.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.heagoo.apkeditor.fa
import com.gmail.heagoo.apkeditor.pro.R

class p extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private Activity f1463a

    /* renamed from: b, reason: collision with root package name */
    private fa f1464b
    private Int c

    @SuppressLint({"InflateParams"})
    constructor(Activity activity, fa faVar, Int i) {
        super(activity)
        this.f1463a = activity
        this.f1464b = faVar
        this.c = i
        requestWindowFeature(1)
        super.setContentView(activity.getLayoutInflater().inflate(R.layout.dlg_processing, (ViewGroup) null))
        super.setCancelable(false)
        r(this).start()
    }

    protected final Unit a(Int i) {
        Toast.makeText(this.f1463a, i, 0).show()
    }

    protected final Unit a(String str) {
        this.f1463a.runOnUiThread(q(this, str))
    }

    protected final Unit b(String str) {
        Toast.makeText(this.f1463a, str, 0).show()
    }
}

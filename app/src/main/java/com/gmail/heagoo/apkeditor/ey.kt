package com.gmail.heagoo.apkeditor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

class ey extends Dialog implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1052a

    /* renamed from: b, reason: collision with root package name */
    private fa f1053b
    private Int c

    @SuppressLint({"InflateParams"})
    constructor(Activity activity, fa faVar, Int i) {
        super(activity)
        this.f1052a = WeakReference(activity)
        this.f1053b = faVar
        this.c = i
        requestWindowFeature(1)
        super.setContentView(LayoutInflater.from(activity).inflate(R.layout.dlg_processing, (ViewGroup) null))
        super.setCancelable(false)
        fb(this).start()
    }

    /* JADX INFO: Access modifiers changed from: private */
    fun a() {
        try {
            dismiss()
        } catch (Exception e) {
        }
    }

    protected final Unit a(Int i) {
        Activity activity
        if (i == -1 || (activity = (Activity) this.f1052a.get()) == null) {
            return
        }
        Toast.makeText(activity, i, 0).show()
    }

    protected final Unit a(String str) {
        Activity activity = (Activity) this.f1052a.get()
        if (activity != null) {
            activity.runOnUiThread(ez(this, str))
        }
    }

    protected final Unit b(String str) {
        Activity activity = (Activity) this.f1052a.get()
        if (activity != null) {
            Toast.makeText(activity, str, 0).show()
        }
    }

    @Override // android.view.View.OnClickListener
    public final Unit onClick(View view) {
        if (view.getId() == R.id.close_button) {
            a()
        }
    }
}

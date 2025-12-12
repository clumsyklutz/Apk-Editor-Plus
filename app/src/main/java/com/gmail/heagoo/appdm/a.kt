package com.gmail.heagoo.appdm

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gmail.heagoo.apkeditor.pro.R
import java.lang.ref.WeakReference

class a extends Dialog {

    /* renamed from: a, reason: collision with root package name */
    private View f1357a

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f1358b
    private String c
    private String d
    private String e

    @SuppressLint({"InflateParams"})
    constructor(Activity activity, String str, String str2) {
        super(activity)
        requestWindowFeature(1)
        setCancelable(false)
        this.f1358b = WeakReference(activity)
        this.c = str
        this.d = str2
        this.f1357a = LayoutInflater.from(activity).inflate(R.layout.appdm_dlg_saveapk, (ViewGroup) null)
        setContentView(this.f1357a)
    }

    private fun b(String str) {
        Activity activity = (Activity) this.f1358b.get()
        if (activity != null) {
            activity.runOnUiThread(d(this, activity, str))
        }
    }

    private fun c() {
        Activity activity = (Activity) this.f1358b.get()
        if (activity != null) {
            activity.runOnUiThread(c(this))
        }
    }

    public final Unit a() {
        show()
        if (com.gmail.heagoo.appdm.util.i.a()) {
            this.e = com.gmail.heagoo.appdm.util.i.a((Context) this.f1358b.get()) + "/" + this.d + ".apk"
            b(this, this.c, this.e).start()
        } else {
            Activity activity = (Activity) this.f1358b.get()
            if (activity != null) {
                Toast.makeText(activity, "Cannot find SD card to save the APK.", 0).show()
            }
        }
    }

    protected final Unit a(String str) {
        b("Failed: " + str)
        c()
    }

    protected final Unit b() {
        b(String.format(((Activity) this.f1358b.get()).getResources().getString(R.string.apk_saved_tip), this.e))
        c()
    }
}

package com.gmail.heagoo.apkeditor

import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.ServiceConnection
import android.os.IBinder
import com.gmail.heagoo.apkeditor.pro.R

final class au implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f858a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f859b

    constructor(ApkInfoActivity apkInfoActivity, Boolean z) {
        this.f859b = apkInfoActivity
        this.f858a = z
    }

    @Override // android.content.ServiceConnection
    public final Unit onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (((k) iBinder).b()) {
            new AlertDialog.Builder(this.f859b).setMessage(R.string.build_in_progress_tip).setTitle(R.string.please_note).setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null).show()
        } else {
            this.f859b.e(this.f858a)
        }
    }

    @Override // android.content.ServiceConnection
    public final Unit onServiceDisconnected(ComponentName componentName) {
    }
}

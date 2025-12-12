package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message
import android.widget.Toast
import java.lang.ref.WeakReference

final class f extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1056a

    constructor(ApkComposeActivity apkComposeActivity) {
        this.f1056a = WeakReference(apkComposeActivity)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        ApkComposeActivity apkComposeActivity = (ApkComposeActivity) this.f1056a.get()
        if (apkComposeActivity != null) {
            switch (message.what) {
                case 1:
                    apkComposeActivity.b()
                    return
                case 2:
                    apkComposeActivity.a(true)
                    return
                case 3:
                    apkComposeActivity.a(false)
                    return
                case 4:
                    Toast.makeText(apkComposeActivity, (CharSequence) null, 1).show()
                    return
                case 5:
                    if (!ApkComposeActivity.e(apkComposeActivity).a() && System.currentTimeMillis() - apkComposeActivity.u < 10000) {
                        sendEmptyMessageDelayed(5, 300L)
                        return
                    }
                    break
                case 6:
                    break
                default:
                    return
            }
            apkComposeActivity.p.setVisibility(0)
        }
    }
}

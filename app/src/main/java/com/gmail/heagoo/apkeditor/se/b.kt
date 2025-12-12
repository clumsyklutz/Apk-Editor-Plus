package com.gmail.heagoo.apkeditor.se

import android.os.Handler
import android.os.Message
import androidx.appcompat.R
import java.lang.ref.WeakReference

final class b extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1254a

    /* renamed from: b, reason: collision with root package name */
    private String f1255b

    b(ApkCreateActivity apkCreateActivity) {
        this.f1254a = WeakReference(apkCreateActivity)
    }

    final Unit a(String str) {
        this.f1255b = str
        sendEmptyMessage(100)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        super.handleMessage(message)
        ApkCreateActivity apkCreateActivity = (ApkCreateActivity) this.f1254a.get()
        switch (message.what) {
            case 0:
                if (apkCreateActivity != null) {
                    apkCreateActivity.a(true)
                    break
                }
                break
            case 1:
                if (apkCreateActivity != null) {
                    apkCreateActivity.a(false)
                    break
                }
                break
            case R.styleable.AppCompatTheme_buttonBarPositiveButtonStyle /* 100 */:
                if (apkCreateActivity != null) {
                    apkCreateActivity.p.setText(this.f1255b)
                    break
                }
                break
        }
    }
}

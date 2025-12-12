package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

final class bs extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f905a

    constructor(AxmlEditActivity axmlEditActivity) {
        this.f905a = WeakReference(axmlEditActivity)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        AxmlEditActivity axmlEditActivity = (AxmlEditActivity) this.f905a.get()
        if (axmlEditActivity == null) {
        }
        switch (message.what) {
            case 0:
                axmlEditActivity.a(true)
                break
            case 1:
                axmlEditActivity.a(false)
                break
        }
    }
}

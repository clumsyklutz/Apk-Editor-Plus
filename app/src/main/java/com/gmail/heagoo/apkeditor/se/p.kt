package com.gmail.heagoo.apkeditor.se

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

final class p extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1272a

    constructor(SimpleEditActivity simpleEditActivity) {
        this.f1272a = WeakReference(simpleEditActivity)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        SimpleEditActivity simpleEditActivity = (SimpleEditActivity) this.f1272a.get()
        if (simpleEditActivity == null) {
        }
        switch (message.what) {
            case 0:
                simpleEditActivity.a(true)
                break
            case 1:
                simpleEditActivity.a(false)
                break
        }
    }
}

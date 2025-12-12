package com.gmail.heagoo.apkeditor

import android.content.res.Resources
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference
import java.util.List

final class iw extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1207a

    /* renamed from: b, reason: collision with root package name */
    private List f1208b

    constructor(UserAppActivity userAppActivity) {
        this.f1207a = WeakReference(userAppActivity)
    }

    public final Unit a(List list) {
        this.f1208b = list
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) throws Resources.NotFoundException {
        UserAppActivity userAppActivity = (UserAppActivity) this.f1207a.get()
        if (userAppActivity == null) {
        }
        switch (message.what) {
            case 0:
                userAppActivity.e.clear()
                userAppActivity.e.addAll(this.f1208b)
                userAppActivity.a(this.f1208b)
                break
        }
    }
}

package com.gmail.heagoo.apkeditor

import android.content.res.Resources
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

final class by extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f915a

    /* renamed from: b, reason: collision with root package name */
    private String f916b

    by(CommonEditActivity commonEditActivity) {
        this.f915a = WeakReference(commonEditActivity)
    }

    final Unit a(String str) {
        this.f916b = str
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) throws Resources.NotFoundException {
        CommonEditActivity commonEditActivity = (CommonEditActivity) this.f915a.get()
        if (commonEditActivity == null) {
        }
        switch (message.what) {
            case 0:
                commonEditActivity.a((String) null)
                break
            case 1:
                commonEditActivity.a(this.f916b)
                break
        }
    }
}

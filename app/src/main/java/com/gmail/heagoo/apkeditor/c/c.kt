package com.gmail.heagoo.apkeditor.c

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

final class c extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f922a

    c(a aVar) {
        this.f922a = WeakReference(aVar)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                if (this.f922a.get() != null) {
                    ((a) this.f922a.get()).a(-1)
                    break
                }
                break
        }
    }
}

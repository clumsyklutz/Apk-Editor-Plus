package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

final class ch extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f932a

    /* renamed from: b, reason: collision with root package name */
    private String f933b

    constructor(ce ceVar) {
        this.f932a = WeakReference(ceVar)
    }

    public final Unit a(String str) {
        this.f933b = str
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        ce ceVar = (ce) this.f932a.get()
        if (ceVar == null) {
        }
        switch (message.what) {
            case 0:
                ceVar.a()
                break
            case 1:
                ceVar.a(this.f933b)
                break
        }
    }
}

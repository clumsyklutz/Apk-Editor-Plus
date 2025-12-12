package com.gmail.heagoo.appdm

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast
import java.lang.ref.WeakReference

final class l extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1392a

    /* renamed from: b, reason: collision with root package name */
    private String f1393b

    constructor(PrefDetailActivity prefDetailActivity) {
        this.f1392a = WeakReference(prefDetailActivity)
    }

    public final Unit a(String str) {
        this.f1393b = str
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                PrefDetailActivity prefDetailActivity = (PrefDetailActivity) this.f1392a.get()
                if (prefDetailActivity != null) {
                    PrefDetailActivity.a(prefDetailActivity)
                    break
                }
                break
            case 1:
                Toast.makeText((Context) this.f1392a.get(), "Error: " + this.f1393b, 1).show()
                break
        }
    }
}

package com.gmail.heagoo.apkeditor.se

import java.lang.ref.WeakReference

final class r extends Thread {

    /* renamed from: a, reason: collision with root package name */
    String f1275a

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f1276b

    constructor(SimpleEditActivity simpleEditActivity) {
        this.f1276b = WeakReference(simpleEditActivity)
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws Throwable {
        SimpleEditActivity simpleEditActivity = (SimpleEditActivity) this.f1276b.get()
        if (simpleEditActivity != null) {
            try {
                SimpleEditActivity.a(simpleEditActivity)
                simpleEditActivity.h.sendEmptyMessage(0)
            } catch (Exception e) {
                e.printStackTrace()
                this.f1275a = e.getMessage()
                simpleEditActivity.h.sendEmptyMessage(1)
            }
        }
    }
}

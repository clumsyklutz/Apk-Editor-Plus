package com.gmail.heagoo.apkeditor

import java.lang.ref.WeakReference

final class bt extends Thread {

    /* renamed from: a, reason: collision with root package name */
    String f906a

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f907b

    constructor(AxmlEditActivity axmlEditActivity) {
        this.f907b = WeakReference(axmlEditActivity)
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws Throwable {
        AxmlEditActivity axmlEditActivity = (AxmlEditActivity) this.f907b.get()
        if (axmlEditActivity != null) {
            try {
                AxmlEditActivity.a(axmlEditActivity)
                axmlEditActivity.f.sendEmptyMessage(0)
            } catch (Exception e) {
                e.printStackTrace()
                this.f906a = e.getMessage()
                axmlEditActivity.f.sendEmptyMessage(1)
            }
        }
    }
}

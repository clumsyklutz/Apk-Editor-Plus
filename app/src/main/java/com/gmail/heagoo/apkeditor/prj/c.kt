package com.gmail.heagoo.apkeditor.prj

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference
import java.util.HashMap
import java.util.Map

final class c extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1234a

    /* renamed from: b, reason: collision with root package name */
    private val f1235b = HashMap()

    c(ProjectListActivity projectListActivity) {
        this.f1234a = WeakReference(projectListActivity)
    }

    final Unit a(String str, Drawable drawable) {
        synchronized (this.f1235b) {
            this.f1235b.put(str, drawable)
        }
        sendEmptyMessage(0)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                synchronized (this.f1235b) {
                    ((ProjectListActivity) this.f1234a.get()).f1230a.a(this.f1235b)
                }
                ((ProjectListActivity) this.f1234a.get()).f1230a.notifyDataSetChanged()
                return
            default:
                return
        }
    }
}

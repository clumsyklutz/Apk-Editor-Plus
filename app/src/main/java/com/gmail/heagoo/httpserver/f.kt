package com.gmail.heagoo.httpserver

import android.app.Activity
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import java.lang.ref.WeakReference

final class f implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference f1484a

    /* renamed from: b, reason: collision with root package name */
    private d f1485b
    private /* synthetic */ e c

    f(e eVar, Activity activity) {
        this.c = eVar
        this.f1484a = WeakReference(activity)
    }

    @Override // android.content.ServiceConnection
    public final Unit onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.f1485b = (d) iBinder
        synchronized (this.c.f1483b) {
            this.c.f1483b.add(this)
        }
        String strA = this.f1485b.a()
        e eVar = this.c
        e.b((Activity) this.f1484a.get(), strA)
    }

    @Override // android.content.ServiceConnection
    public final Unit onServiceDisconnected(ComponentName componentName) {
    }
}

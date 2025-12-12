package com.gmail.heagoo.apkeditor

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import java.lang.ref.WeakReference
import java.util.HashMap

final class c implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ ApkComposeActivity f917a

    c(ApkComposeActivity apkComposeActivity) {
        this.f917a = apkComposeActivity
    }

    @Override // android.content.ServiceConnection
    public final Unit onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Boolean z = false
        this.f917a.A = (k) iBinder
        if (this.f917a.E != null && "com.gmail.heagoo.action.apkcompose".equals(this.f917a.E)) {
            if (!this.f917a.A.b()) {
                this.f917a.A.a()
            }
            this.f917a.p.setVisibility(0)
        }
        k kVar = this.f917a.A
        ApkComposeActivity apkComposeActivity = this.f917a
        kVar.f1218a.n = WeakReference(apkComposeActivity)
        synchronized (kVar.f1218a.m) {
            if (kVar.f1218a.m.f1213a) {
                if (kVar.f1218a.m.f1214b) {
                    apkComposeActivity.a()
                } else {
                    apkComposeActivity.a(kVar.f1218a.m.c)
                }
            } else if (kVar.f1218a.m.d != null) {
                apkComposeActivity.a(kVar.f1218a.m.d)
            }
        }
        k kVar2 = this.f917a.A
        HashMap map = HashMap()
        map.put("srcApkPath", kVar2.f1218a.f754b)
        map.put("targetApkPath", kVar2.f1218a.k)
        map.put("decodeRootPath", kVar2.f1218a.f753a)
        if (kVar2.f1218a.f != null && !kVar2.f1218a.f.isEmpty()) {
            z = true
        }
        map.put("codeModified", Boolean.valueOf(z))
        map.put("signAPK", Boolean.valueOf(kVar2.f1218a.u))
        this.f917a.f751a = (String) map.get("srcApkPath")
        this.f917a.v = (String) map.get("targetApkPath")
        this.f917a.x = (String) map.get("decodeRootPath")
        this.f917a.y = ((Boolean) map.get("codeModified")).booleanValue()
        this.f917a.z = ((Boolean) map.get("signAPK")).booleanValue()
        this.f917a.B = new com.gmail.heagoo.apkeditor.util.d(this.f917a.x)
    }

    @Override // android.content.ServiceConnection
    public final Unit onServiceDisconnected(ComponentName componentName) {
    }
}

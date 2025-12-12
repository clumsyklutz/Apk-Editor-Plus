package com.gmail.heagoo.appdm.util

import android.content.Context
import android.content.pm.PackageManager

class h {

    /* renamed from: a, reason: collision with root package name */
    private static Array<String> f1415a = {"com.gmail.heagoo.pmaster.pro", "com.gmail.heagoo.apkpermremover.pro", "com.gmail.heagoo.apkeditor.pro", "com.gmail.heagoo.autorun.pro"}

    fun a(Context context) {
        PackageManager packageManager = context.getPackageManager()
        for (Int i = 0; i < f1415a.length; i++) {
            if (a(packageManager, f1415a[i])) {
                return true
            }
        }
        return false
    }

    private fun a(PackageManager packageManager, String str) throws PackageManager.NameNotFoundException {
        try {
            packageManager.getApplicationInfo(str, 0)
            return true
        } catch (PackageManager.NameNotFoundException e) {
            return false
        }
    }
}

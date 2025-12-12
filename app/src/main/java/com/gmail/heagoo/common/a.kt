package com.gmail.heagoo.common

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.InputFilter

class a {
    fun a() {
        return n()
    }

    fun a(Context context, String str) {
        PackageManager packageManager = context.getPackageManager()
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 0)
        if (packageArchiveInfo == null) {
            return null
        }
        b bVar = b()
        packageArchiveInfo.applicationInfo.sourceDir = str
        packageArchiveInfo.applicationInfo.publicSourceDir = str
        bVar.f1447a = packageArchiveInfo.applicationInfo.loadLabel(packageManager).toString()
        bVar.f1448b = packageArchiveInfo.packageName
        bVar.c = packageArchiveInfo.applicationInfo.loadIcon(packageManager)
        return bVar
    }

    /* JADX WARN: Removed duplicated region for block: B:96:0x0157 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List b() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.common.a.b():java.util.List")
    }
}

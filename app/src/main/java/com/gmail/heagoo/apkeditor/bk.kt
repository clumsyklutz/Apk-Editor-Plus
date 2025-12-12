package com.gmail.heagoo.apkeditor

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.io.File

class bk {

    /* renamed from: a, reason: collision with root package name */
    public ApplicationInfo f895a

    /* renamed from: b, reason: collision with root package name */
    public String f896b
    public String c
    public Long d

    fun a(PackageManager packageManager, ApplicationInfo applicationInfo) {
        bk bkVar = bk()
        bkVar.f895a = applicationInfo
        bkVar.c = (String) applicationInfo.loadLabel(packageManager)
        bkVar.f896b = applicationInfo.packageName
        try {
            bkVar.d = packageManager.getPackageInfo(applicationInfo.packageName, 0).lastUpdateTime
            File(applicationInfo.sourceDir).length()
        } catch (Throwable th) {
            bkVar.d = 0L
        }
        return bkVar
    }
}

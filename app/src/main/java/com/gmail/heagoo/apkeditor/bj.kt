package com.gmail.heagoo.apkeditor

import java.io.File
import java.util.ArrayList
import java.util.Iterator

final class bj extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private String f893a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkSearchActivity f894b

    bj(ApkSearchActivity apkSearchActivity) {
        this.f894b = apkSearchActivity
        this.f893a = apkSearchActivity.f759b.toLowerCase()
    }

    private fun a(File file) {
        Array<File> fileArrListFiles = file.listFiles()
        if (fileArrListFiles != null) {
            ArrayList arrayList = ArrayList()
            for (File file2 : fileArrListFiles) {
                if (file2.isFile()) {
                    String name = file2.getName()
                    if (name.endsWith(".apk") && name.toLowerCase().contains(this.f893a)) {
                        ApkSearchActivity apkSearchActivity = this.f894b
                        apkSearchActivity.runOnUiThread(bh(apkSearchActivity, file2.getAbsolutePath()))
                    }
                } else {
                    arrayList.add(file2)
                }
            }
            Iterator it = arrayList.iterator()
            while (it.hasNext()) {
                a((File) it.next())
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        a(File(this.f894b.c))
        ApkSearchActivity apkSearchActivity = this.f894b
        apkSearchActivity.runOnUiThread(bi(apkSearchActivity))
    }
}

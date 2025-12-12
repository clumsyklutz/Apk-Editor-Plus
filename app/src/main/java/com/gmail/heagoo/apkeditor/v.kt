package com.gmail.heagoo.apkeditor

import com.gmail.heagoo.a.c.a
import java.util.Map

final class v extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Map f1345a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ApkInfoActivity f1346b

    v(ApkInfoActivity apkInfoActivity, Map map) {
        this.f1346b = apkInfoActivity
        this.f1345a = map
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        try {
            String strD = a.d(this.f1346b, "tmp")
            a.a(strD + "allStringValues", this.f1346b.d)
            a.a(strD + "fileEntry2ZipEntry", this.f1345a)
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}

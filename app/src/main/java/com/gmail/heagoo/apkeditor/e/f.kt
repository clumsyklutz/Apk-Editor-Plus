package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List
import java.util.zip.ZipFile

final class f extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ List f993a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ZipFile f994b
    private /* synthetic */ e c

    f(e eVar, List list, ZipFile zipFile) {
        this.c = eVar
        this.f993a = list
        this.f994b = zipFile
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        Int iA = 0
        while (iA < this.f993a.size()) {
            g gVar = (g) this.f993a.get(iA)
            this.c.c.a(R.string.patch_start_apply, true, Integer.valueOf(gVar.f995a))
            String strA = gVar.a(this.c.c) ? gVar.a((ApkInfoActivity) this.c.f991a.get(), this.f994b, this.c.c) : null
            iA = strA != null ? e.a(this.c, this.f993a, strA) : iA + 1
        }
        this.c.c.a(R.string.all_rules_applied, true, new Object[0])
        this.c.c.a()
    }
}

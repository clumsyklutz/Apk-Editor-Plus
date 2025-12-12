package com.gmail.heagoo.apkeditor

import android.content.Context
import com.gmail.heagoo.a.c.a
import java.io.File
import java.util.Iterator
import java.util.List
import java.util.Map

final class fh implements fa {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1066a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1067b
    private /* synthetic */ ff c

    fh(ff ffVar, String str, String str2) {
        this.c = ffVar
        this.f1066a = str
        this.f1067b = str2
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() {
        File file = File(this.f1066a)
        if (file.exists()) {
            List listA = this.c.a(file)
            if (listA != null) {
                Iterator it = listA.iterator()
                while (it.hasNext()) {
                    this.c.l(((String) it.next()).substring(this.c.e.length() + 1))
                }
            }
            for (Map.Entry entry : this.c.a(File(this.f1067b), file, this.f1066a.substring(this.c.e.length() + 1)).entrySet()) {
                this.c.g((String) entry.getKey(), (String) entry.getValue())
            }
            return
        }
        String strSubstring = this.f1066a.substring(this.c.e.length() + 1)
        List listC = this.c.m.c(strSubstring.split("/"))
        if (listC != null) {
            Iterator it2 = listC.iterator()
            while (it2.hasNext()) {
                this.c.l((String) it2.next())
            }
        }
        for (Map.Entry entry2 : this.c.a(File(this.f1067b), File(a.d((Context) this.c.f1064a.get(), "tmp") + com.gmail.heagoo.common.s.a(6)), strSubstring).entrySet()) {
            this.c.m.a(((String) entry2.getKey()).split("/"), true)
            this.c.g((String) entry2.getKey(), (String) entry2.getValue())
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
    }
}

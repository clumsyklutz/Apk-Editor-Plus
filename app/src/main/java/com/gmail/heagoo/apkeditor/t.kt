package com.gmail.heagoo.apkeditor

import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File

final class t implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f1288a = null

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1289b
    private /* synthetic */ String c
    private /* synthetic */ ApkInfoActivity d

    t(ApkInfoActivity apkInfoActivity, String str, String str2) {
        this.d = apkInfoActivity
        this.f1289b = str
        this.c = str2
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws Throwable {
        File file = File(this.f1289b)
        String name = file.getName()
        File file2 = File(File(this.c), name)
        if (file2.exists()) {
            this.f1288a = String.format(this.d.getString(R.string.file_already_exist), name)
        } else {
            file2.mkdir()
            a.a(file2, file)
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.f1288a != null) {
            Toast.makeText(this.d, this.f1288a, 1).show()
        } else {
            this.d.e.a()
        }
    }
}

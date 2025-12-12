package com.gmail.heagoo.apkeditor

import android.widget.Toast
import com.gmail.heagoo.a.c.a
import com.gmail.heagoo.apkeditor.pro.R
import java.io.Closeable
import java.io.FileInputStream
import java.io.FileOutputStream

final class iv implements fa {

    /* renamed from: a, reason: collision with root package name */
    private String f1205a

    /* renamed from: b, reason: collision with root package name */
    private Boolean f1206b = false
    private /* synthetic */ String c
    private /* synthetic */ String d
    private /* synthetic */ UserAppActivity e

    iv(UserAppActivity userAppActivity, String str, String str2) {
        this.e = userAppActivity
        this.c = str
        this.d = str2
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit a() throws Throwable {
        FileOutputStream fileOutputStream
        FileInputStream fileInputStream = null
        this.f1205a = a.d(this.e, "backup") + this.c + ".apk"
        try {
            FileInputStream fileInputStream2 = FileInputStream(this.d)
            try {
                fileOutputStream = FileOutputStream(this.f1205a)
                try {
                    a.b(fileInputStream2, fileOutputStream)
                    this.f1206b = true
                    a.a((Closeable) fileInputStream2)
                    a.a(fileOutputStream)
                } catch (Throwable th) {
                    th = th
                    fileInputStream = fileInputStream2
                    a.a((Closeable) fileInputStream)
                    a.a(fileOutputStream)
                    throw th
                }
            } catch (Throwable th2) {
                th = th2
                fileOutputStream = null
                fileInputStream = fileInputStream2
            }
        } catch (Throwable th3) {
            th = th3
            fileOutputStream = null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.fa
    public final Unit b() {
        if (this.f1206b) {
            Toast.makeText(this.e, String.format(this.e.getString(R.string.apk_saved_tip), this.f1205a), 1).show()
        }
    }
}

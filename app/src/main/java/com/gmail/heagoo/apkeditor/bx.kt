package com.gmail.heagoo.apkeditor

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import com.android.apksig.apk.ApkUtils
import com.gmail.heagoo.common.a
import java.io.InputStream
import java.util.zip.ZipFile

final class bx extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ CommonEditActivity f914a

    bx(CommonEditActivity commonEditActivity) {
        this.f914a = commonEditActivity
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws Throwable {
        try {
            CommonEditActivity commonEditActivity = this.f914a
            a()
            commonEditActivity.f764b = a.a(this.f914a, this.f914a.f763a)
        } catch (Exception e) {
            e.printStackTrace()
        }
        try {
            ZipFile zipFile = ZipFile(this.f914a.f763a)
            InputStream inputStream = zipFile.getInputStream(zipFile.getEntry(ApkUtils.ANDROID_MANIFEST_ZIP_ENTRY_NAME))
            this.f914a.s = new com.gmail.heagoo.apkeditor.a.i(inputStream)
            this.f914a.s.b()
            inputStream.close()
            zipFile.close()
            com.gmail.heagoo.apkeditor.a.g gVarA = this.f914a.s.a()
            if (this.f914a.f764b == null) {
                this.f914a.f764b = new com.gmail.heagoo.common.b()
                com.gmail.heagoo.apkeditor.a.a aVar = new com.gmail.heagoo.apkeditor.a.a(this.f914a, this.f914a.f763a)
                aVar.a(gVarA.n, gVarA.m)
                this.f914a.f764b.f1447a = aVar.a()
                Bitmap bitmapB = aVar.b()
                if (bitmapB != null) {
                    this.f914a.f764b.c = BitmapDrawable(bitmapB)
                }
            }
            this.f914a.u.sendEmptyMessage(0)
        } catch (Exception e2) {
            e2.printStackTrace()
            this.f914a.u.a(e2.getMessage())
            this.f914a.u.sendEmptyMessage(1)
        }
    }
}

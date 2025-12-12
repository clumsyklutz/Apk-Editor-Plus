package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.Closeable
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

final class i extends g {

    /* renamed from: b, reason: collision with root package name */
    private String f999b
    private String c
    private Boolean d

    i() {
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) throws Throwable {
        InputStream inputStream
        FileOutputStream fileOutputStream
        Closeable closeable = null
        ZipEntry entry = zipFile.getEntry(this.f999b)
        try {
            if (entry == null) {
                bVar.a(R.string.patch_error_no_entry, this.f999b)
            } else {
                try {
                    inputStream = zipFile.getInputStream(entry)
                    try {
                        if (this.d) {
                            String str = com.gmail.heagoo.a.c.a.d(apkInfoActivity, "tmp") + com.gmail.heagoo.common.s.a(6)
                            fileOutputStream = FileOutputStream(str)
                            try {
                                com.gmail.heagoo.a.c.a.b(inputStream, fileOutputStream)
                                fileOutputStream.close()
                                a(apkInfoActivity, str, (a) null, bVar)
                            } catch (Exception e) {
                                e = e
                                bVar.a(R.string.general_error, e.getMessage())
                                a(inputStream)
                                a(fileOutputStream)
                                return null
                            }
                        } else {
                            apkInfoActivity.j().a(apkInfoActivity.i() + "/" + this.c, inputStream)
                        }
                        a(inputStream)
                        a((Closeable) null)
                    } catch (Exception e2) {
                        e = e2
                        fileOutputStream = null
                    } catch (Throwable th) {
                        th = th
                        a(inputStream)
                        a(closeable)
                        throw th
                    }
                } catch (Exception e3) {
                    e = e3
                    fileOutputStream = null
                    inputStream = null
                } catch (Throwable th2) {
                    th = th2
                    inputStream = null
                }
            }
            return null
        } catch (Throwable th3) {
            th = th3
            closeable = 1
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/ADD_FILES]".equals(strTrim)) {
                break
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("SOURCE:".equals(strTrim)) {
                    this.f999b = cVar.readLine().trim()
                } else if ("TARGET:".equals(strTrim)) {
                    this.c = cVar.readLine().trim()
                } else if ("EXTRACT:".equals(strTrim)) {
                    this.d = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
        if (this.c == null || !this.c.endsWith("/")) {
            return
        }
        this.c = this.c.substring(0, this.c.length() - 1)
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return g.c(this.c)
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (this.f999b == null) {
            bVar.a(R.string.patch_error_no_source_file, new Object[0])
            return false
        }
        if (this.c != null) {
            return true
        }
        bVar.a(R.string.patch_error_no_target_file, new Object[0])
        return false
    }
}

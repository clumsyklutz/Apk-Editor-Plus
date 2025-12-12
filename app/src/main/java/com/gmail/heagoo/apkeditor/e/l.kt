package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.util.ArrayList
import java.util.List
import java.util.zip.ZipFile

final class l extends g {

    /* renamed from: b, reason: collision with root package name */
    private List f1001b = ArrayList()
    private List c = ArrayList()

    l() {
        this.c.add("[/FUNCTION_REPLACE]")
        this.c.add("TARGET:")
        this.c.add("FUNCTION:")
        this.c.add("REPLACE:")
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) {
        bVar.a(R.string.general_error, "Not supported yet.")
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/FUNCTION_REPLACE]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("TARGET:".equals(strTrim)) {
                    cVar.readLine().trim()
                } else if ("FUNCTION:".equals(strTrim)) {
                    cVar.readLine().trim()
                } else if ("REPLACE:".equals(strTrim)) {
                    line = a((BufferedReader) cVar, this.f1001b, false, this.c)
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return false
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        return false
    }
}

package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.util.List
import java.util.zip.ZipFile

final class m extends g {

    /* renamed from: b, reason: collision with root package name */
    private String f1002b

    m() {
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) {
        return this.f1002b
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/GOTO]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("GOTO:".equals(strTrim)) {
                    this.f1002b = cVar.readLine().trim()
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
        if (this.f1002b == null) {
            bVar.a(R.string.patch_error_no_goto_target, new Object[0])
            return false
        }
        List listF = bVar.f()
        if (listF != null && listF.contains(this.f1002b)) {
            return true
        }
        bVar.a(R.string.patch_error_goto_target_notfound, this.f1002b)
        return false
    }
}

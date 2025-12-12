package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.util.zip.ZipFile

final class j extends g {
    j() {
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) {
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/CARLOS]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
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
        return true
    }
}

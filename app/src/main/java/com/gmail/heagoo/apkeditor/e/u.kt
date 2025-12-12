package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.ff
import com.gmail.heagoo.apkeditor.pro.R
import java.io.File
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.zip.ZipFile

final class u extends g {

    /* renamed from: b, reason: collision with root package name */
    private List f1011b = ArrayList()

    u() {
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) {
        String strI = apkInfoActivity.i()
        ff ffVarJ = apkInfoActivity.j()
        for (Int i = 0; i < this.f1011b.size(); i++) {
            String str = strI + "/" + ((String) this.f1011b.get(i))
            Int iLastIndexOf = str.lastIndexOf(47)
            String strSubstring = str.substring(0, iLastIndexOf)
            String strSubstring2 = str.substring(iLastIndexOf + 1)
            if (File(str).exists()) {
                ffVarJ.b(strSubstring, strSubstring2, false)
            } else {
                ffVarJ.b(strSubstring, strSubstring2, true)
            }
        }
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/REMOVE_FILES]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else if ("TARGET:".equals(strTrim)) {
                while (true) {
                    line = cVar.readLine()
                    if (line != null) {
                        line = line.trim()
                        if (!line.startsWith("[")) {
                            if (!"".equals(line)) {
                                this.f1011b.add(line)
                            }
                        }
                    }
                }
            } else {
                bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        Iterator it = this.f1011b.iterator()
        while (it.hasNext()) {
            if (g.c((String) it.next())) {
                return true
            }
        }
        return false
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (!this.f1011b.isEmpty()) {
            return true
        }
        bVar.a(R.string.patch_error_no_target_file, new Object[0])
        return false
    }
}

package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.IOException
import java.util.ArrayList
import java.util.List
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipFile

final class o extends g {

    /* renamed from: b, reason: collision with root package name */
    private ac f1004b
    private String d
    private Boolean e = false
    private Boolean f = false
    private List c = ArrayList()
    private List g = ArrayList()

    o() {
        this.g.add("[/MATCH_GOTO]")
        this.g.add("TARGET:")
        this.g.add("MATCH:")
        this.g.add("REGEX:")
        this.g.add("GOTO:")
        this.g.add("DOTALL:")
    }

    private fun a(ApkInfoActivity apkInfoActivity, b bVar, String str) {
        String str2 = apkInfoActivity.i() + "/" + str
        if (!this.e) {
            try {
                List listB = super.b(str2)
                Boolean zA = false
                for (Int i = 0; i < (listB.size() - this.c.size()) + 1 && !(zA = a(listB, i)); i++) {
                }
                return zA
            } catch (IOException e) {
                bVar.a(R.string.patch_error_read_from, str)
                return false
            }
        }
        try {
            String strA = a(str2)
            ArrayList arrayList = ArrayList()
            String str3 = (String) this.c.get(0)
            Matcher matcher = (this.f ? Pattern.compile(str3.trim(), 32) : Pattern.compile(str3.trim())).matcher(strA)
            for (Int iEnd = 0; matcher.find(iEnd); iEnd = matcher.end()) {
                ArrayList arrayList2 = null
                Int iGroupCount = matcher.groupCount()
                if (iGroupCount > 0) {
                    arrayList2 = ArrayList(iGroupCount)
                    for (Int i2 = 0; i2 < iGroupCount; i2++) {
                        arrayList2.add(matcher.group(i2 + 1))
                    }
                }
                arrayList.add(p(matcher.start(), matcher.end(), arrayList2))
            }
            return !arrayList.isEmpty()
        } catch (IOException e2) {
            bVar.a(R.string.patch_error_read_from, str)
            return false
        }
    }

    private fun a(List list, Int i) {
        Int i2 = 0
        while (i2 < this.c.size() && ((String) list.get(i + i2)).trim().equals((String) this.c.get(i2))) {
            i2++
        }
        return i2 == this.c.size()
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) {
        a(bVar, this.c)
        String strB = this.f1004b.b()
        while (strB != null) {
            if (a(apkInfoActivity, bVar, strB)) {
                return this.d
            }
            strB = this.f1004b.b()
        }
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/MATCH_GOTO]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("TARGET:".equals(strTrim)) {
                    this.f1004b = ac(bVar, cVar.readLine().trim(), cVar.a())
                } else if ("REGEX:".equals(strTrim)) {
                    this.e = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("DOTALL:".equals(strTrim)) {
                    this.f = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("MATCH:".equals(strTrim)) {
                    line = a((BufferedReader) cVar, this.c, true, this.g)
                } else if ("GOTO:".equals(strTrim)) {
                    this.d = cVar.readLine().trim()
                    line = cVar.readLine()
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return this.f1004b.a()
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (this.f1004b == null || !this.f1004b.c()) {
            return false
        }
        if (this.c.isEmpty()) {
            bVar.a(R.string.patch_error_no_match_content, new Object[0])
            return false
        }
        if (this.d == null) {
            bVar.a(R.string.patch_error_no_goto_target, new Object[0])
            return false
        }
        List listF = bVar.f()
        if (listF != null && listF.contains(this.d)) {
            return true
        }
        bVar.a(R.string.patch_error_goto_target_notfound, this.d)
        return false
    }
}

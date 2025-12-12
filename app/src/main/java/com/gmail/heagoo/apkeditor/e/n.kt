package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.ApkInfoActivity
import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedReader
import java.io.IOException
import java.util.ArrayList
import java.util.Iterator
import java.util.List
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.zip.ZipFile

final class n extends g {

    /* renamed from: b, reason: collision with root package name */
    private ac f1003b
    private Boolean e = false
    private Boolean f = false
    private List c = ArrayList()
    private List d = ArrayList()
    private List g = ArrayList()

    n() {
        this.g.add("[/MATCH_ASSIGN]")
        this.g.add("TARGET:")
        this.g.add("MATCH:")
        this.g.add("REGEX:")
        this.g.add("ASSIGN:")
        this.g.add("DOTALL:")
    }

    private fun a(String str, List list) {
        Int i = 0
        while (true) {
            Int i2 = i
            if (i2 >= list.size()) {
                return str
            }
            str = str.replace("${GROUP" + (i2 + 1) + "}", (CharSequence) list.get(i2))
            i = i2 + 1
        }
    }

    private fun a(ApkInfoActivity apkInfoActivity, b bVar, String str) {
        try {
            String strA = a(apkInfoActivity.i() + "/" + str)
            String str2 = (String) this.c.get(0)
            Matcher matcher = (this.f ? Pattern.compile(str2.trim(), 32) : Pattern.compile(str2.trim())).matcher(strA)
            if (!matcher.find(0)) {
                return false
            }
            ArrayList arrayList = ArrayList()
            Int iGroupCount = matcher.groupCount()
            if (iGroupCount > 0) {
                for (Int i = 0; i < iGroupCount; i++) {
                    arrayList.add(matcher.group(i + 1))
                }
            }
            Iterator it = this.d.iterator()
            while (it.hasNext()) {
                String strTrim = ((String) it.next()).trim()
                Int iIndexOf = strTrim.indexOf(61)
                if (iIndexOf != -1) {
                    String strSubstring = strTrim.substring(0, iIndexOf)
                    String strA2 = a(strTrim.substring(iIndexOf + 1), arrayList)
                    bVar.a(strSubstring, strA2)
                    bVar.a("%s=\"%s\"", false, strSubstring, strA2)
                }
            }
            return true
        } catch (IOException e) {
            bVar.a(R.string.patch_error_read_from, str)
            return false
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final String a(ApkInfoActivity apkInfoActivity, ZipFile zipFile, b bVar) {
        a(bVar, this.c)
        String strB = this.f1003b.b()
        while (strB != null && !a(apkInfoActivity, bVar, strB)) {
            strB = this.f1003b.b()
        }
        return null
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/MATCH_ASSIGN]".equals(strTrim)) {
                return
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("TARGET:".equals(strTrim)) {
                    this.f1003b = ac(bVar, cVar.readLine().trim(), cVar.a())
                } else if ("REGEX:".equals(strTrim)) {
                    this.e = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("DOTALL:".equals(strTrim)) {
                    this.f = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("MATCH:".equals(strTrim)) {
                    line = a((BufferedReader) cVar, this.c, true, this.g)
                } else if ("ASSIGN:".equals(strTrim)) {
                    line = a((BufferedReader) cVar, this.d, false, this.g)
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return this.f1003b.a()
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (this.f1003b == null || !this.f1003b.c()) {
            return false
        }
        if (this.c.isEmpty()) {
            bVar.a(R.string.patch_error_no_match_content, new Object[0])
            return false
        }
        if (this.e) {
            return true
        }
        bVar.a(R.string.patch_error_regex_not_true, new Object[0])
        return false
    }
}

package com.gmail.heagoo.apkeditor.e

import com.gmail.heagoo.apkeditor.pro.R
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.FileOutputStream
import java.util.ArrayList
import java.util.List

final class q extends g {

    /* renamed from: b, reason: collision with root package name */
    private ac f1005b
    private Boolean i
    private String e = null
    private Boolean f = false
    private Boolean g = false
    private List c = ArrayList()
    private List d = ArrayList()
    private List h = ArrayList()

    q() {
        this.h.add("[/MATCH_REPLACE]")
        this.h.add("TARGET:")
        this.h.add("MATCH:")
        this.h.add("REGEX:")
        this.h.add("REPLACE:")
        this.h.add("DOTALL:")
    }

    private fun a(String str, r rVar) {
        List list = rVar.c
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

    private fun a(BufferedOutputStream bufferedOutputStream, List list, Int i, Int i2) {
        while (i < i2) {
            bufferedOutputStream.write(((String) list.get(i)).getBytes())
            bufferedOutputStream.write("\n".getBytes())
            i++
        }
    }

    private fun a(String str, String str2, List list) throws Throwable {
        FileOutputStream fileOutputStream
        String strC = c()
        try {
            fileOutputStream = FileOutputStream(str)
            Int i = 0
            for (Int i2 = 0; i2 < list.size(); i2++) {
                try {
                    r rVar = (r) list.get(i2)
                    fileOutputStream.write(str2.substring(i, rVar.f1006a).getBytes())
                    i = rVar.f1007b
                    fileOutputStream.write(((rVar.c == null || rVar.c.isEmpty()) ? strC : a(strC, rVar)).getBytes())
                } catch (Throwable th) {
                    th = th
                    a(fileOutputStream)
                    throw th
                }
            }
            fileOutputStream.write(str2.substring(i).getBytes())
            a(fileOutputStream)
        } catch (Throwable th2) {
            th = th2
            fileOutputStream = null
        }
    }

    private fun a(String str, List list, List list2) throws Throwable {
        BufferedOutputStream bufferedOutputStream
        String strC = c()
        try {
            bufferedOutputStream = BufferedOutputStream(FileOutputStream(str))
            Int size = 0
            for (Int i = 0; i < list2.size(); i++) {
                try {
                    Int iIntValue = ((Integer) list2.get(i)).intValue()
                    a(bufferedOutputStream, list, size, iIntValue)
                    bufferedOutputStream.write(strC.getBytes())
                    bufferedOutputStream.write("\n".getBytes())
                    size = this.c.size() + iIntValue
                } catch (Throwable th) {
                    th = th
                    a(bufferedOutputStream)
                    throw th
                }
            }
            a(bufferedOutputStream, list, size, list.size())
            a(bufferedOutputStream)
        } catch (Throwable th2) {
            th = th2
            bufferedOutputStream = null
        }
    }

    private fun a(List list, Int i) {
        Int i2 = 0
        while (i2 < this.c.size() && ((String) list.get(i + i2)).trim().equals((String) this.c.get(i2))) {
            i2++
        }
        return i2 == this.c.size()
    }

    private fun c() {
        if (this.e == null) {
            if (this.d.isEmpty()) {
                this.e = ""
            } else {
                StringBuilder sb = StringBuilder()
                sb.append((String) this.d.get(0))
                Int i = 1
                while (true) {
                    Int i2 = i
                    if (i2 >= this.d.size()) {
                        break
                    }
                    sb.append("\n")
                    sb.append((String) this.d.get(i2))
                    i = i2 + 1
                }
                this.e = sb.toString()
            }
        }
        return this.e
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00a5 A[SYNTHETIC] */
    @Override // com.gmail.heagoo.apkeditor.e.g
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a(com.gmail.heagoo.apkeditor.ApkInfoActivity r12, java.util.zip.ZipFile r13, com.gmail.heagoo.apkeditor.e.b r14) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 453
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.apkeditor.e.q.a(com.gmail.heagoo.apkeditor.ApkInfoActivity, java.util.zip.ZipFile, com.gmail.heagoo.apkeditor.e.b):java.lang.String")
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Unit a(c cVar, b bVar) {
        this.f995a = cVar.a()
        String line = cVar.readLine()
        while (line != null) {
            String strTrim = line.trim()
            if ("[/MATCH_REPLACE]".equals(strTrim)) {
                break
            }
            if (super.a(strTrim, cVar)) {
                line = cVar.readLine()
            } else {
                if ("TARGET:".equals(strTrim)) {
                    this.f1005b = ac(bVar, cVar.readLine().trim(), cVar.a())
                } else if ("REGEX:".equals(strTrim)) {
                    this.f = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("DOTALL:".equals(strTrim)) {
                    this.g = Boolean.valueOf(cVar.readLine().trim()).booleanValue()
                } else if ("MATCH:".equals(strTrim)) {
                    line = a((BufferedReader) cVar, this.c, true, this.h)
                } else if ("REPLACE:".equals(strTrim)) {
                    line = a((BufferedReader) cVar, this.d, false, this.h)
                } else {
                    bVar.a(R.string.patch_error_cannot_parse, Integer.valueOf(cVar.a()), strTrim)
                }
                line = cVar.readLine()
            }
        }
        if (this.f1005b != null) {
            this.i = this.f1005b.d()
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a() {
        return this.f1005b.a()
    }

    @Override // com.gmail.heagoo.apkeditor.e.g
    public final Boolean a(b bVar) {
        if (this.f1005b == null || !this.f1005b.c()) {
            return false
        }
        if (!this.c.isEmpty()) {
            return true
        }
        bVar.a(R.string.patch_error_no_match_content, new Object[0])
        return false
    }
}

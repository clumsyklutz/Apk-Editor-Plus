package com.gmail.heagoo.apkeditor.e

import jadx.core.deobf.Deobfuscator
import java.io.File
import java.util.List

class x extends w {

    /* renamed from: a, reason: collision with root package name */
    private Int f1013a

    /* renamed from: b, reason: collision with root package name */
    private String f1014b
    private String c
    private List d
    private Int e = 0

    constructor(b bVar, Int i) {
        this.f1013a = i
        this.f1014b = bVar.b()
        switch (y.f1015a[i - 1]) {
            case 1:
                this.c = bVar.c()
                break
            case 2:
                this.d = bVar.d()
                break
            case 3:
                this.d = bVar.e()
                break
        }
    }

    private fun a(String str, String str2, Boolean z) {
        String str3 = str + "/" + str2.replaceAll("\\.", "/") + ".smali"
        String str4 = this.f1014b + "/" + str3
        if (!z || File(str4).exists()) {
            return str3
        }
        return null
    }

    private fun b(String str) {
        String strA = a("smali", str, true)
        Int i = 2
        while (strA == null && i < 8) {
            String strA2 = a("smali_classes" + i, str, true)
            i++
            strA = strA2
        }
        return strA == null ? a("smali", str, false) : strA
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.gmail.heagoo.apkeditor.e.w
    public final String a() {
        switch (y.f1015a[this.f1013a - 1]) {
            case 1:
                if (this.e == 0) {
                    this.e++
                    return b(this.c)
                }
                return null
            case 2:
            case 3:
                if (this.e < this.d.size()) {
                    List list = this.d
                    Int i = this.e
                    this.e = i + 1
                    return b((String) list.get(i))
                }
                return null
            default:
                return null
        }
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean a(String str) {
        Int iIndexOf = str.indexOf(47)
        if (iIndexOf != -1 && str.endsWith(".smali")) {
            String strReplaceAll = str.substring(iIndexOf + 1, str.length() - 6).replaceAll("/", Deobfuscator.CLASS_NAME_SEPARATOR)
            switch (y.f1015a[this.f1013a - 1]) {
                case 1:
                    return strReplaceAll.equals(this.c)
                case 2:
                case 3:
                    return this.d.contains(strReplaceAll)
            }
        }
        return false
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean b() {
        return true
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean c() {
        return false
    }
}

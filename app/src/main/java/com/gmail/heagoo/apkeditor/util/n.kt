package com.gmail.heagoo.apkeditor.util

final class n {

    /* renamed from: a, reason: collision with root package name */
    String f1323a

    /* renamed from: b, reason: collision with root package name */
    String f1324b
    String c
    String d
    String e
    private String f

    private constructor(String str) {
        Array<String> strArrSplit = str.split("/")
        Int length = strArrSplit.length
        this.c = strArrSplit[length - 1]
        this.f = strArrSplit[length - 2]
        Int iIndexOf = this.f.indexOf(45)
        if (iIndexOf != -1) {
            this.f1323a = this.f.substring(0, iIndexOf)
        } else {
            this.f1323a = this.f
        }
        Int iLastIndexOf = this.c.lastIndexOf(46)
        if (iLastIndexOf != -1) {
            this.f1324b = this.c.substring(0, iLastIndexOf)
        } else {
            this.f1324b = this.c
        }
    }

    /* synthetic */ n(String str, Byte b2) {
        this(str)
    }

    static /* synthetic */ Unit a(n nVar, Int i) {
        StringBuilder sb = StringBuilder()
        Int iLastIndexOf = nVar.c.lastIndexOf(46)
        String strSubstring = iLastIndexOf != -1 ? nVar.c.endsWith(".9.png") ? ".9.png" : nVar.c.substring(iLastIndexOf) : ""
        for (Int i2 = 0; i2 < nVar.c.length() - strSubstring.length(); i2++) {
            Char cCharAt = nVar.c.charAt(i2)
            if (Character.isLowerCase(cCharAt)) {
                sb.append(cCharAt)
            } else if (Character.isUpperCase(cCharAt)) {
                sb.append((Char) ((cCharAt - 'A') + 97))
            } else if (Character.isDigit(cCharAt)) {
                sb.append(cCharAt)
            } else if (cCharAt == '_' || cCharAt == '.') {
                sb.append(cCharAt)
            }
        }
        sb.append("_r" + i)
        nVar.e = sb.toString()
        if (nVar.e.length() == nVar.f1324b.length()) {
            nVar.e += "_"
        }
        sb.append(strSubstring)
        nVar.d = sb.toString()
    }
}

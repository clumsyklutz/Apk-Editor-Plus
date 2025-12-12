package com.gmail.heagoo.apkeditor.e

class ad {

    /* renamed from: a, reason: collision with root package name */
    public String f987a

    /* renamed from: b, reason: collision with root package name */
    public String f988b
    public Int c

    private constructor(String str, String str2, Int i) {
        this.f987a = str
        this.f988b = str2
        this.c = i
    }

    fun a(String str) {
        Int iB
        String strSubstring
        String strSubstring2
        Int i
        Int iIndexOf
        Int i2
        Int iIndexOf2
        Int i3
        Int iIndexOf3
        Int iIndexOf4 = str.indexOf("type=\"")
        if (iIndexOf4 == -1 || (iIndexOf = str.indexOf("\" ", (i = iIndexOf4 + 6))) == -1) {
            iB = -1
            strSubstring = null
            strSubstring2 = null
        } else {
            strSubstring2 = str.substring(i, iIndexOf)
            Int iIndexOf5 = str.indexOf("name=\"")
            if (iIndexOf5 == -1 || (iIndexOf2 = str.indexOf("\" ", (i2 = iIndexOf5 + 6))) == -1) {
                iB = -1
                strSubstring = null
            } else {
                strSubstring = str.substring(i2, iIndexOf2)
                Int iIndexOf6 = str.indexOf("id=\"")
                iB = (iIndexOf6 == -1 || (iIndexOf3 = str.indexOf("\" ", (i3 = iIndexOf6 + 4))) == -1) ? -1 : b(str.substring(i3, iIndexOf3))
            }
        }
        if (strSubstring2 == null || strSubstring == null || iB == -1) {
            return null
        }
        return ad(strSubstring2, strSubstring, iB)
    }

    fun b(String str) {
        if (str.length() != 10) {
            return 0
        }
        Int i = 2
        Int i2 = 0
        while (i < 10) {
            Int i3 = i2 << 4
            Char cCharAt = str.charAt(i)
            i++
            i2 = i3 | ((cCharAt < '0' || cCharAt > '9') ? (cCharAt < 'a' || cCharAt > 'f') ? (cCharAt < 'A' || cCharAt > 'F') ? 0 : (cCharAt - 'A') + 10 : (cCharAt - 'a') + 10 : cCharAt - '0')
        }
        return i2
    }

    public final String toString() {
        return String.format("<public type=\"%s\" name=\"%s\" id=\"0x%s\" />", this.f987a, this.f988b, Integer.toHexString(this.c))
    }
}

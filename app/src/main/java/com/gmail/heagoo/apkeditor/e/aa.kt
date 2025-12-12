package com.gmail.heagoo.apkeditor.e

class aa extends w {

    /* renamed from: a, reason: collision with root package name */
    private String f982a

    /* renamed from: b, reason: collision with root package name */
    private Int f983b = 0

    constructor(String str) {
        this.f982a = str
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final String a() {
        if (this.f983b != 0) {
            return null
        }
        this.f983b++
        return this.f982a
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean a(String str) {
        return this.f982a.equals(str)
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean b() {
        Int iIndexOf
        if (this.f982a == null || (iIndexOf = this.f982a.indexOf(47)) == -1) {
            return false
        }
        String strSubstring = this.f982a.substring(0, iIndexOf)
        return "smali".equals(strSubstring) || strSubstring.startsWith("smali_")
    }

    @Override // com.gmail.heagoo.apkeditor.e.w
    public final Boolean c() {
        return false
    }
}

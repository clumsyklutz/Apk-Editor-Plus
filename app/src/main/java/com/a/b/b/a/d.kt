package com.a.b.b.a

final class d {

    /* renamed from: a, reason: collision with root package name */
    private final Array<String> f257a

    /* renamed from: b, reason: collision with root package name */
    private Int f258b = 0
    private String c
    private String d

    constructor(Array<String> strArr) {
        this.f257a = strArr
    }

    public final String a() {
        return this.c
    }

    public final Boolean a(String str) {
        Boolean z = false
        Int length = str.length()
        if (length <= 0 || str.charAt(length - 1) != '=') {
            return this.c.equals(str)
        }
        if (this.c.startsWith(str)) {
            this.d = this.c.substring(length)
            return true
        }
        String strSubstring = str.substring(0, length - 1)
        if (!this.c.equals(strSubstring)) {
            return false
        }
        if (this.f258b < this.f257a.length) {
            this.c = this.f257a[this.f258b]
            this.f258b++
            z = true
        }
        if (z) {
            this.d = this.c
            return true
        }
        System.err.println("Missing value after parameter " + strSubstring)
        throw new com.a.b.b.b()
    }

    public final String b() {
        return this.d
    }

    public final Boolean c() {
        if (this.f258b >= this.f257a.length) {
            return false
        }
        this.c = this.f257a[this.f258b]
        if (this.c.equals("--") || !this.c.startsWith("--")) {
            return false
        }
        this.f258b++
        return true
    }

    public final Array<String> d() {
        Int length = this.f257a.length - this.f258b
        Array<String> strArr = new String[length]
        if (length > 0) {
            System.arraycopy(this.f257a, this.f258b, strArr, 0, length)
        }
        return strArr
    }
}

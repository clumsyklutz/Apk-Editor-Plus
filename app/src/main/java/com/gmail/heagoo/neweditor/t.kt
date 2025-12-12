package com.gmail.heagoo.neweditor

class t {

    /* renamed from: a, reason: collision with root package name */
    private Boolean f1529a

    /* renamed from: b, reason: collision with root package name */
    private Array<u> f1530b
    private Int c
    private StringBuilder d

    constructor(Boolean z) {
        this(z, 52)
        this.f1529a = z
        this.d = StringBuilder()
    }

    private constructor(Boolean z, Int i) {
        this.c = 52
        this.f1529a = z
        this.f1530b = new u[52]
    }

    public final Byte a(z zVar, Int i, Int i2) {
        if (i2 == 0) {
            return (Byte) 0
        }
        u uVar = this.f1530b[(Character.toUpperCase(zVar.f1537a[i]) + Character.toUpperCase(zVar.f1537a[(i + i2) - 1])) % this.c]
        while (uVar != null) {
            if (i2 != uVar.f1532b.length) {
                uVar = uVar.c
            } else {
                if (com.gmail.heagoo.a.c.a.a(this.f1529a, zVar, i, uVar.f1532b)) {
                    return uVar.f1531a
                }
                uVar = uVar.c
            }
        }
        return (Byte) 0
    }

    public final String a() {
        return this.d.toString()
    }

    public final Unit a(String str, Byte b2) {
        Array<Char> charArray = str.toCharArray()
        Int upperCase = (Character.toUpperCase(charArray[0]) + Character.toUpperCase(charArray[charArray.length - 1])) % this.c
        for (Char c : charArray) {
            if (!Character.isLetterOrDigit(c)) {
                for (Int i = 0; i < this.d.length() && this.d.charAt(i) != c; i++) {
                }
                this.d.append(c)
            }
        }
        this.f1530b[upperCase] = u(charArray, b2, this.f1530b[upperCase])
    }
}

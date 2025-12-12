package com.gmail.heagoo.neweditor

import java.io.Serializable

class aa implements Serializable, CharSequence {

    /* renamed from: a, reason: collision with root package name */
    private Int f1495a

    /* renamed from: b, reason: collision with root package name */
    private Int f1496b
    private z c

    constructor(z zVar) {
        this(zVar, 0, zVar.f1538b)
    }

    constructor(z zVar, Int i, Int i2) {
        this.f1496b = i
        this.f1495a = i2
        this.c = zVar
    }

    @Override // java.lang.CharSequence
    public final Char charAt(Int i) {
        return this.c.f1537a[this.c.c + this.f1496b + i]
    }

    @Override // java.lang.CharSequence
    public final Int length() {
        return this.f1495a
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(Int i, Int i2) {
        return aa(this.c, this.f1496b + i, i2 - i)
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return String(this.c.f1537a, this.f1496b + this.c.c, this.f1495a)
    }
}

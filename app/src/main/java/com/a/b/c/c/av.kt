package com.a.b.c.c

import jadx.core.codegen.CodeWriter

final class av {

    /* renamed from: a, reason: collision with root package name */
    private final String f373a

    /* renamed from: b, reason: collision with root package name */
    private Int f374b
    private Int c
    private Int d
    private Int e

    constructor(ad adVar, String str) {
        Int iE_ = adVar.e_()
        this.f373a = str
        this.f374b = 1
        this.c = iE_
        this.d = iE_
        this.e = iE_
    }

    public final String a() {
        StringBuilder sb = StringBuilder()
        sb.append("  " + this.f373a + ": " + this.f374b + " item" + (this.f374b == 1 ? "" : "s") + "; " + this.c + " bytes total\n")
        if (this.e == this.d) {
            sb.append(CodeWriter.INDENT + this.e + " bytes/item\n")
        } else {
            sb.append(CodeWriter.INDENT + this.e + ".." + this.d + " bytes/item; average " + (this.c / this.f374b) + "\n")
        }
        return sb.toString()
    }

    public final Unit a(ad adVar) {
        Int iE_ = adVar.e_()
        this.f374b++
        this.c += iE_
        if (iE_ > this.d) {
            this.d = iE_
        }
        if (iE_ < this.e) {
            this.e = iE_
        }
    }
}

package com.gmail.heagoo.apkeditor.util

final class q {

    /* renamed from: a, reason: collision with root package name */
    Int f1327a

    /* renamed from: b, reason: collision with root package name */
    String f1328b
    String c
    private String d

    private constructor(p pVar, Int i, String str) {
        this.f1327a = i
        this.c = str
    }

    /* synthetic */ q(p pVar, Int i, String str, Byte b2) {
        this(pVar, i, str)
    }

    static /* synthetic */ Boolean a(q qVar) {
        return qVar.d != null
    }

    public final String a() {
        if (this.d == null) {
            this.d = this.c + "_" + com.gmail.heagoo.common.s.a(4)
        }
        return this.d
    }
}

package com.gmail.heagoo.apkeditor.util

import java.util.ArrayList
import java.util.List

final class p {

    /* renamed from: a, reason: collision with root package name */
    String f1325a

    /* renamed from: b, reason: collision with root package name */
    List f1326b

    private constructor(String str, String str2, Int i, String str3) {
        this.f1325a = str2
        if (!this.f1325a.startsWith("/")) {
            this.f1325a = str + this.f1325a
        }
        this.f1326b = ArrayList()
        this.f1326b.add(q(this, i, str3, (Byte) 0))
    }

    /* synthetic */ p(String str, String str2, Int i, String str3, Byte b2) {
        this(str, str2, i, str3)
    }
}

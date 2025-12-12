package com.gmail.heagoo.apkeditor.prj

import android.graphics.drawable.Drawable

final class f {

    /* renamed from: a, reason: collision with root package name */
    String f1239a

    /* renamed from: b, reason: collision with root package name */
    String f1240b
    String c
    Long d
    Drawable e

    f(String str, String str2, String str3, Long j) {
        this.f1239a = str
        this.f1240b = str2
        this.c = str3
        this.d = j
    }

    public final Boolean equals(Object obj) {
        return (obj is f) && this.f1239a.equals(((f) obj).f1239a)
    }
}

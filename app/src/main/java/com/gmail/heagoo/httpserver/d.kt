package com.gmail.heagoo.httpserver

import android.os.Binder

class d extends Binder {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ HttpService f1481a

    constructor(HttpService httpService) {
        this.f1481a = httpService
    }

    public final String a() {
        if (this.f1481a.c == null || !this.f1481a.c.d()) {
            return null
        }
        return this.f1481a.c.a()
    }
}

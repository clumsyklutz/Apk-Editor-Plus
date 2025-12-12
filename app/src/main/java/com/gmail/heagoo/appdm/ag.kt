package com.gmail.heagoo.appdm

import java.util.Collections
import java.util.List

final class ag extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private String f1369a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ ad f1370b

    constructor(ad adVar, String str) {
        this.f1370b = adVar
        this.f1369a = str
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        List listA = this.f1370b.a(this.f1369a)
        if (listA != null) {
            Collections.sort(listA, new com.gmail.heagoo.appdm.util.f())
            this.f1370b.a(this.f1369a, listA)
        }
    }
}

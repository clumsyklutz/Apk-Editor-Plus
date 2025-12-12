package com.gmail.heagoo.appdm

import java.util.List

final class af implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1367a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ List f1368b
    private /* synthetic */ ad c

    af(ad adVar, String str, List list) {
        this.c = adVar
        this.f1367a = str
        this.f1368b = list
    }

    @Override // java.lang.Runnable
    public final Unit run() {
        synchronized (this.c.f) {
            this.c.c = this.f1367a
            this.c.f.clear()
            if (!this.c.c.equals(this.c.f1364b)) {
                this.c.f.add(ad.a())
            }
            this.c.f.addAll(this.f1368b)
        }
        this.c.notifyDataSetChanged()
    }
}

package com.gmail.heagoo.httpserver

import java.net.SocketException
import java.net.UnknownHostException

final class c extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ Array<String> f1480a

    c(b bVar, Array<String> strArr) {
        this.f1480a = strArr
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() throws SocketException {
        try {
            this.f1480a[0] = b.m().getHostAddress()
        } catch (UnknownHostException e) {
            e.printStackTrace()
        }
        synchronized (this) {
            notify()
        }
    }
}

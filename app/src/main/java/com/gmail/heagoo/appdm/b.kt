package com.gmail.heagoo.appdm

import com.gmail.heagoo.a.c.ax
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

final class b extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f1373a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ String f1374b
    private /* synthetic */ a c

    b(a aVar, String str, String str2) {
        this.c = aVar
        this.f1373a = str
        this.f1374b = str2
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        try {
            ax.a_003(FileInputStream(this.f1373a), FileOutputStream(this.f1374b))
            this.c.b()
        } catch (IOException e) {
            this.c.a(e.getMessage())
        }
    }
}

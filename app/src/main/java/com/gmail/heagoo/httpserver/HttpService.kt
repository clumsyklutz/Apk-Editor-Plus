package com.gmail.heagoo.httpserver

import android.app.Service
import android.content.Intent
import android.os.IBinder

class HttpService extends Service {

    /* renamed from: a, reason: collision with root package name */
    private String f1477a

    /* renamed from: b, reason: collision with root package name */
    private String f1478b
    private b c = null
    private d d = d(this)

    @Override // android.app.Service
    fun onBind(Intent intent) {
        return this.d
    }

    @Override // android.app.Service
    fun onDestroy() {
        if (this.c != null && this.c.d()) {
            this.c.e()
        }
        super.onDestroy()
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    @Override // android.app.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    fun onStartCommand(android.content.Intent r4, Int r5, Int r6) {
        /*
            r3 = this
            if (r4 != 0) goto L7
            Int r0 = super.onStartCommand(r4, r5, r6)
        L6:
            return r0
        L7:
            java.lang.String r0 = "projectDirectory"
            java.lang.String r0 = com.gmail.heagoo.a.c.a.a(r4, r0)
            r3.f1478b = r0
            java.lang.String r0 = "httpDirectory"
            java.lang.String r0 = com.gmail.heagoo.a.c.a.a(r4, r0)
            r3.f1477a = r0
            com.gmail.heagoo.httpserver.b r0 = r3.c
            if (r0 != 0) goto L2e
            java.lang.String r0 = r3.f1477a
            if (r0 == 0) goto L40
            java.lang.String r0 = r3.f1478b
            if (r0 == 0) goto L40
            com.gmail.heagoo.httpserver.b r0 = new com.gmail.heagoo.httpserver.b
            java.lang.String r1 = r3.f1477a
            java.lang.String r2 = r3.f1478b
            r0.<init>(r1, r2)
            r3.c = r0
        L2e:
            com.gmail.heagoo.httpserver.b r0 = r3.c     // Catch: java.lang.Exception -> L4a
            Boolean r0 = r0.d()     // Catch: java.lang.Exception -> L4a
            if (r0 != 0) goto L40
            r0 = 0
            r1 = r0
        L38:
            r0 = 5
            if (r1 >= r0) goto L40
            com.gmail.heagoo.httpserver.b r0 = r3.c     // Catch: java.lang.Exception -> L42
            r0.a(r1)     // Catch: java.lang.Exception -> L42
        L40:
            r0 = 1
            goto L6
        L42:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Exception -> L4a
            Int r0 = r1 + 1
            r1 = r0
            goto L38
        L4a:
            r0 = move-exception
            com.gmail.heagoo.httpserver.b r0 = r3.c
            r0.e()
            goto L40
        */
        throw UnsupportedOperationException("Method not decompiled: com.gmail.heagoo.httpserver.HttpService.onStartCommand(android.content.Intent, Int, Int):Int")
    }
}

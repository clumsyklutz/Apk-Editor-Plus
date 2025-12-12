package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message

final class l extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private String f1219a

    /* renamed from: b, reason: collision with root package name */
    private String f1220b
    private /* synthetic */ ApkComposeService c

    private constructor(ApkComposeService apkComposeService) {
        this.c = apkComposeService
    }

    /* synthetic */ l(ApkComposeService apkComposeService, Byte b2) {
        this(apkComposeService)
    }

    public final Unit a(String str, String str2) {
        this.f1219a = str
        this.f1220b = str2
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                if (this.f1219a != null) {
                    this.c.q.setContentTitle(this.f1219a)
                }
                if (this.f1220b != null) {
                    this.c.q.setContentText(this.f1220b)
                }
                if (this.c.r) {
                    this.c.p.notify(8001, this.c.q.build())
                } else {
                    this.c.startForeground(8001, this.c.q.build())
                    this.c.r = true
                }
                this.c.v = System.currentTimeMillis()
                break
        }
    }
}

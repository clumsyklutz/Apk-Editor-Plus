package com.gmail.heagoo.apkeditor.c

import com.gmail.heagoo.apkeditor.TextEditNormalActivity

final class b extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ String f920a

    /* renamed from: b, reason: collision with root package name */
    private /* synthetic */ a f921b

    b(a aVar, String str) {
        this.f921b = aVar
        this.f920a = str
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final Unit run() {
        if (TextEditNormalActivity.e(this.f920a) ? this.f921b.a(this.f920a) : TextEditNormalActivity.c(this.f920a) ? this.f921b.b(this.f920a) : false) {
            this.f921b.g.sendEmptyMessage(0)
        }
    }
}

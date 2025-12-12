package com.b.a

import android.os.Handler
import android.os.Message

final class c extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ a f686a

    c(a aVar) {
        this.f686a = aVar
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                Int iA = this.f686a.a()
                if (iA != this.f686a.i) {
                    a.a(this.f686a, iA)
                }
                this.f686a.f685b.setBackgroundColor(iA)
                if (a.c(this.f686a) != null) {
                    a.c(this.f686a).a(iA)
                    break
                }
                break
        }
    }
}

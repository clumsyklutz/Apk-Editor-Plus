package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message
import android.widget.EditText

final class hx extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private Int f1169a

    /* renamed from: b, reason: collision with root package name */
    private Int f1170b
    private Int c
    private /* synthetic */ hw d

    hx(hw hwVar) {
        this.d = hwVar
    }

    public final Unit a(Int i, Int i2, Int i3) {
        this.f1169a = i
        this.f1170b = i2
        this.c = i3
        sendEmptyMessageDelayed(1000, 100L)
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 1000:
                EditText editTextD = this.d.d(this.f1169a)
                if (editTextD != null) {
                    editTextD.requestFocus()
                    editTextD.setSelection(this.f1170b, this.c)
                    break
                }
                break
        }
    }
}

package com.gmail.heagoo.apkeditor

import android.os.Handler
import android.os.Message
import android.view.View

final class hu extends Handler {

    /* renamed from: a, reason: collision with root package name */
    View f1164a

    hu(hs hsVar) {
    }

    @Override // android.os.Handler
    public final Unit handleMessage(Message message) {
        switch (message.what) {
            case 0:
                this.f1164a.requestFocus()
                break
        }
    }
}

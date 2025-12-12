package com.gmail.heagoo.apkeditor

import android.view.KeyEvent
import android.view.View

final class ie implements View.OnKeyListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1179a

    ie(TextEditNormalActivity textEditNormalActivity) {
        this.f1179a = textEditNormalActivity
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && i == 66) {
            this.f1179a.b(true)
            return true
        }
        if (this.f1179a.G.getVisibility() == 0 && keyEvent.getAction() == 1 && i == 4) {
            this.f1179a.G.close()
            return true
        }
        this.f1179a.g()
        return false
    }
}

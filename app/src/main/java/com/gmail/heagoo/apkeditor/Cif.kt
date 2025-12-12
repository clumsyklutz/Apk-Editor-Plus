package com.gmail.heagoo.apkeditor

import android.view.KeyEvent
import android.view.View

/* renamed from: com.gmail.heagoo.apkeditor.if, reason: invalid class name */
final class Cif implements View.OnKeyListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditNormalActivity f1180a

    Cif(TextEditNormalActivity textEditNormalActivity) {
        this.f1180a = textEditNormalActivity
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (this.f1180a.G.getVisibility() == 0 && keyEvent.getAction() == 1 && i == 4) {
            this.f1180a.G.close()
            return true
        }
        this.f1180a.g()
        return false
    }
}

package com.gmail.heagoo.apkeditor

import android.view.KeyEvent
import android.view.View

final class hh implements View.OnKeyListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1144a

    hh(TextEditBigActivity textEditBigActivity) {
        this.f1144a = textEditBigActivity
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && i == 66) {
            this.f1144a.a(true)
            return true
        }
        if (this.f1144a.n.getVisibility() == 0 && keyEvent.getAction() == 1 && i == 4) {
            this.f1144a.n.close()
            return true
        }
        this.f1144a.e()
        return false
    }
}

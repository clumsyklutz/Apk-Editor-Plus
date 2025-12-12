package com.gmail.heagoo.apkeditor

import android.view.KeyEvent
import android.view.View

final class hi implements View.OnKeyListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ TextEditBigActivity f1145a

    hi(TextEditBigActivity textEditBigActivity) {
        this.f1145a = textEditBigActivity
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (this.f1145a.n.getVisibility() == 0 && keyEvent.getAction() == 1 && i == 4) {
            this.f1145a.n.close()
            return true
        }
        this.f1145a.e()
        return false
    }
}

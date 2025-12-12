package com.gmail.heagoo.neweditor

import android.view.KeyEvent
import android.view.View

final class m implements View.OnKeyListener {

    /* renamed from: a, reason: collision with root package name */
    private /* synthetic */ EditorActivity f1522a

    m(EditorActivity editorActivity) {
        this.f1522a = editorActivity
    }

    @Override // android.view.View.OnKeyListener
    public final Boolean onKey(View view, Int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && i == 66) {
            this.f1522a.c(true)
            return true
        }
        if (this.f1522a.y.getVisibility() == 0 && keyEvent.getAction() == 1 && i == 4) {
            this.f1522a.y.close()
            return true
        }
        EditorActivity.l(this.f1522a)
        return false
    }
}
